import java.util.Scanner;

public class JuegoTriangulos {
    public static void main(String[] args) {
        // Acá llamaremos al menú y arrancaremos el juego
        System.out.println("Bienvenidos al juego Triángulos!");

        // Crear un Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Pedir el nombre del jugador
        System.out.print("Ingrese el nombre del jugador: ");
        String nombre = scanner.nextLine();

        // Pedir la edad del jugador
        System.out.print("Ingrese la edad del jugador: ");
        int edad = scanner.nextInt();

        // Crear un objeto Jugador
        Jugador jugador = new Jugador(nombre, edad);

        // Mostrar un mensaje de confirmación
        System.out.println("Jugador creado: " + jugador.getNombre() + ", Edad: " + jugador.getEdad());

        // Cerrar el Scanner
        scanner.close();
    }
}
