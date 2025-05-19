package dominio;

import java.util.ArrayList;

import interfaz.VisualizadorTablero;

public class Tablero {
    private final char[][] tablero;
    private final int filas = 13;
    private final int columnas = 26; // Duplicado de 13
    private ValidadorBanda validadorBanda;
    private final DetectorTriangulo detectorTriangulo;
    private final VisualizadorTablero visualizadorTablero;
    private ArrayList<Triangulo> triangulosActivos;

    public Tablero(boolean permitirSuperposicionBandas) {
        this.tablero = new char[filas][columnas];
        this.triangulosActivos = new ArrayList<>();
        inicializarTablero();
        this.validadorBanda = new ValidadorBanda(tablero, filas, columnas, permitirSuperposicionBandas);
        this.detectorTriangulo = new DetectorTriangulo(tablero, filas, columnas);
        this.visualizadorTablero = new VisualizadorTablero(tablero, filas, columnas, triangulosActivos);
    }

    public void setPermitirSuperposicionBandas(boolean permitirSuperposicionBandas) {
        this.validadorBanda = new ValidadorBanda(tablero, filas, columnas, permitirSuperposicionBandas);
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
        int inicio = 6;
        int fin = 20;

        for (int fila = 0; fila < filas; fila++) {
            if (fila % 2 == 0) {
                for (int columna = inicio; columna <= fin && columna < columnas; columna += 4) {
                    tablero[fila][columna] = '*';
                }

                if (inicio > 0 && fila < (filas / 2)) {
                    inicio -= 2;
                } else {
                    inicio += 2;
                }
                if (fin < columnas && fila < (filas / 2)) {
                    fin += 2;
                } else {
                    fin -= 2;
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
        ArrayList<Triangulo> todosLosTriangulos = detectorTriangulo.detectarTriangulos();
        ArrayList<Triangulo> triangulosNuevos = new ArrayList<>();

        // Filtrar solo los triángulos nuevos
        for (Triangulo triangulo : todosLosTriangulos) {
            boolean esNuevo = true;
            for (Triangulo existente : triangulosActivos) {
                if (existente.getFilaPunto() == triangulo.getFilaPunto() &&
                        existente.getColumnaPunto() == triangulo.getColumnaPunto() &&
                        existente.isApuntaArriba() == triangulo.isApuntaArriba()) {
                    esNuevo = false;
                    break;
                }
            }

            if (esNuevo) {
                triangulosActivos.add(triangulo);
                triangulosNuevos.add(triangulo);
            }
        }

        return triangulosNuevos;
    }

    // Método para marcar un triángulo con el color del jugador
    public void marcarTriangulo(Triangulo triangulo, char color) {
        triangulo.setColor(color);
        // Actualizar el tablero con el color en el centro del triángulo
        // El centro está en la posición intermedia entre los puntos
        int filaCentro = triangulo.getFilaCentro();
        int columnaCentro = triangulo.getColumnaCentro();
        tablero[filaCentro][columnaCentro] = color;
    }

    // Método para obtener los triángulos activos
    public ArrayList<Triangulo> getTriangulosActivos() {
        return triangulosActivos;
    }

    // Muestra el tablero con las letras de las columnas y los numeros de las filas
    public void mostrarTablero() {
        visualizadorTablero.mostrarTablero();
    }
}
