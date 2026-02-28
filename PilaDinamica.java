import java.util.ArrayList;

public class PilaDinamica {
    private ArrayList<Token> pilaTokens;

    public PilaDinamica() {
        pilaTokens = new ArrayList<>();
    }

    public void push(Token token) {
        pilaTokens.add(token);
    }

    public Token pop() {
        if (pilaTokens.isEmpty()){
            System.out.println("La pila esta vacia");
            return null;
        } 
        return pilaTokens.remove(pilaTokens.size() - 1);
    }

    public Token peek() {
        if (pilaTokens.isEmpty()){
            System.out.println("La pila esta vacia");
            return null;
        } 
        return pilaTokens.get(pilaTokens.size() - 1);
    }

    public boolean isEmpty() {
        return pilaTokens.isEmpty();
    }

    public int size() {
        return pilaTokens.size();
    }

    // dividir una cadena de texto en partes con significado (Tokenizar)
    private ArrayList<Token> tokenizar(String expresion) {
        ArrayList<Token> tokens = new ArrayList<>();
        String expr = expresion.replaceAll("\\s+", "");
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder numero = new StringBuilder();
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    numero.append(expr.charAt(i));
                    i++;
                }
                tokens.add(new Token(numero.toString()));
            } else {
                tokens.add(new Token(String.valueOf(c)));
                i++;
            }
        }
        return tokens;
    }

    private int prioridad(String operador) {
        switch (operador) {
            case "+": case "-": return 1;
            case "*": case "/": return 2;
            default: return 0;
        }
    }

    private double operar(double a, double b, String operador) {
        switch (operador) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) {
                    System.out.println("Motivo: división por cero.");
                    return 0;
                }
                return a / b;
            default: return 0;
        }
    }
    //Metodo para validar expresiones, parentesis, operadores
    public boolean validarExpresion(String expresion) {
        String expr = expresion.replaceAll("\\s+", "");

        if (expr.isEmpty()) return false;

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (!"0123456789+-*/()".contains(String.valueOf(c))) {
                System.out.println("Motivo: carácter no válido -> '" + c + "'");
                return false;
            }
        }

        int balance = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    System.out.println("Motivo: paréntesis de cierre sin apertura.");
                    return false;
                }
            }
        }
        if (balance != 0) {
            System.out.println("Motivo: paréntesis desbalanceados.");
            return false;
        }

        char ultimo = expr.charAt(expr.length() - 1);
        if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
            System.out.println("Motivo: la expresión no puede terminar con un operador.");
            return false;
        }

        char primero = expr.charAt(0);
        if (primero == '*' || primero == '/' || primero == '+') {
            System.out.println("Motivo: la expresión no puede empezar con '" + primero + "'");
            return false;
        }

        ArrayList<Token> tokens = tokenizar(expr);
        for (int i = 1; i < tokens.size(); i++) {
            boolean actualEsOp  = tokens.get(i).getTipo().equals("OPERADOR");
            boolean anteriorEsOp = tokens.get(i - 1).getTipo().equals("OPERADOR");
            if (actualEsOp && anteriorEsOp) {
                System.out.println("Motivo: operadores consecutivos -> '"
                        + tokens.get(i - 1).getValor() + tokens.get(i).getValor() + "'");
                return false;
            }
        }
        return true;
    }

    // Algoritmo Shunting-Yard
    public ArrayList<Token> infijaAPostfija(String expresion) {
        ArrayList<Token> salida = new ArrayList<>();
        PilaDinamica pilaOperadores = new PilaDinamica();
        ArrayList<Token> tokens = tokenizar(expresion);

        for (Token token : tokens) {
            String tipo = token.getTipo();
            if (tipo.equals("NUMERO")) {
                salida.add(token);
            } else if (tipo.equals("OPERADOR")) {
                while (!pilaOperadores.isEmpty()
                        && pilaOperadores.peek().getTipo().equals("OPERADOR")
                        && prioridad(pilaOperadores.peek().getValor()) >= prioridad(token.getValor())) {
                    salida.add(pilaOperadores.pop());
                }
                pilaOperadores.push(token);
            } else if (tipo.equals("PARENTESIS_IZQ")) {
                pilaOperadores.push(token);
            } else if (tipo.equals("PARENTESIS_DER")) {
                while (!pilaOperadores.isEmpty()
                        && !pilaOperadores.peek().getTipo().equals("PARENTESIS_IZQ")) {
                    salida.add(pilaOperadores.pop());
                }
                if (!pilaOperadores.isEmpty()) pilaOperadores.pop();
            }
        }

        while (!pilaOperadores.isEmpty()) {
            salida.add(pilaOperadores.pop());
        }
        return salida;
    }

    // Evaluación de expresión postfija
    public double evaluarPostFijas(ArrayList<Token> postfija) {
        PilaDinamica pilaNumeros = new PilaDinamica();

        for (Token token : postfija) {
            if (token.getTipo().equals("NUMERO")) {
                pilaNumeros.push(token);
            } else if (token.getTipo().equals("OPERADOR")) {
                Token b = pilaNumeros.pop();
                Token a = pilaNumeros.pop();

                if (a == null || b == null) {
                    System.out.println("Expresión inválida durante evaluación.");
                    return 0;
                }

                double numA = Double.parseDouble(a.getValor());
                double numB = Double.parseDouble(b.getValor());
                double resultado = operar(numA, numB, token.getValor());

                pilaNumeros.push(new Token(String.valueOf(resultado)));
            }
        }
        Token resultado = pilaNumeros.pop();
        if (resultado != null) {
            return Double.parseDouble(resultado.getValor());
        }else{
            return 0;
        }
    }
}