// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package interfaz;

import java.util.Scanner;

import dominio.Jugador;
import dominio.Sistema;

public class Interfaz {
    private Sistema sistema;
    private Scanner input;

    public Interfaz(Sistema unSistema) {
        this.sistema = unSistema;
        this.input = new Scanner(System.in);
    }

    public void menu() {
        mensajeBienvenida();
        int opcion = -1;
        while (opcion != 5) {
            menuPrincipal();
            opcion = ingresarNumero("Ingrese una opción:", 1, 5);
            switch (opcion) {
                case 1:
                    System.out.println("");
                    registrarJugador();
                    break;
                case 2:
                    System.out.println("");
                    System.out.println("Opción 2 seleccionada: Configurar partida");
                    // Aquí puedes agregar la lógica para configurar la partida
                    break;
                case 3:
                    System.out.println("");
                    jugarPartida();
                    break;
                case 4:
                    System.out.println("");
                    mostrarRanking();
                    break;
                case 5:
                    System.out.println("Saliendo del juego. ¡Hasta luego!");
                    break;
            }
        }
    }

    public void menuPrincipal() {
        System.out.println(" ");
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║              🌟 MENÚ PRINCIPAL 🌟          ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  1. 🧑 Registrar jugador                   ║");
        System.out.println("║  2. ⚙️ Configurar partida                   ║");
        System.out.println("║  3. 🎮 Jugar partida                       ║");
        System.out.println("║  4. 🏆 Mostrar ranking                     ║");
        System.out.println("║  5. 🚪 Salir                               ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println(" ");
    }

    public void mensajeBienvenida() {
        System.out.println(" ");
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         ¡Bienvenido a Triángulos!        ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║   Disfruta del juego y mucha suerte.     ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println(" ");
    }

    public int ingresarNumero(String mensaje, int min, int max) {
        int opcion;
        while (true) {
            System.out.print(mensaje + " ");
            try {
                System.out.print("👉 ");
                opcion = Integer.parseInt(input.nextLine().trim());
                if (opcion >= min && opcion <= max) {
                    return opcion;
                } else {
                    System.out.println("⚠️  Por favor, ingrese un número entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("😅 ¡Eso no es un número! Intenta de nuevo, que tú puedes.");
            }
        }
    }

    public String ingresarTexto(String mensaje) {
        System.out.print(mensaje + " ");
        return input.nextLine().trim();
    }

    public String registrarJugador() {
        String nombre = ingresarTexto("👤 Ingrese el nombre del Jugador:");

        do {
            if (!sistema.validarNombre(nombre)) {
                System.out.println("⚠️  El nombre ya existe. Por favor, ingrese un nombre diferente para el jugador.");
                nombre = ingresarTexto("Ingrese el nombre del Jugador:");
            }
        } while (!sistema.validarNombre(nombre));
        int edad = ingresarNumero("🎂 Ingrese la edad del Jugador:", 1, 100);
        Jugador nuevoJugador = new Jugador(nombre, edad);
        this.sistema.agregarJugador(nuevoJugador);

        return nombre;
    }

    public void mostrarRanking() {
        if (sistema.getJugadores().isEmpty()) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║    🚫 ¡No hay jugadores registrados! 🚫    ║");
            System.out.println("╚════════════════════════════════════════════╝");
        } else {
            System.out.println("╔═════════════════════════════════════════════════╗");
            System.out.println("║           🏆 RANKING DE JUGADORES               ║");
            System.out.println("╠═════════════════════════════════════════════════╣");
            // System.out.printf("║ %-25s ││ %-25s ║%n", "Nombre", "Partidas Ganadas");
            for (Jugador unJugador : sistema.getJugadores()) {
                System.out.printf("║      %-15s ││   Partidas Ganadas: %-2s  ║%n", unJugador.getNombre(),
                        unJugador.getPartidasGanadas());
                System.out.println("╠" + "═".repeat(49) + "╣");
            }
        }
    }

    public void jugarPartida() {
        // quiero que como minimo tenga 2 jugadores para jugar
        if (sistema.getJugadores().size() < 2) {
            System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
            System.out.println("║ 🚫 ¡Se necesitan al menos 2 jugadores registrados para jugar! 🚫  ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
            return;
        }
        System.out.println("Empezando partida...");

        // Note for NICO: here you should call 'Sistema' to play the game, remember that
        // in the Interfaz only goes the 'System.out.println' so to say.

        // Write it in english because I want to and I can .
        // (NAAA mentira es para practicar)
    }
}
