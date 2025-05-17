// Trabajo desarrollado por: Nicolas(258264) y Giovanni(288127)
package interfaz;

import dominio.Sistema;

public class Inicio {
    public static void main(String[] args) {
        Sistema nuevoSistema = new Sistema();
        Interfaz consola = new Interfaz(nuevoSistema);
        consola.menu();
    }
}