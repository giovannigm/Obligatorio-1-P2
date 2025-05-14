import java.util.ArrayList;
import models.Posicion;
import models.Triangulo;

public class Tablero {
    private final char[][] tablero;
    private final int filas = 13;
    private final int columnas = 13;
    private final ValidadorBanda validadorBanda;
    private final DetectorTriangulo detectorTriangulo;
    private final VisualizadorTablero visualizadorTablero;

    public Tablero() {
        this.tablero = new char[filas][columnas];
        inicializarTablero();
        this.validadorBanda = new ValidadorBanda(tablero, filas, columnas);
        this.detectorTriangulo = new DetectorTriangulo(tablero, filas, columnas);
        this.visualizadorTablero = new VisualizadorTablero(tablero, filas, columnas);
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
        ArrayList<Posicion> posiciones = validadorBanda.validarJugada(jugada);

        if (posiciones == null || posiciones.isEmpty()) {
            return false;
        }

        // Colocar todas las bandas
        for (Posicion pos : posiciones) {
            tablero[pos.getFila()][pos.getColumna()] = pos.getSimbolo();
        }

        return true;
    }

    // Método para detectar triángulos en el tablero
    public ArrayList<Triangulo> detectarTriangulos() {
        return detectorTriangulo.detectarTriangulos();
    }

    // Muestra el tablero con las letras de las columnas y los numeros de las filas
    public void mostrarTablero() {
        visualizadorTablero.mostrarTablero();
    }
}
