package dominio;

public class Triangulo {
  private final int filaPunto; // Fila del punto principal
  private final int columnaPunto; // Columna del punto principal
  private final boolean apuntaArriba; // true si apunta hacia arriba, false si apunta hacia abajo
  private final int filaCentro; // Fila del centro del triángulo
  private final int columnaCentro; // Columna del centro del triángulo
  private char color; // 'B' para blanco, 'N' para negro

  public Triangulo(int filaPunto, int columnaPunto, boolean apuntaArriba) {
    this.filaPunto = filaPunto;
    this.columnaPunto = columnaPunto;
    this.apuntaArriba = apuntaArriba;

    // Calcular el centro del triángulo
    if (apuntaArriba) {
      this.filaCentro = filaPunto - 1;
      this.columnaCentro = columnaPunto;
    } else {
      this.filaCentro = filaPunto + 1;
      this.columnaCentro = columnaPunto;
    }
    this.color = ' '; // Inicialmente sin color
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

  public int getFilaCentro() {
    return filaCentro;
  }

  public int getColumnaCentro() {
    return columnaCentro;
  }

  public char getColor() {
    return color;
  }

  public void setColor(char color) {
    this.color = color;
  }

  @Override
  public String toString() {
    return String.format("Triángulo en (%d,%d) apuntando %s, centro en (%d,%d), color: %c",
        filaPunto, columnaPunto, apuntaArriba ? "arriba" : "abajo",
        filaCentro, columnaCentro, color);
  }
}
