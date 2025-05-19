package dominio;

import java.util.ArrayList;

public class VisualizadorTablero {
  private final char[][] tablero;
  private final int filas;
  private final int columnas;
  private final ArrayList<Triangulo> triangulosActivos;
  private ArrayList<char[][]> historialTableros;

  public VisualizadorTablero(char[][] tablero, int filas, int columnas, ArrayList<Triangulo> triangulosActivos) {
    this.tablero = tablero;
    this.filas = filas;
    this.columnas = columnas;
    this.triangulosActivos = triangulosActivos;
    this.historialTableros = new ArrayList<>();
  }

  public void setHistorialTableros(ArrayList<char[][]> historial) {
    System.out.println("Historial de tableros: " + historial.size());
    this.historialTableros = historial;
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

    System.out.println("Historial de tableros Visualizador: " + historialTableros.size());
    // Preparar los tableros para mostrar
    ArrayList<char[][]> tablerosAMostrar = new ArrayList<>();
    for (char[][] tablero : historialTableros) {
      tablerosAMostrar.add(tablero);
    }
    if (tablerosAMostrar.size() == 0) {
      tablerosAMostrar.add(tablero);
    }
    System.out.println("Tableros para mostrar: " + tablerosAMostrar.size());
    // Mostrar letras en la parte superior para cada tablero
    for (int t = 0; t < tablerosAMostrar.size(); t++) {
      System.out.print("    ");
      for (char c = 'A'; c <= 'M'; c++) {
        System.out.print(c + " ");
      }
      System.out.print("    "); // Espacio entre tableros
    }
    System.out.println();

    // Mostrar los tableros lado a lado
    for (int fila = 0; fila < filas; fila++) {
      for (char[][] tableroActual : tablerosAMostrar) {
        if (fila % 2 == 0) {
          System.out.print(String.format("%02d", fila / 2 + 1) + "  ");
        } else {
          System.out.print("--  ");
        }
        for (int columna = 0; columna < columnas; columna++) {
          System.out.print(tableroActual[fila][columna]);
        }
        System.out.print("    "); // Espacio entre tableros
      }
      System.out.println();
    }

    // Mostrar indicador de tiempo para cada tablero
    System.out.println();
    for (int t = 0; t < tablerosAMostrar.size(); t++) {
      if (t == tablerosAMostrar.size() - 1) {
        System.out.print("Tablero actual    ");
      } else {
        System.out.print("Tablero " + (t + 1) + " atrás                   ");
      }
    }
    System.out.println();
  }
}
