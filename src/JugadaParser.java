public class JugadaParser {
  public static Jugada interpretar(String input) {
    input = input.trim().toUpperCase(); // Limpieza y mayúsculas

    char columna = input.charAt(0); // Letra A-M
    int fila = Character.getNumericValue(input.charAt(1)); // Número 1-7
    char direccion = input.charAt(2); // Dirección: Q, E, D, C, Z, A
    int cantidad = 4; // Por defecto

    if (input.length() > 3) {
      cantidad = Character.getNumericValue(input.charAt(3));
    }

    return new Jugada(columna, fila, direccion, cantidad);
  }
}
