public class Tablero {
    private int cantFilas;
    private int cantColumnas;
    private String[][] tablero;

    // Campos para marcar la última posición de triángulo detectado
    private int ultimaFilaTriangulo = -1;
    private int ultimaColTriangulo = -1;
    private boolean hayTriangulo = false;

    // Campos para personalización dinámica
    private int[][] puntosPorFila; // Almacena los índices de columna de los puntos visibles en cada fila
    private int filasLogicas = 7;
    private int columnasLogicas = 13;

    public Tablero() {
        this.cantFilas = filasLogicas * 2 - 1;
        this.cantColumnas = columnasLogicas * 2 - 1;
        this.tablero = new String[cantFilas][cantColumnas];
        inicializarPuntosPorFila();
        inicializarTablero();
    }

    // Inicializa los índices de columna de los puntos visibles para cada fila
    private void inicializarPuntosPorFila() {
        puntosPorFila = new int[filasLogicas][];
        int mitad = filasLogicas / 2;
        for (int fila = 0; fila < filasLogicas; fila++) {
            int puntosEnFila = columnasLogicas - Math.abs(mitad - fila) * 2;
            int offset = Math.abs(mitad - fila);
            puntosPorFila[fila] = new int[puntosEnFila];
            for (int j = 0; j < puntosEnFila; j++) {
                puntosPorFila[fila][j] = (offset + j) * 2;
            }
        }
    }

    private void inicializarTablero() {
        for (int fila = 0; fila < cantFilas; fila++) {
            for (int columna = 0; columna < cantColumnas; columna++) {
                tablero[fila][columna] = " ";
            }
        }
        for (int fila = 0; fila < filasLogicas; fila++) {
            int realFila = fila * 2;
            for (int colIdx : puntosPorFila[fila]) {
                tablero[realFila][colIdx] = "*";
            }
        }
    }

    public void mostrarTablero() {
        // Print column headers: one letter per logical column, above the first visible
        // point
        System.out.print("    ");
        for (int logicalCol = 0; logicalCol < columnasLogicas; logicalCol++) {
            int realCol = getRealColForHeader(logicalCol);
            for (int col = 0; col < cantColumnas; col++) {
                if (col == realCol) {
                    System.out.printf("%2c ", (char) ('A' + logicalCol));
                } else if (col < realCol) {
                    System.out.print("   ");
                }
            }
        }
        System.out.println();
        System.out.println();

        int logicalRow = 1;
        for (int fila = 0; fila < cantFilas; fila++) {
            boolean visibleRow = false;
            for (int colIdx = 0; colIdx < columnasLogicas; colIdx++) {
                for (int idx : puntosPorFila[Math.min(fila / 2, filasLogicas - 1)]) {
                    if (fila % 2 == 0 && idx == getRealColForHeader(colIdx)) {
                        visibleRow = true;
                        break;
                    }
                }
                if (visibleRow)
                    break;
            }
            if (visibleRow && fila % 2 == 0) {
                System.out.printf("%2d  ", logicalRow++);
            } else {
                System.out.print("    ");
            }
            for (int columna = 0; columna < cantColumnas; columna++) {
                if (hayTriangulo && fila == ultimaFilaTriangulo && columna == ultimaColTriangulo
                        && tablero[fila][columna].equals(" ")) {
                    System.out.printf("%2s ", "□");
                } else {
                    System.out.printf("%2s ", tablero[fila][columna]);
                }
            }
            System.out.println();
            if (fila < cantFilas - 1) {
                System.out.println();
            }
        }
    }

