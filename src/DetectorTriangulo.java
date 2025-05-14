import models.Triangulo;
import java.util.ArrayList;

public class DetectorTriangulo {
  private final char[][] tablero;
  private final int filas;
  private final int columnas;

  public DetectorTriangulo(char[][] tablero, int filas, int columnas) {
    this.tablero = tablero;
    this.filas = filas;
    this.columnas = columnas;
  }

  public ArrayList<Triangulo> detectarTriangulos() {
    ArrayList<Triangulo> triangulos = new ArrayList<>();

    // Recorrer solo las filas pares donde están las horizontales
    for (int fila = 0; fila < filas; fila += 2) {
      for (int columna = 0; columna < columnas; columna++) {
        if (tablero[fila][columna] == '-') {
          if (esTrianguloArriba(fila, columna)) {
            triangulos.add(new Triangulo(fila, columna, true));
          }

          if (esTrianguloAbajo(fila, columna)) {
            triangulos.add(new Triangulo(fila, columna, false));
          }
        }
      }
    }

    System.out.println("Triangulos encontrados: " + triangulos.size());
    return triangulos;
  }

  private boolean esTrianguloArriba(int fila, int columna) {
    if ((fila - 1) <= 0) {
      return false;
    }

    boolean bandaDiagonalIzquierda = tablero[fila - 1][columna - 1] == '/';
    boolean bandaDiagonalDerecha = tablero[fila - 1][columna] == '\\';

    return bandaDiagonalIzquierda && bandaDiagonalDerecha;
  }

  private boolean esTrianguloAbajo(int fila, int columna) {
    if ((fila - 1) >= filas || (fila + 1) >= filas) {
      return false;
    }

    boolean bandaDiagonalDerecha = tablero[fila + 1][columna] == '/';
    boolean bandaDiagonalIzquierda = tablero[fila + 1][columna - 1] == '\\';

    return bandaDiagonalDerecha && bandaDiagonalIzquierda;
  }
}
