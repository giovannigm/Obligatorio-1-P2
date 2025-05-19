// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package dominio;

import java.util.ArrayList;

public class Sistema {
    private ArrayList<Jugador> misJugadores;
    private int cantidadTablerosAMostrar;
    private boolean permitirSuperposicionBandas;
    private int maxJugadas;
    private boolean bandaLargaFija;

    public Sistema() {
        misJugadores = new ArrayList<Jugador>();
        this.cantidadTablerosAMostrar = 1;
        this.permitirSuperposicionBandas = true;
        this.maxJugadas = 10;
        this.bandaLargaFija = true;
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

    public int getMaxJugadas() {
        return maxJugadas;
    }

    public void setMaxJugadas(int maxJugadas) {
        if (maxJugadas <= 0) {
            throw new IllegalArgumentException("La cantidad máxima de jugadas debe ser mayor a 0");
        }
        this.maxJugadas = maxJugadas;
    }

    public boolean isBandaLargaFija() {
        return bandaLargaFija;
    }

    public void setBandaLargaFija(boolean bandaLargaFija) {
        this.bandaLargaFija = bandaLargaFija;
    }
}
