// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package dominio;

import java.util.ArrayList;

public class Sistema {
    private ArrayList<Jugador> misJugadores;
    private boolean permitirSuperposicionBandas;

    public Sistema() {
        misJugadores = new ArrayList<Jugador>();
        this.permitirSuperposicionBandas = true;
    }

    public void agregarJugador(Jugador unJugador) {
        misJugadores.add(unJugador);
    }

    public ArrayList<Jugador> getJugadores() {
        return misJugadores;
    }

    public boolean validarNombre(String nombre) {
        for (Jugador jugador : misJugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPermitirSuperposicionBandas() {
        return permitirSuperposicionBandas;
    }

    public void setPermitirSuperposicionBandas(boolean permitirSuperposicionBandas) {
        this.permitirSuperposicionBandas = permitirSuperposicionBandas;
    }
}
