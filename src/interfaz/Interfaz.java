// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package interfaz;

import java.util.ArrayList;
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
                    configurarPartida();
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
        System.out.println("║  2. ⚙️  Configurar partida                  ║");
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

    public void registrarJugador() {
        String nombre = ingresarTexto("👤 Ingrese el nombre del Jugador:");

        do {
            if (!sistema.validarNombre(nombre)) {
                System.out.println("⚠️  El nombre ya existe. Por favor, ingrese un nombre diferente para el jugador.");
                nombre = ingresarTexto("👤 Ingrese el nombre del Jugador:");
            }
        } while (!sistema.validarNombre(nombre));
        int edad = ingresarNumero("🎂 Ingrese la edad del Jugador:", 1, 100);
        Jugador nuevoJugador = new Jugador(nombre, edad);
        this.sistema.agregarJugador(nuevoJugador);
    }

    public void mostrarRanking() {
        if (sistema.getJugadores().isEmpty()) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║    🚫 ¡No hay jugadores registrados! 🚫    ║");
            System.out.println("╚════════════════════════════════════════════╝");
        } else {
            // Ordenar jugadores por partidas ganadas (de mayor a menor)
            ArrayList<Jugador> jugadoresOrdenados = new ArrayList<>(sistema.getJugadores());
            jugadoresOrdenados.sort((j1, j2) -> Integer.compare(j2.getPartidasGanadas(), j1.getPartidasGanadas()));

            // Calcular la racha actual más alta
            int rachaMaxima = 0;
            ArrayList<Jugador> jugadoresConRachaMaxima = new ArrayList<>();
            for (Jugador j : jugadoresOrdenados) {
                int racha = j.getRachaActual();
                if (racha > rachaMaxima) {
                    rachaMaxima = racha;
                    jugadoresConRachaMaxima.clear();
                    jugadoresConRachaMaxima.add(j);
                } else if (racha == rachaMaxima && rachaMaxima > 0) {
                    jugadoresConRachaMaxima.add(j);
                }
            }

            System.out.println("╔════════════════════════════════════════════════════════════════════╗");
            System.out.println("║           🏆 RANKING DE JUGADORES                                 ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════╣");
            int totalJugadores = jugadoresOrdenados.size();
            int contador = 0;
            for (Jugador unJugador : jugadoresOrdenados) {
                contador++;
                System.out.printf("║ %-2d. %-15s │ Partidas Ganadas: %-2d │ Racha Actual: %-2d ║%n",
                        contador, unJugador.getNombre(), unJugador.getPartidasGanadas(), unJugador.getRachaActual());
                if (contador < totalJugadores) {
                    System.out.println("╠" + "═".repeat(66) + "╣");
                }
            }
            System.out.println("╚" + "═".repeat(66) + "╝");

            // Mostrar el/los jugador/es con la racha actual más larga
            if (rachaMaxima > 0 && !jugadoresConRachaMaxima.isEmpty()) {
                System.out.print("\n🔥 Racha ganadora actual más larga: " + rachaMaxima + " partida(s) consecutivas. Jugador(es): ");
                for (int i = 0; i < jugadoresConRachaMaxima.size(); i++) {
                    System.out.print(jugadoresConRachaMaxima.get(i).getNombre());
                    if (i < jugadoresConRachaMaxima.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            } else {
                System.out.println("\nNo hay rachas ganadoras actuales.");
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

        try {
            Partida partida = Partida.crearPartida(sistema.getJugadores(), input,
                    sistema.getCantidadTablerosAMostrar(),
                    sistema.isPermitirSuperposicionBandas(),
                    sistema.getMaxJugadas(),
                    sistema.isBandaLargaFija());
            partida.iniciar();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void configurarPartida() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║           ⚙️  CONFIGURACIÓN DE PARTIDA      ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  1. Configurar cantidad de tableros        ║");
        System.out.println("║  2. Configurar superposición de bandas     ║");
        System.out.println("║  3. Configurar cantidad máxima de jugadas  ║");
        System.out.println("║  4. Configurar longitud de bandas          ║");
        System.out.println("║  5. Volver al menú principal               ║");
        System.out.println("╚════════════════════════════════════════════╝");

        int opcion = ingresarNumero("Ingrese una opción:", 1, 5);
        if (opcion == 1) {
            configurarCantidadTableros();
        }
        if (opcion == 2) {
            configurarSuperposicionBandas();
        }
        if (opcion == 3) {
            configurarMaxJugadas();
        }
        if (opcion == 4) {
            configurarLongitudBandas();
        }
    }

    private void configurarCantidadTableros() {
        System.out.println("\nConfiguración de cantidad de tableros");
        System.out.println("Actualmente se muestran " + sistema.getCantidadTablerosAMostrar() + " tablero(s)");
        System.out.println("Puede configurar entre 1 y 3 tableros");

        try {
            int cantidad = ingresarNumero("Ingrese la cantidad de tableros a mostrar:", 1, 3);
            sistema.setCantidadTablerosAMostrar(cantidad);
            System.out.println("✅ Configuración guardada: se mostrarán " + cantidad + " tablero(s)");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void configurarSuperposicionBandas() {
        System.out.println("\nConfiguración de superposición de bandas:");
        System.out.println("Actualmente: " + (sistema.isPermitirSuperposicionBandas() ? "Permitida" : "No permitida"));
        System.out.println("1. Permitir superposición de bandas");
        System.out.println("2. No permitir superposición de bandas");
        System.out.println("3. Volver");

        int opcion = ingresarNumero("Ingrese una opción:", 1, 3);
        switch (opcion) {
            case 1:
                sistema.setPermitirSuperposicionBandas(true);
                System.out.println("✅ Superposición de bandas permitida.");
                break;
            case 2:
                sistema.setPermitirSuperposicionBandas(false);
                System.out.println("✅ Superposición de bandas no permitida.");
                break;
            case 3:
                return;
        }
    }

    private void configurarMaxJugadas() {
        System.out.println("\nConfiguración de cantidad máxima de jugadas");
        System.out.println("Actualmente se permiten " + sistema.getMaxJugadas() + " jugadas por partida");
        System.out.println("Ingrese un número mayor a 0 para la cantidad máxima de jugadas");

        try {
            int cantidad = ingresarNumero("Ingrese la cantidad máxima de jugadas:", 1, Integer.MAX_VALUE);
            sistema.setMaxJugadas(cantidad);
            System.out.println("✅ Configuración guardada: se permitirán " + cantidad + " jugadas por partida");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void configurarLongitudBandas() {
        System.out.println("\nConfiguración de longitud de bandas:");
        System.out.println(
                "Actualmente: " + (sistema.isBandaLargaFija() ? "Longitud fija de 4" : "Longitud variable (1-4)"));
        System.out.println("1. Longitud fija de 4");
        System.out.println("2. Longitud variable (1-4)");
        System.out.println("3. Volver");

        int opcion = ingresarNumero("Ingrese una opción:", 1, 3);
        switch (opcion) {
            case 1:
                sistema.setBandaLargaFija(true);
                System.out.println("✅ Longitud de bandas configurada a fija (4).");
                break;
            case 2:
                sistema.setBandaLargaFija(false);
                System.out.println("✅ Longitud de bandas configurada a variable (1-4).");
                break;
            case 3:
                return;
        }
    }
}
