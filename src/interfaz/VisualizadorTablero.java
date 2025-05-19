// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package interfaz;

import java.util.ArrayList;

import dominio.Triangulo;

public class VisualizadorTablero {
  private final char[][] tablero;
  private final int filas;
  private final int columnas;
  private final ArrayList<Triangulo> triangulosActivos;

  public VisualizadorTablero(char[][] tablero, int filas, int columnas, ArrayList<Triangulo> triangulosActivos) {
    this.tablero = tablero;
    this.filas = filas;
    this.columnas = columnas;
    this.triangulosActivos = triangulosActivos;
  }

  public void mostrarTablero() {
    // Contar triángulos por color
    int triangulosBlancos = 0;
    int triangulosNegros = 0;
    for (Triangulo triangulo : triangulosActivos) {
      if (triangulo.getColor() == '□') {
        triangulosBlancos++;
      } else if (triangulo.getColor() == '■') {
        triangulosNegros++;
      }
    }

    // Mostrar contador de triángulos
    System.out.println("\nTriángulos formados:");
    System.out.println("Blancos (□): " + triangulosBlancos);
    System.out.println("Negros (■): " + triangulosNegros);
    System.out.println();

    // Mostrar letras en la parte superior (A-M)
    System.out.print("    ");
    for (char c = 'A'; c <= 'M'; c++) {
      System.out.print(c + " "); // Añadido espacio extra para el nuevo tamaño
    }
    System.out.println();

    // Mostrar el tablero
    for (int fila = 0; fila < filas; fila++) {
      if (fila % 2 == 0) {
        // Imprime el numero con un cero a la izquierda para evitar saltos de linea
        System.out.print(String.format("%02d", fila / 2 + 1) + "  ");
      } else {
        System.out.print("--  ");
      }
      for (int columna = 0; columna < columnas; columna++) {
        System.out.print(tablero[fila][columna]);
      }
      System.out.println();
    }
  }
}
