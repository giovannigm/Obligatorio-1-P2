// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package dominio;

public class JugadaParser {
  public static Jugada interpretar(String input) {
    input = input.trim().toUpperCase(); // Limpieza y mayúsculas

    char columnaLetra = input.charAt(0); // Letra A-M
    int columna = columnaLetra - 'A'; // Convertir letra a índice (A=0, B=1, ..., M=12)
    int fila = Character.getNumericValue(input.charAt(1)); // Número 1-13 convertido a 0-12
    char direccion = input.charAt(2); // Dirección: Q, E, D, C, Z, A
    int cantidad = 4; // Por defecto

    if (input.length() > 3) {
      cantidad = Character.getNumericValue(input.charAt(3));
      if (cantidad > 4) {
        throw new IllegalArgumentException("La longitud de la banda no puede ser mayor a 4");
      }
    }

    return new Jugada(columna, fila, direccion, cantidad);
  }
}
