package dominio;

import java.util.ArrayList;

public class ValidadorBanda {
  private final char[][] tablero;
  private final int filas;
  private final int columnas;
  private final boolean permitirSuperposicionBandas;

  public ValidadorBanda(char[][] tablero, int filas, int columnas, boolean permitirSuperposicionBandas) {
    this.tablero = tablero;
    this.filas = filas;
    this.columnas = columnas;
    this.permitirSuperposicionBandas = permitirSuperposicionBandas;
  }

  public ArrayList<Posicion> validarJugada(Jugada jugada) {
    ArrayList<Posicion> posiciones = new ArrayList<>();
    int filaUsuario = jugada.getFila();
    int columna = jugada.getColumna();
    char direccion = jugada.getDireccion();
    int cantidad = jugada.getCantidad();

    // Validar que la fila del usuario esté en el rango correcto (1-7)
    if (filaUsuario < 1 || filaUsuario > 7) {
      System.out.println("La fila debe estar entre 1 y 7.");
      return null;
    }

    // Convertir la fila del usuario a nuestra representación interna
    int fila = (filaUsuario - 1) * 2;

    if (fila >= filas || fila < 0) {
      return null;
    }

    // Validar que la columna esté en el rango correcto (A-M)
    if (columna < 0 || columna >= columnas / 2) { // Ajustado para el nuevo tamaño
      System.out.println("La columna debe estar entre A y M.");
      return null;
    }

    columna = columna * 2; // Duplicar la columna

    // Validar que la posición inicial sea un punto "*"
    if (tablero[fila][columna] != '*') {
      System.out.println("Debe comenzar desde un punto.");
      return null;
    }

    int deltaFila = 0;
    int deltaColumna = 0;

    switch (direccion) {
      case 'D':
        deltaColumna = -4;
        break;
      case 'A':
        deltaColumna = 4;
        break;
      case 'E':
        deltaFila = 2;
        deltaColumna = -2;
        break;
      case 'Q':
        deltaFila = 2;
        deltaColumna = 2;
        break;
      case 'C':
        deltaFila = -2;
        deltaColumna = -2;
        break;
      case 'Z':
        deltaFila = -2;
        deltaColumna = 2;
        break;
      default:
        System.out.println("Dirección inválida.");
        return null;
    }

    // Posiciones para validación
    int filaValidacion = fila;
    int columnaValidacion = columna;
    char simbolo = obtenerSimbolo(direccion);

    // Validación de límites y recolección de posiciones
    for (int i = 0; i < cantidad; i++) {
      // Actualizar posición de validación
      filaValidacion += deltaFila;
      columnaValidacion += deltaColumna;

      boolean fueraDeLimites = filaValidacion < 0 || filaValidacion >= filas ||
          columnaValidacion < 0 || columnaValidacion >= columnas;

      if (fueraDeLimites) {
        System.out.println("La banda se sale del tablero.");
        return null;
      }

      // Calcular la posición donde se colocará la banda (en el espacio entre puntos)
      int filaBanda = (fila + filaValidacion) / 2;
      int columnaBanda = (columna + columnaValidacion) / 2;

      // Validar que la posición de la banda sea un espacio vacío o permitir
      if (!permitirSuperposicionBandas && tablero[filaBanda][columnaBanda] != ' ') {
        System.out.println("No se puede colocar banda sobre otra banda o punto.");
        return null;
      }

      // Validar que la siguiente posición sea un punto "*" o una banda válida
      if (i == cantidad - 1) { // Si es la última banda
        if (tablero[filaValidacion][columnaValidacion] != '*') {
          System.out.println("La banda debe terminar en un punto.");
          return null;
        }
      } else { // Si no es la última banda
        int siguienteFila = (filaValidacion + (filaValidacion + deltaFila)) / 2;
        int siguienteColumna = (columnaValidacion + (columnaValidacion + deltaColumna)) / 2;

        fueraDeLimites = siguienteFila < 0 || siguienteFila >= filas ||
            siguienteColumna < 0 || siguienteColumna >= columnas;
        if (fueraDeLimites || (!permitirSuperposicionBandas && tablero[siguienteFila][siguienteColumna] != ' ')) {
          System.out.println("La banda debe conectarse con otro punto o banda válida.");
          return null;
        }
      }

      // Agregar la posición a la lista
      posiciones.add(new Posicion(filaBanda, columnaBanda, simbolo));

      fila += deltaFila;
      columna += deltaColumna;
    }

    return posiciones;
  }

  private char obtenerSimbolo(char direccion) {
    return switch (direccion) {
      case 'D', 'A' -> '-';
      case 'Q', 'C' -> '\\';
      case 'E', 'Z' -> '/';
      default -> ' ';
    };
  }
}
