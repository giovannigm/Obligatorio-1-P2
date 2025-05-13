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
        int inicio = 3;
        int fin = 10;

        for (int fila = 0; fila < filas; fila++) {
            if (fila % 2 == 0) {
                for (int columna = inicio; columna <= fin && columna < columnas; columna += 2) {
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

    // Método para colocar una banda en el tablero
    public boolean colocarBanda(int fila, int columna, char direccion, int cantidad) {
        int deltaFila = 0;
        int deltaColumna = 0;

        switch (direccion) {
            case 'D':
                deltaColumna = 2;
                break; // Este ➡️
            case 'A':
                deltaColumna = -2;
                break; // Oeste ⬅️
            case 'E':
                deltaFila = -1;
                deltaColumna = 1;
                break; // Noreste ↗️
            case 'Q':
                deltaFila = -1;
                deltaColumna = -1;
                break; // Noroeste ↖️
            case 'C':
                deltaFila = 1;
                deltaColumna = 1;
                break; // Sureste ↘️
            case 'Z':
                deltaFila = 1;
                deltaColumna = -1;
                break; // Suroeste ↙️
            default:
                System.out.println("Dirección inválida.");
                return false;
        }

        // Validación de límites y colocación
        for (int i = 0; i < cantidad; i++) {
            if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
                System.out.println("Se sale del tablero.");
                return false;
            }

            if (tablero[fila][columna] == '*') {
                System.out.println("No se puede colocar banda sobre un punto.");
                return false;
            }

            tablero[fila][columna] = obtenerSimbolo(direccion);

            fila += deltaFila;
            columna += deltaColumna;
        }
        return true;
    }

    // Determina el símbolo correcto para la dirección
    private char obtenerSimbolo(char direccion) {
        return switch (direccion) {
            case 'D', 'A' -> '-';
            case 'Q', 'C' -> '/';
            case 'E', 'Z' -> '\\';
            default -> ' ';
        };
    }

    // Renderiza el tablero en forma hexagonal con las letras
    public void mostrarTablero() {
        // Mostrar letras en la parte superior
        System.out.print("   ");
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