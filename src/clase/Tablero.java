package clase;

public class Tablero {
    private int cantFilas = 7 * 2 - 1; // Número de filas del tablero
    private int cantColumnas = 13 * 2 - 1; // Número de columnas del tablero
    private String[][] tablero;

    // Constructor para inicializar el tablero con dimensiones fijas (7 filas, 13
    // columnas)
    public Tablero() {
        this.tablero = new String[cantFilas][cantColumnas];
        inicializarTablero();
    }

    // Método para inicializar el tablero con filas y columnas alternadas
    private void inicializarTablero() {
        int filaVisibleArriba = 0; // Contador para las filas visibles desde arriba
        int filaVisibleAbajo = 0; // Contador para las filas visibles desde abajo
        int mitad = cantFilas / 2; // Calcular la mitad del tablero

        for (int fila = 0; fila < cantFilas; fila++) {
            if (fila < mitad) { // Parte superior del tablero
                if (fila % 2 == 0) { // Solo procesar filas visibles
                    int offset = 0;
                    if (filaVisibleArriba == 0) {
                        offset = 3; // 3 espacios vacíos en la primera fila visible desde arriba
                    } else if (filaVisibleArriba == 1) {
                        offset = 2; // 2 espacios vacíos en la segunda fila visible desde arriba
                    } else if (filaVisibleArriba == 2) {
                        offset = 1; // 1 espacio vacío en la tercera fila visible desde arriba
                    }

                    for (int columna = 0; columna < cantColumnas; columna++) {
                        if (columna % 2 == 0 && columna >= offset * 2 && columna < cantColumnas - offset * 2) {
                            tablero[fila][columna] = "*"; // Visible
                        } else {
                            tablero[fila][columna] = " "; // No visible
                        }
                    }
                    filaVisibleArriba++; // Incrementar el contador de filas visibles desde arriba
                } else {
                    for (int columna = 0; columna < cantColumnas; columna++) {
                        tablero[fila][columna] = " "; // Filas no visibles
                    }
                }
            } else { // Parte inferior del tablero
                if (fila % 2 == 0) { // Solo procesar filas visibles
                    int offset = 0;
                    if (filaVisibleAbajo == 0) {
                        offset = 0; // Sin espacios vacíos en la primera fila visible desde abajo
                    } else if (filaVisibleAbajo == 1) {
                        offset = 1; // 1 espacio vacío en la segunda fila visible desde abajo
                    } else if (filaVisibleAbajo == 2) {
                        offset = 2; // 2 espacios vacíos en la tercera fila visible desde abajo
                    } else if (filaVisibleAbajo == 3) {
                        offset = 3; // 3 espacios vacíos en la cuarta fila visible desde abajo
                    }

                    for (int columna = 0; columna < cantColumnas; columna++) {
                        if (columna % 2 == 0 && columna >= offset * 2 && columna < cantColumnas - offset * 2) {
                            tablero[fila][columna] = "*"; // Visible
                        } else {
                            tablero[fila][columna] = " "; // No visible
                        }
                    }
                    filaVisibleAbajo++; // Incrementar el contador de filas visibles desde abajo
                } else {
                    for (int columna = 0; columna < cantColumnas; columna++) {
                        tablero[fila][columna] = " "; // Filas no visibles
                    }
                }
            }
        }
    }

    // Método para mostrar el tablero con filas separadas por un espacio adicional
    public void mostrarTablero() {
        // Imprimir las coordenadas alfabéticas de las columnas alternadas
        System.out.print("    "); // Espacio adicional para alinear con los números de fila
        for (int columna = 0; columna < cantColumnas; columna++) {
            if (columna % 2 == 0) {
                System.out.printf("%2c ", (char) ('A' + (columna / 2)));
            } else {
                System.out.print("   "); // Espacio para columnas no visibles
            }
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
