public class Tablero {
    private final char[][] tablero;
    private final int filas = 13;
    private final int columnas = 13;

    public Tablero() {
        this.tablero = new char[filas][columnas];
        inicializarTablero();
    }

    // Inicializa el tablero en forma de hexágono
    private void inicializarTablero() {
        // Rellenar con espacios vacíos
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                tablero[fila][columna] = ' ';
            }
        }

        // Definición de los puntos en filas impares
        int inicio = 3; // TODO: hacer que se calcule dinamicamente
        int fin = 10; // TODO: hacer que se calcule dinamicamente

        for (int fila = 0; fila < filas; fila++) {
            if (fila % 2 == 0) {
                System.out.println("fila: " + fila + " inicio: " + inicio + " fin: " + fin);
                for (int columna = inicio; columna <= fin && columna < columnas; columna += 2) {
                    System.out.println("             columna: " + columna);
                    tablero[fila][columna] = '*';
                }

                if (inicio > 0 && fila < (filas / 2)) {
                    inicio--;
                } else {
                    inicio++;
                }
                if (fin < columnas && fila < (filas / 2)) {
                    fin++;
                } else {
                    fin--;
                }
            }
        }
    }

    // Renderiza el tablero en forma hexagonal con las letras
    public void mostrarTablero() {
        // Mostrar letras en la parte superior
        System.out.print("    ");
        for (char c = 'A'; c <= 'M'; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        // Mostrar el tablero
        for (int fila = 0; fila < filas; fila++) {
            System.out.print((fila + 1) + "  ");
            for (int columna = 0; columna < columnas; columna++) {
                System.out.print(tablero[fila][columna] + " ");
            }
            System.out.println();
        }
    }
}
