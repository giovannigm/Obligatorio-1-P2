public class Tablero {
  private char[][] puntos; // Matriz para el tablero
  private final int filas = 7;
  private final int columnas = 13; // De A a M = 13 columnas

  public Tablero() {
    puntos = new char[filas][columnas];
    inicializarTablero();
  }

  private void inicializarTablero() {
    for (int fila = 0; fila < filas; fila++) {
      for (int columna = 0; columna < columnas; columna++) {
        puntos[fila][columna] = '*'; // Cada punto es un '*'
      }
    }
  }

  public void mostrarTablero() {
    System.out.print("  ");
    for (char c = 'A'; c <= 'M'; c++) {
      System.out.print(c + " ");
    }
    System.out.println();

    for (int fila = 0; fila < filas; fila++) {
      System.out.print((fila + 1) + " ");
      for (int columna = 0; columna < columnas; columna++) {
        System.out.print(puntos[fila][columna] + " ");
      }
      System.out.println();
    }
  }
}
