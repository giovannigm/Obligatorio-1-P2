package models;

public class Posicion {
  private final int fila;
  private final int columna;
  private final char simbolo;

  public Posicion(int fila, int columna, char simbolo) {
    this.fila = fila;
    this.columna = columna;
    this.simbolo = simbolo;
  }

  public int getFila() {
    return fila;
  }

  public int getColumna() {
    return columna;
  }

  public char getSimbolo() {
    return simbolo;
  }

  @Override
  public String toString() {
    return String.format("Posicion(fila=%d, columna=%d, simbolo='%c')", fila, columna, simbolo);
  }
}
