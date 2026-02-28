# Pila en el Análisis de Expresiones Aritméticas
**Curso:** Estructura de Datos — Universidad Cenfotec  
**Estudiante:** Fabián Vargas Hidalgo

## Descripción
Implementación en Java de una pila dinámica aplicada al análisis de expresiones aritméticas. El programa permite ingresar una expresión, validarla, convertirla de notación infija a postfija y evaluarla.

## Clases
- **Token** — representa cada elemento de la expresión (número, operador, paréntesis).
- **PilaDinamica** — estructura de pila basada en `ArrayList` con los algoritmos principales.
- **Menu** — interfaz de consola para interactuar con el programa.

## Algoritmos implementados
- **Shunting-Yard (Dijkstra)** — conversión de notación infija a postfija.
- **Evaluación postfija** — cálculo del resultado recorriendo la expresión postfija.

## Funcionalidades
- Validación de la expresión (caracteres, paréntesis balanceados, operadores consecutivos).
- Conversión de infija a postfija.
- Evaluación y resultado de la expresión.

## Ejemplo
```
Ingrese expresión: 3+5*2

¿Es válida?         SI
¿Se puede resolver? SI
Notación Infija:    3+5*2
Notación Postfija:  3 5 2 * +
Resultado:          13.0
```