public class JuegoTriangulos {
    public static void main(String[] args) {
        // Acá llamaremos al menú y arrancaremos el juego
        System.out.println("Bienvenidos al juego Triángulos!");

        // Crear y mostrar el tablero
        Tablero tablero = new Tablero();
        System.out.println("\nTablero inicial:");
        tablero.mostrarTablero();
    }
}
