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

  public boolean colocarBanda(Jugada jugada) {
    int fila = jugada.getFila() - 1;
    int columna = jugada.getColumna() - 'A';
    char direccion = jugada.getDireccion();
    int cantidad = jugada.getCantidad();

    int deltaFila = 0;
    int deltaColumna = 0;

    switch (direccion) {
      case 'Q':
        deltaFila = -1;
        deltaColumna = -1;
        break;
      case 'E':
        deltaFila = -1;
        deltaColumna = 1;
        break;
      case 'D':
        deltaFila = 0;
        deltaColumna = 1;
        break;
      case 'C':
        deltaFila = 1;
        deltaColumna = 1;
        break;
      case 'Z':
        deltaFila = 1;
        deltaColumna = -1;
        break;
      case 'A':
        deltaFila = 0;
        deltaColumna = -1;
        break;
      default:
        System.out.println("Dirección inválida.");
        return false;
    }

    for (int i = 0; i < cantidad; i++) {
      if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
        System.out.println("Se sale del tablero.");
        return false;
      }

      if (direccion == 'D' || direccion == 'A') {
        puntos[fila][columna] = '-';
      } else if (direccion == 'Q' || direccion == 'C') {
        puntos[fila][columna] = '/';
      } else if (direccion == 'E' || direccion == 'Z') {
        puntos[fila][columna] = '\\';
      }

      fila += deltaFila;
      columna += deltaColumna;
    }

    return true;
  }
}
