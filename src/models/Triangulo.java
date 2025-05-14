package models;

public class Triangulo {
  private final int filaPunto; // Fila del punto principal
  private final int columnaPunto; // Columna del punto principal
  private final boolean apuntaArriba; // true si apunta hacia arriba, false si apunta hacia abajo

  public Triangulo(int filaPunto, int columnaPunto, boolean apuntaArriba) {
    this.filaPunto = filaPunto;
    this.columnaPunto = columnaPunto;
    this.apuntaArriba = apuntaArriba;
  }

  public int getFilaPunto() {
    return filaPunto;
  }

  public int getColumnaPunto() {
    return columnaPunto;
  }

  public boolean isApuntaArriba() {
    return apuntaArriba;
  }

  @Override
  public String toString() {
    return String.format("Triángulo en (%d,%d) apuntando %s",
        filaPunto, columnaPunto, apuntaArriba ? "arriba" : "abajo");
  }
}
