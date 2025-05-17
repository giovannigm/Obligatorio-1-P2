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

  public Partida(Jugador jugadorBlanco, Jugador jugadorNegro, Scanner scanner) {
    this.tablero = new Tablero();
    this.jugadorBlanco = jugadorBlanco;
    this.jugadorNegro = jugadorNegro;
    this.jugadorActual = jugadorBlanco; // El blanco siempre comienza
    this.partidaTerminada = false;
    this.scanner = scanner;
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
    System.out.println("\nTurno de: " + jugadorActual.getNombre());
    System.out.println("Ingrese una jugada (ejemplo: A1Q o A1Q3) o 'salir' para terminar:");

    String input = scanner.nextLine();
    if (input.equalsIgnoreCase("salir")) {
      partidaTerminada = true;
      return;
    }

    try {
      Jugada jugada = JugadaParser.interpretar(input);

      if (tablero.colocarBanda(jugada)) {
        ArrayList<Triangulo> triangulos = tablero.detectarTriangulos();
        if (!triangulos.isEmpty()) {
          System.out.println("¡Felicidades " + jugadorActual.getNombre() + "! Formaste " +
              triangulos.size() + " triángulo(s)!");

          // Marcar los triángulos con el color del jugador actual
          char color = (jugadorActual == jugadorBlanco) ? '□' : '■';
          for (Triangulo triangulo : triangulos) {
            tablero.marcarTriangulo(triangulo, color);
            jugadorActual.incrementarPuntaje(1); // Incrementar puntaje por cada triángulo
          }
        }

        System.out.println("\nTablero actualizado:");
        tablero.mostrarTablero();
        cambiarTurno();
      }
    } catch (Exception e) {
      System.out.println("Error al interpretar la jugada: " + e.getMessage());
    }
  }

  private void cambiarTurno() {
    jugadorActual = (jugadorActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
  }

  public static Partida crearPartida(ArrayList<Jugador> jugadores, Scanner scanner) {
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

    return new Partida(jugadorBlanco, jugadorNegro, scanner);
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
