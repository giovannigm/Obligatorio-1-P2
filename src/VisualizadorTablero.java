public class VisualizadorTablero {
  private final char[][] tablero;
  private final int filas;
  private final int columnas;

  public VisualizadorTablero(char[][] tablero, int filas, int columnas) {
    this.tablero = tablero;
    this.filas = filas;
    this.columnas = columnas;
  }

  public void mostrarTablero() {
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