    public boolean colocarBanda(Jugada jugada) {
        int logicalFila = jugada.getFila() - 1;
        int logicalCol = jugada.getColumna() - 'A';
        int fila = getRealFila(logicalFila);
        int columna = getRealColumna(logicalFila, logicalCol);
        char direccion = jugada.getDireccion();
        int cantidad = jugada.getCantidad();

        int deltaFila = 0;
        int deltaColumna = 0;
        String banda = "-";
        switch (direccion) {
            case 'Q':
                deltaFila = -1;
                deltaColumna = -1;
                banda = "\\";
                break;
            case 'E':
                deltaFila = -1;
                deltaColumna = 1;
                banda = "/";
                break;
            case 'D':
                deltaFila = 0;
                deltaColumna = 1;
                banda = "-";
                break;
            case 'C':
                deltaFila = 1;
                deltaColumna = 1;
                banda = "\\";
                break;
            case 'Z':
                deltaFila = 1;
                deltaColumna = -1;
                banda = "/";
                break;
            case 'A':
                deltaFila = 0;
                deltaColumna = -1;
                banda = "-";
                break;
            default:
                System.out.println("Dirección inválida.");
                return false;
        }

        if (!estaDentroLimites(fila, columna) || !tablero[fila][columna].equals("*")) {
            System.out.println("Posición inicial inválida.");
            return false;
        }

        int logicalFilaTemp = logicalFila;
        int logicalColTemp = logicalCol;
        int filaTemp = fila;
        int columnaTemp = columna;
        for (int i = 0; i < cantidad - 1; i++) {
            int nextLogicalFila = logicalFilaTemp + deltaFila;
            int nextLogicalCol = logicalColTemp + deltaColumna;
            int nextFila = getRealFila(nextLogicalFila);
            int nextColumna = getRealColumna(nextLogicalFila, nextLogicalCol);
            int bandFila = (filaTemp + nextFila) / 2;
            int bandColumna = (columnaTemp + nextColumna) / 2;
            if (direccion == 'D' || direccion == 'A') {
                int step = (nextColumna > columnaTemp) ? 1 : -1;
                for (int k = 1; k <= 3; k++) {
                    int bandCol = columnaTemp + k * step;
                    if (estaDentroLimites(filaTemp, bandCol) && !tablero[filaTemp][bandCol].equals("*")) {
                        tablero[filaTemp][bandCol] = "-";
                    }
                }
            } else {
                if (estaDentroLimites(bandFila, bandColumna) && !tablero[bandFila][bandColumna].equals("*")) {
                    tablero[bandFila][bandColumna] = banda;
                }
            }
            logicalFilaTemp = nextLogicalFila;
            logicalColTemp = nextLogicalCol;
            filaTemp = nextFila;
            columnaTemp = nextColumna;
        }
        return true;
    }

    private boolean estaDentroLimites(int fila, int columna) {
        return fila >= 0 && fila < cantFilas && columna >= 0 && columna < cantColumnas;
    }

    private String obtenerSimbolo(char direccion) {
        return switch (direccion) {
            case 'D', 'A' -> "-";
            case 'E', 'Z' -> "/";
            case 'Q', 'C' -> "\\";
            default -> "*";
        };
    }

    public String[][] getTablero() {
        return tablero;
    }

    public void marcarTriangulo(int fila, int columna) {
        this.ultimaFilaTriangulo = fila;
        this.ultimaColTriangulo = columna;
        this.hayTriangulo = true;
    }

    public void limpiarTriangulo() {
        this.hayTriangulo = false;
    }

    // Helper: map logical row/col to real indices
    public int getRealFila(int logicalFila) {
        return logicalFila * 2;
    }

    public int getRealColumna(int logicalFila, int logicalCol) {
        // logicalFila: 0-based, logicalCol: 0-based
        if (logicalFila < 0 || logicalFila >= puntosPorFila.length)
            return -1;
        if (logicalCol < 0 || logicalCol >= puntosPorFila[logicalFila].length)
            return -1;
        return puntosPorFila[logicalFila][logicalCol];
    }

    // Helper: find logicalCol for a given realCol in a logicalFila
    public int getLogicalCol(int logicalFila, int realCol) {
        for (int i = 0; i < puntosPorFila[logicalFila].length; i++) {
            if (puntosPorFila[logicalFila][i] == realCol)
                return i;
        }
        return -1;
    }

    // Helper: get real column for a logical column (first row where it appears)
    public int getRealColForHeader(int logicalCol) {
        for (int fila = 0; fila < filasLogicas; fila++) {
            if (logicalCol < puntosPorFila[fila].length) {
                return puntosPorFila[fila][logicalCol];
            }
        }
        return -1;
    }
}
