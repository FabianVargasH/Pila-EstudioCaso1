import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private PilaDinamica pila = new PilaDinamica();

    public void mostrar() throws IOException {
        int opcion = 0;

        do {
            System.out.println("\n=== ANALIZADOR DE EXPRESIONES ===");
            System.out.println("1. Ingresar expresión");
            System.out.println("2. Salir");
            System.out.print("Opción: ");

            String entrada = reader.readLine();
            opcion = Integer.parseInt(entrada);

            if (opcion == 1) {
                System.out.print("Ingrese expresión: ");
                String expresion = reader.readLine();

                if (expresion.trim().isEmpty()) {
                    System.out.println("Expresión vacía.");
                } else {
                    System.out.println("\n--- Resultado del análisis ---");

                    if (pila.validarExpresion(expresion)) {
                        ArrayList<Token> postfija = pila.infijaAPostfija(expresion);
                        double resultado = pila.evaluarPostFijas(postfija);

                        System.out.println("¿Es valida?         SI");
                        System.out.println("¿Se puede resolver? SI");
                        System.out.println("Notacion Infija:    " + expresion);

                        System.out.print("Notacion Postfija:  ");
                        for (int i = 0; i < postfija.size(); i++) {
                            System.out.print(postfija.get(i).getValor() + " ");
                        }
                        System.out.println();

                        System.out.println("Resultado:          " + resultado);

                    } else {
                        System.out.println("¿Es valida?         NO");
                        System.out.println("¿Se puede resolver? NO");
                        System.out.println("Notación Infija:    " + expresion);
                        System.out.println("Notación Postfija:  No disponible");
                    }
                }
            } else if (opcion == 2) {
                System.out.println("Saliendo del menu");

            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 2);
    }
}