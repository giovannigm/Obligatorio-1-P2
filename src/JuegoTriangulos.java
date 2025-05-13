import java.util.Scanner;
import clase.Jugador;

public class JuegoTriangulos {
    private static final int MIN_JUGADORES = 2;
    private static Jugador[] jugadores = new Jugador[10]; // Capacidad inicial de 10 jugadores
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

        // Agregar jugadores iniciales para pruebas
        jugadores[0] = new Jugador("Jugador1", 20);
        jugadores[1] = new Jugador("Jugador2", 25);
        jugadoresRegistrados = 2;

        // Mostrar el menú principal usando el método mostrarMenu
        mostrarMenu();

        // Crear un Scanner para leer la entrada
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una opción: ");

        int opcion = -1;
        while (opcion != 5) {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                switch (opcion) {
                    case 1:
                        registrarJugador(scanner);
                        break;
                    case 2:
                        System.out.println("Opción 2 seleccionada: Configurar partida");
                        // Aquí puedes agregar la lógica para configurar la partida
                        break;
                    case 3:
                        jugarPartida(scanner);
                        break;
                    case 4:
                        mostrarRanking();
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
                System.out.print("\nIngrese una opción: ");
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
        if (jugadoresRegistrados >= jugadores.length) {
            System.out.println("\n*-------------------------------------------*");
            System.out.println("Se alcanzó el límite de jugadores registrados.");
            System.out.println("*-------------------------------------------*");
            return;
        }
        System.out.println("");

        // Pedir el nombre del jugador y verificar unicidad
        String nombre;
        boolean esNombreUnico;
        do {
            esNombreUnico = true;
            System.out.print("Ingrese el nombre del Jugador: ");
            nombre = scanner.nextLine();

            // Verificar si el nombre ya existe
            for (int i = 0; i < jugadoresRegistrados; i++) {
                if (jugadores[i].getNombre().equalsIgnoreCase(nombre)) {
                    System.out.println("El nombre ya está en uso. Por favor, ingrese un nombre único.");
                    esNombreUnico = false;
                    break;
                }
            }
        } while (!esNombreUnico);

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

    public static void jugarPartida(Scanner scanner) {
        // Crear un tablero para la partida usando la nueva clase Tablero
        Tablero tablero = new Tablero();
        tablero.mostrarTablero(); // Mostrar el tablero inicial

        // if (jugadoresRegistrados >= MIN_JUGADORES) {
        // System.out.println("\nGENIAL!!, vamos a Jugar");
        // System.out.println("\nInstrucciones para colocar bandas:");
        // System.out.println("1. El formato es: [Letra][Número][Dirección]");
        // System.out.println(" Ejemplo: A1Q (coloca una banda en A1 en dirección Q)");
        // System.out.println("2. Las direcciones disponibles son:");
        // System.out.println(" Q: Diagonal superior izquierda (\\)");
        // System.out.println(" E: Diagonal superior derecha (/)");
        // System.out.println(" D: Horizontal derecha (-)");
        // System.out.println(" C: Diagonal inferior derecha (\\)");
        // System.out.println(" Z: Diagonal inferior izquierda (/)");
        // System.out.println(" A: Horizontal izquierda (-)");
        // System.out.println("3. También puedes especificar la longitud:
        // [Letra][Número][Dirección][Longitud]");
        // System.out.println(" Ejemplo: A1Q3 (coloca una banda de longitud 3 en A1 en
        // dirección Q)");
        // System.out.println("\nNota: Las letras van de A a M y los números de 1 a 7");

        // // Mostrar jugadores registrados
        // System.out.println("\nJugadores registrados:");
        // for (int i = 0; i < jugadoresRegistrados; i++) {
        // System.out.println((i + 1) + ". " + jugadores[i].getNombre());
        // }

        // // Seleccionar jugadores
        // int jugadorBlanco = -1;
        // int jugadorNegro = -1;

        // while (jugadorBlanco < 1 || jugadorBlanco > jugadoresRegistrados) {
        // System.out.print("Seleccione el número del jugador blanco: ");
        // if (scanner.hasNextInt()) {
        // jugadorBlanco = scanner.nextInt();
        // if (jugadorBlanco < 1 || jugadorBlanco > jugadoresRegistrados) {
        // System.out.println("Número inválido. Intente nuevamente.");
        // }
        // } else {
        // System.out.println("Entrada inválida. Por favor, ingrese un número.");
        // scanner.next(); // Consumir la entrada inválida
        // }
        // }

        // while (jugadorNegro < 1 || jugadorNegro > jugadoresRegistrados ||
        // jugadorNegro == jugadorBlanco) {
        // System.out.print("Seleccione el número del jugador negro: ");
        // if (scanner.hasNextInt()) {
        // jugadorNegro = scanner.nextInt();
        // if (jugadorNegro < 1 || jugadorNegro > jugadoresRegistrados) {
        // System.out.println("Número inválido. Intente nuevamente.");
        // } else if (jugadorNegro == jugadorBlanco) {
        // System.out.println(
        // "El jugador negro no puede ser el mismo que el jugador blanco. Intente
        // nuevamente.");
        // }
        // } else {
        // System.out.println("Entrada inválida. Por favor, ingrese un número.");
        // scanner.next(); // Consumir la entrada inválida
        // }
        // }

        // // Mostrar selección final
        // System.out.println("\nJugador blanco: " + jugadores[jugadorBlanco -
        // 1].getNombre());
        // System.out.println("Jugador negro: " + jugadores[jugadorNegro -
        // 1].getNombre());

        // // Lógica de turnos
        // int turno = 0; // 0: blanco, 1: negro
        boolean continuar = true;
        // scanner.nextLine(); // Limpiar buffer
        while (continuar) {
            // String jugadorActual = (turno == 0) ? jugadores[jugadorBlanco -
            // 1].getNombre()
            // : jugadores[jugadorNegro - 1].getNombre();
            // System.out.println("\nTurno de: " + jugadorActual);
            System.out.println("Ingrese una jugada (ejemplo: A1Q o A1Q3) o 'salir' para terminar:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("salir")) {
                continuar = false;
                continue;
            }

            try {
                Jugada jugada = JugadaParser.interpretar(input);
                System.out.println("Jugada interpretada: " + jugada);

                if (tablero.colocarBanda(jugada.getFila(), jugada.getColumna(), jugada.getDireccion(),
                        jugada.getCantidad())) {
                    // Detección de triángulo: verifica si la última jugada forma un triángulo
                    // if (detectarTrianguloSimple(tablero, jugada)) {
                    // // Marcar el triángulo en el tablero
                    // int fila = (jugada.getFila() - 1) * 2;
                    // int columna = (jugada.getColumna() - 'A') * 2;
                    // tablero.marcarTriangulo(fila, columna);
                    // // System.out.println("¡Felicidades " + jugadorActual + "! Formaste
                    // // untriángulo!");
                    // }
                    System.out.println("\nTablero actualizado:");
                    tablero.mostrarTablero();
                    // Cambiar de turno
                    // turno = 1 - turno;
                }
            } catch (Exception e) {
                System.out.println("Error al interpretar la jugada: " + e.getMessage());
            }
            // }
            // } else {
            // System.out.println("\nNo hay suficientes jugadores registrados para jugar.");
            // System.out.println("Por favor, registre al menos " + MIN_JUGADORES + "
            // jugadores antes de jugar.");
        }
    }

    // Detección de triángulo: verifica si la última jugada forma un triángulo con
    // cualquier par de celdas adyacentes (ahora solo marca el centro en un espacio
    // entre puntos)
    // private static boolean detectarTrianguloSimple(Tablero tablero, Jugada
    // jugada) {
    // int fila = (jugada.getFila() - 1) * 2;
    // int columna = (jugada.getColumna() - 'A') * 2;
    // String[][] grid = tablero.getTablero();
    // int maxFila = grid.length;
    // int maxCol = grid[0].length;
    // // Direcciones de adyacencia en hexágono (6 direcciones, saltando a los
    // puntos
    // // visibles)
    // int[][] dirs = {
    // { -2, -2 }, // arriba-izquierda
    // { -2, 2 }, // arriba-derecha
    // { 0, -4 }, // izquierda
    // { 0, 4 }, // derecha
    // { 2, -2 }, // abajo-izquierda
    // { 2, 2 } // abajo-derecha
    // };
    // // Buscar todos los posibles centros de triángulo (espacios entre puntos)
    // for (int d = 0; d < dirs.length; d++) {
    // int f1 = fila + dirs[d][0];
    // int c1 = columna + dirs[d][1];
    // int f2 = fila + dirs[(d + 1) % dirs.length][0];
    // int c2 = columna + dirs[(d + 1) % dirs.length][1];
    // int centerFila = (fila + f1 + f2) / 3;
    // int centerCol = (columna + c1 + c2) / 3;
    // // El centro debe estar en un espacio (índices impares)
    // if (centerFila >= 0 && centerFila < maxFila && centerCol >= 0 && centerCol <
    // maxCol && centerFila % 2 == 1
    // && centerCol % 2 == 1) {
    // // Verificar que haya bandas en los tres lados
    // if (!grid[(fila + f1) / 2][(columna + c1) / 2].equals("*")
    // && !grid[(fila + f1) / 2][(columna + c1) / 2].equals(" ") &&
    // !grid[(fila + f2) / 2][(columna + c2) / 2].equals("*")
    // && !grid[(fila + f2) / 2][(columna + c2) / 2].equals(" ") &&
    // !grid[(f1 + f2) / 2][(c1 + c2) / 2].equals("*")
    // && !grid[(f1 + f2) / 2][(c1 + c2) / 2].equals(" ")) {
    // tablero.marcarTriangulo(centerFila, centerCol);
    // return true;
    // }
    // }
    // }
    // return false;
    // }

    public static void mostrarRanking() {
        if (jugadoresRegistrados >= MIN_JUGADORES) {
            System.out.println("Ranking de jugadores");
            System.out.println("");
            // Calcular el ancho máximo del nombre de los jugadores
            int maxNombreLength = 0;
            for (int i = 0; i < jugadoresRegistrados; i++) {
                maxNombreLength = Math.max(maxNombreLength, jugadores[i].getNombre().length());
            }

            // Mostrar el ranking con formato centrado
            for (int i = 0; i < jugadoresRegistrados; i++) {
                String nombre = jugadores[i].getNombre();
                int espacios = maxNombreLength - nombre.length();
                String padding = " ".repeat(espacios);
                System.out.println((i + 1) + ". " + nombre + padding + " |" + jugadores[i].getPartidasGanadas());
            }
        } else {
            System.out.println("");
            System.out.println("Por favor, registre al menos " + MIN_JUGADORES + " jugadores antes.");
        }
    }
}
