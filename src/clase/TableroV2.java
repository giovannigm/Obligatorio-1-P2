package clase;

public class TableroV2 {
    private int cantFilas = 7; // Número de filas del tablero
    private int cantColumnas = 13; // Número de columnas del tablero
    private String[][] tablero;

    // Constructor para inicializar el tablero con dimensiones fijas (7 filas, 13
    // columnas)
    public TableroV2() {
        this.tablero = new String[cantFilas][cantColumnas];
        inicializarTablero();
    }

    // Método para inicializar el tablero con espacios vacíos decrecientes y
    // crecientes
    private void inicializarTablero() {
        for (int fila = 0; fila < cantFilas; fila++) {
            int espaciosVacios = Math.abs((cantFilas / 2) - fila); // Calcula espacios vacíos según la distancia a la
                                                                   // mitad
            for (int columna = 0; columna < cantColumnas; columna++) {
                // Dejar espacios vacíos al inicio y al final de cada fila
                if (columna < espaciosVacios || columna >= cantColumnas - espaciosVacios) {
                    tablero[fila][columna] = " ";
                } else {
                    tablero[fila][columna] = "*"; // Rellenar el resto con '*'
                }
            }
        }
    }

    // Método para mostrar el tablero con filas separadas por un espacio adicional
    public void mostrarTablero() {
        // Imprimir las coordenadas alfabéticas de las columnas
        System.out.print("    "); // Espacio adicional para alinear con los números de fila
        for (int columna = 0; columna < cantColumnas; columna++) {
            System.out.printf("%2c ", (char) ('A' + columna));
        }
        System.out.println();
        System.out.println();

        // Imprimir las filas con sus números
        for (int fila = 0; fila < cantFilas; fila++) {
            System.out.printf("%2d  ", fila + 1); // Asegurar alineación con números de dos dígitos
            for (int columna = 0; columna < cantColumnas; columna++) {
                System.out.printf("%2s ", tablero[fila][columna]);
            }
            System.out.println();

            // Agregar una línea vacía entre filas, excepto después de la última fila
            if (fila < cantFilas - 1) {
                System.out.println();
            }
        }
    }

}
