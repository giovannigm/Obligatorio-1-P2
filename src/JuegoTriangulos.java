import java.util.Scanner;

import clase.Jugador;

public class JuegoTriangulos {
    private static final int MAX_JUGADORES = 2;
    private static Jugador[] jugadores = new Jugador[MAX_JUGADORES];
    private static int jugadoresRegistrados = 0;

    public static void main(String[] args) {
        // Mostrar mensaje de bienvenida rodeado por un cuadrado
        System.out.println(" ");
        System.out.println("=> Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127) <=");
        System.out.println(" ");
        System.out.println("+----------------------------------+");
        System.out.println("| Bienvenidos al juego triángulos! |");
        System.out.println("+----------------------------------+");
        System.out.println(" ");

        // Mostrar el menú principal usando el método mostrarMenu
        mostrarMenu();

        // Crear un Scanner para leer la entrada
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese una opción: ");

        int opcion = -1;
        while (opcion != 5) {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                switch (opcion) {
                    case 1:
                        registrarJugador(scanner);
                        // Aquí puedes agregar la lógica para registrar jugadores
                        break;
                    case 2:
                        System.out.println("Opción 2 seleccionada: Configurar partida");
                        // Aquí puedes agregar la lógica para configurar la partida
                        break;
                    case 3:
                        System.out.println("Opción 3 seleccionada: Jugar partida");
                        // Aquí puedes agregar la lógica para jugar la partida
                        break;
                    case 4:
                        System.out.println("Opción 4 seleccionada: Mostrar ranking");
                        // Aquí puedes agregar la lógica para mostrar el ranking
                        break;
                    case 5:
                        System.out.println("Saliendo del juego. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, ingrese un número entre 1 y 5.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next(); // Consumir la entrada inválida
            }
            if (opcion != 5) {
                System.out.println("");
                mostrarMenu();
                System.out.println("\nIngrese una opción: ");
            }
        }
        scanner.close();

    }

    public static void mostrarMenu() {
        System.out.println(" --- MENÚ PRINCIPAL ---");
        System.out.println("1. Registrar jugador");
        System.out.println("2. Configurar partida");
        System.out.println("3. Jugar partida");
        System.out.println("4. Mostrar ranking");
        System.out.println("5. Salir");
        System.out.println("----------------------");
    }

    public static void registrarJugador(Scanner scanner) {
        if (jugadoresRegistrados >= MAX_JUGADORES) {
            System.out.println("");
            System.out.println("*-------------------------------------------*");
            System.out.println("No se pueden registrar más de dos jugadores.");
            System.out.println("*-------------------------------------------*");
            return;
        }
        System.out.println("");

        // Pedir el nombre del jugador y verificar unicidad
        String nombre;
        boolean nombreUnico;
        do {
            nombreUnico = true;
            System.out.print("Ingrese el nombre del Jugador: ");
            nombre = scanner.nextLine();

            // Verificar si el nombre ya existe
            for (int i = 0; i < jugadoresRegistrados; i++) {
                if (jugadores[i].getNombre().equalsIgnoreCase(nombre)) {
                    System.out.println("El nombre ya está en uso. Por favor, ingrese un nombre único.");
                    nombreUnico = false;
                    break;
                }
            }
        } while (!nombreUnico);

        // Pedir la edad del jugador con validación
        int edad = -1;
        while (edad <= 0) {
            System.out.print("Ingrese la edad del jugador (solo números): ");
            if (scanner.hasNextInt()) {
                edad = scanner.nextInt();
                if (edad <= 0) {
                    System.out.println("La edad debe ser un número positivo mayor que cero. Intente nuevamente.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next(); // Consumir la entrada inválida
            }
        }
        scanner.nextLine(); // Consumir el salto de línea

        // Crear un objeto Jugador y almacenarlo en el arreglo
        jugadores[jugadoresRegistrados] = new Jugador(nombre, edad);
        jugadoresRegistrados++;

        // Mostrar un mensaje de confirmación
        System.out.println("Jugador creado");

        // Mostrar los datos de todos los jugadores registrados
        System.out.println("\nJugadores registrados:");
        for (int i = 0; i < jugadoresRegistrados; i++) {
            System.out.println(
                    "Jugador " + (i + 1) + ": " + jugadores[i].getNombre() + " , Edad: " +
                            jugadores[i].getEdad());
        }
    }
}
