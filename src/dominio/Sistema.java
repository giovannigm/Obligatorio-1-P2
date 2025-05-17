// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Sistema {
    private ArrayList<Jugador> misJugadores;

    public Sistema() {
        // inicializo las listas
        misJugadores = new ArrayList<Jugador>();

    }

    public void agregarJugador(Jugador unJugador) {
        misJugadores.add(unJugador);
    }

    public List<Jugador> getJugadores() {
        return Collections.unmodifiableList(misJugadores);
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
