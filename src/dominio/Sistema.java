// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package dominio;

import java.util.ArrayList;

public class Sistema {
    private ArrayList<Jugador> misJugadores;

    public Sistema() {
        // inicializo las listas
        misJugadores = new ArrayList<Jugador>();

    }

    public void agregarJugador(Jugador unJugador) {
        misJugadores.add(unJugador);
    }

    public ArrayList<Jugador> getJugadores() {
        return misJugadores;
    }

    public boolean validarNombre(String nombre) {
        // Devuelve true si el nombre NO existe en la lista de jugadores
        for (Jugador jugador : misJugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                return false; // El nombre ya existe
            }
        }
        return true; // El nombre es único
    }
}
