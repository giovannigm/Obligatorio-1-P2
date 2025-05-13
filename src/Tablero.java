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

        // Definición de los puntos en filas pares
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
    public boolean colocarBanda(Jugada jugada) {
        int filaUsuario = jugada.getFila();
        int columna = jugada.getColumna();
        char direccion = jugada.getDireccion();
        int cantidad = jugada.getCantidad();

        // Validar que la fila del usuario esté en el rango correcto (1-7), esta
        // validacion deberia hacerse en el parser
        if (filaUsuario < 1 || filaUsuario > 7) {
            System.out.println("La fila debe estar entre 1 y 7.");
            return false;
        }

        // Convertir la fila del usuario a nuestra representación interna
        // Las filas pares en nuestra matriz (0,2,4,6,8,10,12) corresponden a las
        // filas 1-7 del usuario
        int fila = (filaUsuario - 1) * 2;

        // Validar que la columna esté en el rango correcto (A-M)
        if (columna < 0 || columna >= columnas) {
            System.out.println("La columna debe estar entre A y M.");
            return false;
        }

        // Validar que la posición inicial sea un punto "*"
        if (tablero[fila][columna] != '*') {
            System.out.println("Debe comenzar desde un punto.");
            return false;
        }

        int deltaFila = 0;
        int deltaColumna = 0;

        switch (direccion) {
            case 'D':
                deltaColumna = -2;
                break;
            case 'A':
                deltaColumna = 2;
                break;
            case 'E':
                deltaFila = 2;
                deltaColumna = -1;
                break;
            case 'Q':
                deltaFila = 2;
                deltaColumna = 1;
                break;
            case 'C':
                deltaFila = -2;
                deltaColumna = -1;
                break;
            case 'Z':
                deltaFila = -2;
                deltaColumna = 1;
                break;
            default:
                System.out.println("Dirección inválida.");
                return false;
        }

        // Posiciones para validación
        int filaValidacion = fila;
        int columnaValidacion = columna;

        // Validación de límites y colocación
        for (int i = 0; i < cantidad; i++) {
            // Actualizar posición de validación
            filaValidacion += deltaFila;
            columnaValidacion += deltaColumna;

            boolean fueraDeLimites = filaValidacion < 0 || filaValidacion >= filas || columnaValidacion < 0;

            if (fueraDeLimites) {
                System.out.println("La banda se sale del tablero.");
                return false;
            }

            // Calcular la posición donde se colocará la banda (en el espacio entre puntos)
            int filaBanda = (fila + filaValidacion) / 2;
            int columnaBanda = (columna + columnaValidacion) / 2;

            // Validar que la posición de la banda sea un espacio vacío
            if (tablero[filaBanda][columnaBanda] != ' ') {
                System.out.println("No se puede colocar banda sobre otra banda o punto.");
                return false;
            }

            // Validar que la siguiente posición sea un punto "*" o una banda válida
            if (i == cantidad - 1) { // Si es la última banda
                if (tablero[filaValidacion][columnaValidacion] != '*') {
                    System.out.println("La banda debe terminar en un punto.");
                    return false;
                }
            } else { // Si no es la última banda
                int siguienteFila = (filaValidacion + (filaValidacion + deltaFila)) / 2;
                int siguienteColumna = (columnaValidacion + (columnaValidacion + deltaColumna)) / 2;

                fueraDeLimites = siguienteFila < 0 || siguienteFila >= filas || siguienteColumna < 0;
                if (fueraDeLimites || tablero[siguienteFila][siguienteColumna] != ' ') {
                    System.out.println("La banda debe conectarse con otro punto o banda válida.");
                    return false;
                }
            }

            // Colocar la banda
            tablero[filaBanda][columnaBanda] = obtenerSimbolo(direccion);

            fila += deltaFila;
            columna += deltaColumna;
        }

        return true;
    }

    // Determina el símbolo correcto para la dirección
    private char obtenerSimbolo(char direccion) {
        return switch (direccion) {
            case 'D', 'A' -> '-';
            case 'Q', 'C' -> '\\';
            case 'E', 'Z' -> '/';
            default -> ' ';
        };
    }

    // Muestra el tablero con las letras de las columnas y los numeros de las filas
    public void mostrarTablero() {
        // Mostrar letras en la parte superior
        System.out.print("    ");
        for (char c = 'A'; c <= 'M'; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        // Mostrar el tablero
        for (int fila = 0; fila < filas; fila++) {
            if (fila % 2 == 0) {
                // Imprime el numero con un cero a la izquierda para evitar saltos de linea
                System.out.print(String.format("%02d", fila / 2 + 1) + "  ");
            } else {
                System.out.print("--   ");
            }
            for (int columna = 0; columna < columnas; columna++) {
                System.out.print(tablero[fila][columna] + " ");
            }
            System.out.println();
        }
    }
}
