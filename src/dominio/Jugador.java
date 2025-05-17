// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package dominio;

public class Jugador {
    private String nombre;
    private int edad;
    private int partidasGanadas;
    private int rachaActual;
    private int puntaje;

    // Constructor
    public Jugador(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.partidasGanadas = 0;
        this.rachaActual = 0;
        this.puntaje = 0;
    }

    // Constructor simplificado para mantener compatibilidad
    public Jugador(String nombre) {
        this(nombre, 0); // Edad por defecto 0 si no se especifica
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public int getRachaActual() {
        return rachaActual;
    }

    public void setRachaActual(int rachaActual) {
        this.rachaActual = rachaActual;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void incrementarPuntaje(int puntos) {
        this.puntaje += puntos;
    }

    @Override
    public String toString() {
        return String.format("%s (Edad: %d, Partidas: %d, Puntaje: %d)",
                nombre, edad, partidasGanadas, puntaje);
    }
}