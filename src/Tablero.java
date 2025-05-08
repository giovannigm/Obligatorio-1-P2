public class Tablero {
    private char[][] puntos;
    private final int filas = 7;
    private final int columnas = 13;

    public Tablero() {
        puntos = new char[filas][columnas];
        inicializarTablero();
    }

    private void inicializarTablero() {
        // Inicializar todo con espacios
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                puntos[fila][columna] = ' ';
            }
        }

        // Definir los rangos de asteriscos para cada fila según la imagen de referencia
        int[] inicio = { 3, 2, 1, 0, 1, 2, 3 }; // columna inicial para cada fila
        int[] fin = { 9, 10, 11, 12, 11, 10, 9 }; // columna final (inclusive) para cada fila

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = inicio[fila]; columna <= fin[fila]; columna++) {
                puntos[fila][columna] = '*';
            }
        }
    }

    public void mostrarTablero() {
        // Imprimir las coordenadas alfabéticas de las columnas (A-M) con espacio extra
        System.out.print("    ");
        for (int columna = 0; columna < columnas; columna++) {
            System.out.printf("%3c ", (char) ('A' + columna));
        }
        System.out.println();
        System.out.println();

        // Imprimir las filas con sus números y espacio extra entre columnas
        for (int fila = 0; fila < filas; fila++) {
            System.out.printf("%2d  ", fila + 1);
            for (int columna = 0; columna < columnas; columna++) {
                System.out.printf("%3c ", puntos[fila][columna]);
            }
            System.out.println();
            // Línea vacía entre filas para mayor separación visual
            System.out.println();
        }
    }

    public boolean colocarBanda(Jugada jugada) {
        int fila = jugada.getFila() - 1;
        int columna = jugada.getColumna() - 'A';
        char direccion = jugada.getDireccion();
        int cantidad = jugada.getCantidad();

        // Verificar si la posición inicial es válida
        if (!estaDentroLimites(fila, columna) || puntos[fila][columna] == ' ') {
            System.out.println("Posición inicial inválida.");
            return false;
        }

        int deltaFila = 0;
        int deltaColumna = 0;

        switch (direccion) {
            case 'Q':
                deltaFila = -1;
                deltaColumna = -1;
                break;
            case 'E':
                deltaFila = -1;
                deltaColumna = 1;
                break;
            case 'D':
                deltaFila = 0;
                deltaColumna = 1;
                break;
            case 'C':
                deltaFila = 1;
                deltaColumna = 1;
                break;
            case 'Z':
                deltaFila = 1;
                deltaColumna = -1;
                break;
            case 'A':
                deltaFila = 0;
                deltaColumna = -1;
                break;
            default:
                System.out.println("Dirección inválida.");
                return false;
        }

        // Verificar si toda la banda cabe en el tablero antes de colocarla
        int filaTemp = fila;
        int columnaTemp = columna;
        for (int i = 0; i < cantidad; i++) {
            if (!estaDentroLimites(filaTemp, columnaTemp) || puntos[filaTemp][columnaTemp] == ' ') {
                System.out.println("La banda se sale del tablero o pasa por espacios inválidos.");
                return false;
            }
            filaTemp += deltaFila;
            columnaTemp += deltaColumna;
        }

        // Colocar la banda
        for (int i = 0; i < cantidad; i++) {
            puntos[fila][columna] = obtenerSimbolo(direccion);
            fila += deltaFila;
            columna += deltaColumna;
        }
        return true;
    }

    private boolean estaDentroLimites(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    private char obtenerSimbolo(char direccion) {
        return switch (direccion) {
            case 'D', 'A' -> '-';
            case 'E', 'Z' -> '/';
            case 'Q', 'C' -> '\\';
            default -> '*';
        };
    }
}
