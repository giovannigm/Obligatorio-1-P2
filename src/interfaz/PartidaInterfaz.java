package interfaz;

import java.util.Scanner;
import java.util.ArrayList;

import dominio.Jugada;
import dominio.JugadaParser;
import dominio.Jugador;
import dominio.Partida;

public class PartidaInterfaz {
  private final Partida partida;
  private final Scanner scanner;
  private static final int MIN_JUGADORES = 2;

  public PartidaInterfaz(Partida partida, Scanner scanner) {
    this.partida = partida;
    this.scanner = scanner;
  }

  public void iniciar() {
    mostrarInstrucciones();
    partida.getTablero().mostrarTablero();

    while (!partida.isPartidaTerminada()) {
      ejecutarTurno();
    }
  }

  private void mostrarInstrucciones() {
    System.out.println("\n==============================================");
    System.out.println("         INSTRUCCIONES PARA COLOCAR BANDAS");
    System.out.println("==============================================");
    System.out
        .println(" Formato de jugada: [Letra][Número][Dirección]" + (partida.isBandaLargaFija() ? "" : "[Longitud]"));
    System.out.println("   Ejemplo: " + (partida.isBandaLargaFija() ? "A1Q (coloca una banda en A1 en dirección Q)"
        : "A1Q3 (coloca una banda de longitud 3 en A1 en dirección Q)"));
    System.out.println("2. Las direcciones disponibles son:");
    System.out.println();
    System.out.println(" Direcciones disponibles:");
    System.out.println("   Q : Diagonal superior izquierda   (\\)");
    System.out.println("   E : Diagonal superior derecha     (/)");
    System.out.println("   D : Horizontal derecha            (-)");
    System.out.println("   C : Diagonal inferior derecha     (\\)");
    System.out.println("   Z : Diagonal inferior izquierda   (/)");
    System.out.println("   A : Horizontal izquierda          (-)");
    System.out.println();
    System.out.println(" Rango de coordenadas:");
    System.out.println("   Letras:  A a M");
    System.out.println("   Números: 1 a 7");
    System.out.println();
    System.out.println(" Otros comandos:");
    System.out.println("   H : Ver historial de jugadas");
    System.out.println("   X : Terminar la partida");
    System.out.println("==============================================\n");
  }

  private void ejecutarTurno() {
    if (partida.getJugadasRealizadas() >= partida.getMaxJugadas()) {
      determinarGanador();
      partida.terminarPartida();
      return;
    }

    System.out.println(
        "\nTurno de jugador: " + ((partida.getJugadorActual() == partida.getJugadorBlanco()) ? "blanco" : "negro"));
    System.out.println("Jugadas restantes: " + (partida.getMaxJugadas() - partida.getJugadasRealizadas()));
    System.out.println("Ingrese una jugada (ejemplo: A1Q o A1Q3), 'H' para ver historial, o 'X' para terminar:");

    String input = scanner.nextLine();
    if (input.equalsIgnoreCase("X")) {
      partida.terminarPartida();
      return;
    }

    if (input.equalsIgnoreCase("H")) {
      mostrarHistorial();
      return;
    }

    try {
      Jugada jugada = JugadaParser.interpretar(input, partida.isBandaLargaFija());

      if (partida.ejecutarJugada(jugada)) {
        System.out.println("Historial de tableros Partida: " + partida.getTablero().getHistorialTableros().size());
        System.out.println("\nTablero actualizado:");
        partida.getTablero().mostrarTablero();

        if (partida.getJugadasRealizadas() >= partida.getMaxJugadas()) {
          determinarGanador();
          partida.terminarPartida();
        }
      }
    } catch (Exception e) {
      System.out.println("Error al interpretar la jugada: " + e.getMessage());
    }
  }

  private void mostrarHistorial() {
    ArrayList<String> historial = partida.getHistorialJugadas();
    if (historial.isEmpty()) {
      System.out.println("\nNo hay jugadas registradas en el historial.");
      return;
    }

    System.out.println("\n=== Historial de Jugadas ===");
    for (int i = 0; i < historial.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, historial.get(i));
    }
    System.out.println("===========================");
  }

  private void determinarGanador() {
    System.out.println("\n=== FIN DE LA PARTIDA ===");
    System.out.println("Jugadas realizadas: " + partida.getJugadasRealizadas());
    System.out.println("Puntaje " + partida.getJugadorBlanco().getNombre() + ": " + partida.getPuntajeBlanco());
    System.out.println("Puntaje " + partida.getJugadorNegro().getNombre() + ": " + partida.getPuntajeNegro());

    Jugador ganador = partida.determinarGanador();
    if (ganador != null) {
      System.out.println("¡" + ganador.getNombre() + " es el ganador!");
      ganador.setPartidasGanadas(ganador.getPartidasGanadas() + 1);
    } else {
      System.out.println("¡Empate! Ambos jugadores formaron la misma cantidad de triángulos.");
    }
  }

  public static PartidaInterfaz crearPartida(ArrayList<Jugador> jugadores, Scanner scanner,
      int cantidadTablerosAMostrar,
      boolean permitirSuperposicionBandas, int maxJugadas, boolean bandaLargaFija) {
    if (jugadores.size() < MIN_JUGADORES) {
      throw new IllegalStateException(
          "Se necesitan al menos " + MIN_JUGADORES + " jugadores para iniciar una partida.");
    }

    System.out.println("\nJugadores disponibles:");
    for (int i = 0; i < jugadores.size(); i++) {
      System.out.println((i + 1) + ". " + jugadores.get(i).getNombre());
    }

    Jugador jugadorBlanco = seleccionarJugador(jugadores, scanner, "blanco", null);
    Jugador jugadorNegro = seleccionarJugador(jugadores, scanner, "negro", jugadorBlanco);

    System.out.println("\nJugador blanco: " + jugadorBlanco.getNombre());
    System.out.println("Jugador negro: " + jugadorNegro.getNombre());

    Partida partida = new Partida(jugadorBlanco, jugadorNegro, cantidadTablerosAMostrar,
        permitirSuperposicionBandas, maxJugadas, bandaLargaFija);
    return new PartidaInterfaz(partida, scanner);
  }

  private static Jugador seleccionarJugador(ArrayList<Jugador> jugadores, Scanner scanner,
      String color, Jugador excluido) {
    while (true) {
      System.out.print("Seleccione el número del jugador " + color + ": ");
      if (scanner.hasNextInt()) {
        int seleccion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (seleccion < 1 || seleccion > jugadores.size()) {
          System.out.println("Número inválido. Intente nuevamente.");
          continue;
        }

        Jugador seleccionado = jugadores.get(seleccion - 1);

        // Verificar que el jugador no esté excluido
        if (seleccionado == excluido) {
          System.out.println("Este jugador ya está seleccionado. Elija otro.");
          continue;
        }

        return seleccionado;
      } else {
        System.out.println("Entrada inválida. Por favor, ingrese un número.");
        scanner.nextLine(); // Limpiar buffer
      }
    }
  }
}
