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
  private static final int MAX_JUGADAS = 10;
  private int jugadasRealizadas;
  private int puntajeBlanco;
  private int puntajeNegro;
  private ArrayList<String> historialJugadas;

  public Partida(Jugador jugadorBlanco, Jugador jugadorNegro, Scanner scanner, boolean permitirSuperposicionBandas) {
    this.tablero = new Tablero(permitirSuperposicionBandas);
    this.jugadorBlanco = jugadorBlanco;
    this.jugadorNegro = jugadorNegro;
    this.jugadorActual = jugadorBlanco; // El blanco siempre comienza
    this.partidaTerminada = false;
    this.scanner = scanner;
    this.jugadasRealizadas = 0;
    this.puntajeBlanco = 0;
    this.puntajeNegro = 0;
    this.historialJugadas = new ArrayList<>();
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
    System.out.println("1. El formato es: [Letra][Número][Dirección]");
    System.out.println("   Ejemplo: A1Q (coloca una banda en A1 en dirección Q)");
    System.out.println("2. Las direcciones disponibles son:");
    System.out.println("   Q: Diagonal superior izquierda (\\)");
    System.out.println("   E: Diagonal superior derecha (/)");
    System.out.println("   D: Horizontal derecha (-)");
    System.out.println("   C: Diagonal inferior derecha (\\)");
    System.out.println("   Z: Diagonal inferior izquierda (/)");
    System.out.println("   A: Horizontal izquierda (-)");
    System.out.println("3. También puedes especificar la longitud: [Letra][Número][Dirección][Longitud]");
    System.out.println("   Ejemplo: A1Q3 (coloca una banda de longitud 3 en A1 en dirección Q)");
    System.out.println("\nNota: Las letras van de A a M y los números de 1 a 7");
  }

  private void ejecutarTurno() {
    if (jugadasRealizadas >= MAX_JUGADAS) {
      System.out.println("Se acabaron las jugadas 1");
      determinarGanador();
      partidaTerminada = true;
      return;
    }

    System.out.println("\nTurno de: " + jugadorActual.getNombre());
    System.out.println("Jugadas restantes: " + (MAX_JUGADAS - jugadasRealizadas));
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
      Jugada jugada = JugadaParser.interpretar(input);

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

        System.out.println("\nTablero actualizado:");
        tablero.mostrarTablero();

        if (jugadasRealizadas >= MAX_JUGADAS) {
          System.out.println("Se acabaron las jugadas 2");
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

  public static Partida crearPartida(ArrayList<Jugador> jugadores, Scanner scanner,
      boolean permitirSuperposicionBandas) {
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

    return new Partida(jugadorBlanco, jugadorNegro, scanner, permitirSuperposicionBandas);
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
