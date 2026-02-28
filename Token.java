public class Token {
    private String valor;
    private String tipo;

    public Token(String valor) {
        this.valor = valor;
        this.tipo = determinarTipo(valor);
    }

    private String determinarTipo(String valor) {
        if (valor.matches("\\d+")) {
            return "NUMERO";
        } else if (valor.equals("+") || valor.equals("-") || valor.equals("*") || valor.equals("/")) {
            return "OPERADOR";
        } else if (valor.equals("(")) {
            return "PARENTESIS_IZQ";
        } else if (valor.equals(")")) {
            return "PARENTESIS_DER";
        } else {
            return "DESCONOCIDO";
        }
    }
    //Getters
    public String getValor() {
        return valor;
    }
    public String getTipo() {
        return tipo;
    }

    //Metodo toString, que solo va devolver el valor
    public String toString() {
        return valor;
    }
}