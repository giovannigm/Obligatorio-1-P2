import java.util.Scanner;

public class JuegoTriangulos {
    public static void main(String[] args) {
        // Acá llamaremos al menú y arrancaremos el juego
        System.out.println("Bienvenidos al juego Triángulos!");

        // Crear y mostrar el tablero
        Tablero tablero = new Tablero();
        System.out.println("\nTablero inicial:");
        tablero.mostrarTablero();

        // Probar el parser de jugadas
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nIngrese una jugada (ejemplo: A1Q o A1Q3):");
        String input = scanner.nextLine();

        try {
            Jugada jugada = JugadaParser.interpretar(input);
            System.out.println("Jugada interpretada: " + jugada);
        } catch (Exception e) {
            System.out.println("Error al interpretar la jugada: " + e.getMessage());
        }

        scanner.close();
    }
}
