package dominio;

import java.util.ArrayList;

public class Partida {
  private final Tablero tablero;
  private final Jugador jugadorBlanco;
  private final Jugador jugadorNegro;
  private Jugador jugadorActual;
  private boolean partidaTerminada;
  private final int maxJugadas;
  private int jugadasRealizadas;
  private int puntajeBlanco;
  private int puntajeNegro;
  private ArrayList<String> historialJugadas;
  private boolean bandaLargaFija;

  public Partida(Jugador jugadorBlanco, Jugador jugadorNegro, int cantidadTablerosAMostrar,
      boolean permitirSuperposicionBandas, int maxJugadas, boolean bandaLargaFija) {
    this.tablero = new Tablero(cantidadTablerosAMostrar, permitirSuperposicionBandas);
    this.jugadorBlanco = jugadorBlanco;
    this.jugadorNegro = jugadorNegro;
    this.jugadorActual = jugadorBlanco; // El blanco siempre comienza
    this.partidaTerminada = false;
    this.maxJugadas = maxJugadas;
    this.jugadasRealizadas = 0;
    this.puntajeBlanco = 0;
    this.puntajeNegro = 0;
    this.historialJugadas = new ArrayList<>();
    this.bandaLargaFija = bandaLargaFija;
  }

  public boolean ejecutarJugada(Jugada jugada) {
    if (partidaTerminada || jugadasRealizadas >= maxJugadas) {
      return false;
    }

    if (tablero.colocarBanda(jugada)) {
      jugadasRealizadas++;
      String jugadaHistorial = String.format("%s: %s", jugadorActual.getNombre(), jugada.toString());
      historialJugadas.add(jugadaHistorial);

      ArrayList<Triangulo> triangulos = tablero.detectarTriangulos();
      if (!triangulos.isEmpty()) {
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

      // Guardar el estado del tablero después de cada jugada válida
      tablero.getVisualizadorTablero().setHistorialTableros(tablero.getHistorialTableros());
      tablero.guardarEstadoTablero();

      if (jugadasRealizadas < maxJugadas) {
        cambiarTurno();
      }
      return true;
    }
    return false;
  }

  private void cambiarTurno() {
    jugadorActual = (jugadorActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
  }

  public Jugador determinarGanador() {
    if (puntajeBlanco > puntajeNegro) {
      return jugadorBlanco;
    } else if (puntajeNegro > puntajeBlanco) {
      return jugadorNegro;
    }
    return null; // Empate
  }

  // Getters
  public Tablero getTablero() {
    return tablero;
  }

  public Jugador getJugadorBlanco() {
    return jugadorBlanco;
  }

  public Jugador getJugadorNegro() {
    return jugadorNegro;
  }

  public Jugador getJugadorActual() {
    return jugadorActual;
  }

  public boolean isPartidaTerminada() {
    return partidaTerminada;
  }

  public void terminarPartida() {
    this.partidaTerminada = true;
  }

  public int getMaxJugadas() {
    return maxJugadas;
  }

  public int getJugadasRealizadas() {
    return jugadasRealizadas;
  }

  public int getPuntajeBlanco() {
    return puntajeBlanco;
  }

  public int getPuntajeNegro() {
    return puntajeNegro;
  }

  public ArrayList<String> getHistorialJugadas() {
    return historialJugadas;
  }

  public boolean isBandaLargaFija() {
    return bandaLargaFija;
  }
}