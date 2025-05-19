package dominio;

import java.util.Scanner;
import java.util.ArrayList;

public class Partida {
  private final Tablero tablero;
  private final Jugador jugadorBlanco;
  private final Jugador jugadorNegro;
  private Jugador jugadorActual;
  private boolean partidaTerminada;
  private final Scanner scanner;
  private static final int MIN_JUGADORES = 2;
  private final int maxJugadas;
  private int jugadasRealizadas;
  private int puntajeBlanco;
  private int puntajeNegro;
  private ArrayList<String> historialJugadas;
  private boolean bandaLargaFija;

  public Partida(Jugador jugadorBlanco, Jugador jugadorNegro, Scanner scanner, int cantidadTablerosAMostrar,
      boolean permitirSuperposicionBandas, int maxJugadas, boolean bandaLargaFija) {
    this.tablero = new Tablero(cantidadTablerosAMostrar, permitirSuperposicionBandas);
    this.jugadorBlanco = jugadorBlanco;
    this.jugadorNegro = jugadorNegro;
    this.jugadorActual = jugadorBlanco; // El blanco siempre comienza
    this.partidaTerminada = false;
    this.scanner = scanner;
    this.maxJugadas = maxJugadas;
    this.jugadasRealizadas = 0;
    this.puntajeBlanco = 0;
    this.puntajeNegro = 0;
    this.historialJugadas = new ArrayList<>();
    this.bandaLargaFija = bandaLargaFija;
  }

  public void iniciar() {
    mostrarInstrucciones();
    tablero.mostrarTablero();

    while (!partidaTerminada) {
      ejecutarTurno();
    }
  }

  private void mostrarInstrucciones() {
    System.out.println("\nInstrucciones para colocar bandas:");
    System.out.println("1. El formato es: [Letra][Número][Dirección]" +
        (bandaLargaFija ? "" : "[Longitud]"));
    System.out.println("   Ejemplo: " + (bandaLargaFija ? "A1Q (coloca una banda en A1 en dirección Q)"
        : "A1Q3 (coloca una banda de longitud 3 en A1 en dirección Q)"));
    System.out.println("2. Las direcciones disponibles son:");
    System.out.println("   Q: Diagonal superior izquierda (\\)");
    System.out.println("   E: Diagonal superior derecha (/)");
    System.out.println("   D: Horizontal derecha (-)");
    System.out.println("   C: Diagonal inferior derecha (\\)");
    System.out.println("   Z: Diagonal inferior izquierda (/)");
    System.out.println("   A: Horizontal izquierda (-)");
    if (!bandaLargaFija) {
      System.out.println("3. La longitud de la banda puede ser de 1 a 4");
      System.out.println("   Si no se especifica, se usa longitud 4 por defecto");
    } else {
      System.out.println("3. La longitud de la banda está fija en 4");
    }
    System.out.println("\nNota: Las letras van de A a M y los números de 1 a 7");
  }

  private void ejecutarTurno() {
    if (jugadasRealizadas >= maxJugadas) {
      determinarGanador();
      partidaTerminada = true;
      return;
    }

    System.out.println("\nTurno de: " + jugadorActual.getNombre());
    System.out.println("Jugadas restantes: " + (maxJugadas - jugadasRealizadas));
    System.out.println("Ingrese una jugada (ejemplo: A1Q o A1Q3), 'H' para ver historial, o 'X' para terminar:");

    String input = scanner.nextLine();
    if (input.equalsIgnoreCase("X")) {
      partidaTerminada = true;
      return;
    }

    if (input.equalsIgnoreCase("H")) {
      mostrarHistorial();
    }

    try {
      Jugada jugada = JugadaParser.interpretar(input, bandaLargaFija);

      if (tablero.colocarBanda(jugada)) {
        jugadasRealizadas++;
        String jugadaHistorial = String.format("%s: %s", jugadorActual.getNombre(), jugada.toString());
        historialJugadas.add(jugadaHistorial);

        ArrayList<Triangulo> triangulos = tablero.detectarTriangulos();
        if (!triangulos.isEmpty()) {
          System.out.println("¡Felicidades " + jugadorActual.getNombre() + "! Formaste " +
              triangulos.size() + " triángulo(s)!");

          // Marcar los triángulos con el color del jugador actual
          char color = (jugadorActual == jugadorBlanco) ? '□' : '■';
          for (Triangulo triangulo : triangulos) {
            tablero.marcarTriangulo(triangulo, color);
            if (jugadorActual == jugadorBlanco) {
              puntajeBlanco++;
            } else {
              puntajeNegro++;
            }
          }
        }

        System.out.println("Historial de tableros Partida: " + tablero.getHistorialTableros().size());
        // Guardar el estado del tablero después de cada jugada válida
        tablero.getVisualizadorTablero().setHistorialTableros(tablero.getHistorialTableros());
        tablero.guardarEstadoTablero();

        System.out.println("\nTablero actualizado:");
        tablero.mostrarTablero();

        if (jugadasRealizadas >= maxJugadas) {
          determinarGanador();
          partidaTerminada = true;
        } else {
          cambiarTurno();
        }
      }
    } catch (Exception e) {
      System.out.println("Error al interpretar la jugada: " + e.getMessage());
    }
  }

  private void mostrarHistorial() {
    if (historialJugadas.isEmpty()) {
      System.out.println("\nNo hay jugadas registradas en el historial.");
      return;
    }

    System.out.println("\n=== Historial de Jugadas ===");
    for (int i = 0; i < historialJugadas.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, historialJugadas.get(i));
    }
    System.out.println("===========================");
  }

  private void cambiarTurno() {
    jugadorActual = (jugadorActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
  }

  private void determinarGanador() {
    System.out.println("\n=== FIN DE LA PARTIDA ===");
    System.out.println("Jugadas realizadas: " + jugadasRealizadas);
    System.out.println("Puntaje " + jugadorBlanco.getNombre() + ": " + puntajeBlanco);
    System.out.println("Puntaje " + jugadorNegro.getNombre() + ": " + puntajeNegro);

    if (puntajeBlanco > puntajeNegro) {
      System.out.println("¡" + jugadorBlanco.getNombre() + " es el ganador!");
      jugadorBlanco.setPartidasGanadas(jugadorBlanco.getPartidasGanadas() + 1);
    } else if (puntajeNegro > puntajeBlanco) {
      System.out.println("¡" + jugadorNegro.getNombre() + " es el ganador!");
      jugadorNegro.setPartidasGanadas(jugadorNegro.getPartidasGanadas() + 1);
    } else {
      System.out.println("¡Empate! Ambos jugadores formaron la misma cantidad de triángulos.");
    }
  }

  public static Partida crearPartida(ArrayList<Jugador> jugadores, Scanner scanner, int cantidadTablerosAMostrar,
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

    return new Partida(jugadorBlanco, jugadorNegro, scanner, cantidadTablerosAMostrar, permitirSuperposicionBandas,
        maxJugadas, bandaLargaFija);
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
