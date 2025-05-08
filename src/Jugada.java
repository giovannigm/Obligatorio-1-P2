public class Jugada {
  private char columna;
  private int fila;
  private char direccion;
  private int cantidad;

  public Jugada(char columna, int fila, char direccion, int cantidad) {
    this.columna = columna;
    this.fila = fila;
    this.direccion = direccion;
    this.cantidad = cantidad;
  }

  // Getters y toString() para probar
  public char getColumna() {
    return columna;
  }

  public int getFila() {
    return fila;
  }

  public char getDireccion() {
    return direccion;
  }

  public int getCantidad() {
    return cantidad;
  }

  @Override
  public String toString() {
    return "Columna: " + columna + ", Fila: " + fila + ", Dirección: " + direccion + ", Cantidad: " + cantidad;
  }
}
