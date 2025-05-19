// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package dominio;

import java.util.ArrayList;

public class Sistema {
    private ArrayList<Jugador> misJugadores;
    private int cantidadTablerosAMostrar;
    private boolean permitirSuperposicionBandas;

    public Sistema() {
        misJugadores = new ArrayList<Jugador>();
        this.cantidadTablerosAMostrar = 1;
        this.permitirSuperposicionBandas = true;
    }

    public void setCantidadTablerosAMostrar(int cantidad) {
        if (cantidad < 1 || cantidad > 3) {
            throw new IllegalArgumentException("La cantidad de tableros debe estar entre 1 y 3");
        }
        this.cantidadTablerosAMostrar = cantidad;
    }

    public int getCantidadTablerosAMostrar() {
        return cantidadTablerosAMostrar;
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
