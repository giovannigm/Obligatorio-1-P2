import java.util.Scanner;
import java.util.ArrayList;
import clase.Jugador;

public class JuegoTriangulos {
    private static final int MAX_JUGADORES = 10;
    private static ArrayList<Jugador> jugadores = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        jugadores.add(new Jugador("Jugador Blanco", 20));
        jugadores.add(new Jugador("Jugador Negro", 20));

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    registrarJugador();
                    break;
                case 2:
                    jugarPartida();
                    break;
                case 3:
                    mostrarJugadores();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=== JUEGO DE TRIÁNGULOS ===");
        System.out.println("1. Registrar jugador");
        System.out.println("2. Jugar partida");
        System.out.println("3. Mostrar jugadores registrados");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return opcion;
    }

    private static void registrarJugador() {
        if (jugadores.size() >= MAX_JUGADORES) {
            System.out.println("No se pueden registrar más jugadores. Límite alcanzado.");
            return;
        }

        System.out.print("Ingrese el nombre del jugador: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        // Verificar si el jugador ya existe
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Ya existe un jugador con ese nombre.");
                return;
            }
        }

        System.out.print("Ingrese la edad del jugador (opcional, presione Enter para omitir): ");
        String edadInput = scanner.nextLine().trim();

        Jugador nuevoJugador;
        if (edadInput.isEmpty()) {
            nuevoJugador = new Jugador(nombre);
        } else {
            try {
                int edad = Integer.parseInt(edadInput);
                nuevoJugador = new Jugador(nombre, edad);
            } catch (NumberFormatException e) {
                System.out.println("Edad inválida. Se creará el jugador sin edad.");
                nuevoJugador = new Jugador(nombre);
            }
        }

        jugadores.add(nuevoJugador);
        System.out.println("Jugador registrado exitosamente.");
    }

    private static void jugarPartida() {
        try {
            Partida partida = Partida.crearPartida(jugadores, scanner);
            partida.iniciar();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void mostrarJugadores() {
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        System.out.println("\nJugadores registrados:");
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println((i + 1) + ". " + jugadores.get(i));
        }
    }
}
