import java.util.Scanner;

public class JuegoTriangulos {
    public static void main(String[] args) {
        // Mostrar mensaje de bienvenida rodeado por un cuadrado
        System.out.println(" ");
        System.out.println("+-----------------------------+");
        System.out.println("| Bienvenidos al juego       |");
        System.out.println("| Triángulos!                |");
        System.out.println("+-----------------------------+");

        // Crear un Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Crear un arreglo para almacenar hasta dos jugadores
        Jugador[] jugadores = new Jugador[2];

        for (int i = 0; i < 2; i++) {
            System.out.println("\nJugador " + (i + 1) + ":");

            // Pedir el nombre del jugador
            System.out.print("Ingrese el nombre del jugador: ");
            String nombre = scanner.nextLine();

            // Pedir la edad del jugador con validación
            int edad = -1;
            while (edad <= 0) {
                System.out.print("Ingrese la edad del jugador (solo números): ");
                if (scanner.hasNextInt()) {
                    edad = scanner.nextInt();
                    if (edad < 0) {
                        System.out.println("La edad no puede ser negativa. Intente nuevamente.");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor, ingrese un número.");
                    scanner.next(); // Consumir la entrada inválida
                }
            }
            scanner.nextLine(); // Consumir el salto de línea

            // Crear un objeto Jugador y almacenarlo en el arreglo
            jugadores[i] = new Jugador(nombre, edad);

            // Mostrar un mensaje de confirmación
            System.out.println("Jugador creado");
        }

        // Cerrar el Scanner después de leer los datos
        scanner.close();

        // Mostrar los datos de todos los jugadores
        System.out.println("\nJugadores registrados:");
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println("Jugador " + (i + 1) + ": " + jugadores[i].getNombre() + " , Edad: " + jugadores[i].getEdad());
        }
    }
}
