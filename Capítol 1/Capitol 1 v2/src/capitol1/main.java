package capitol1;

import capitol1.control.Control;
import capitol1.model.Model;
import capitol1.vista.Vista.Vista;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class main implements PerEsdeveniments {
    private Model mod;    // Punter al Model
    private Vista vis;    // Punter a la Vista
    private Control con;  // Punter al Control

    // Construcció de l'esquema MVC
    private void inicio() {
        mod = new Model(this);
        con = new Control(this);
        vis = new Vista("Pràctica capítol 1 - Dawid Roch i Julià Wallis", this);
        vis.mostrar();
    }

    public static void main(String[] args) {
        (new main()).inicio();
    }

    // Funció símple de la comunicació per Patró d'esdeveniments
    @Override
    public void notificar(String s) {
        if (s.startsWith("Executar")) {
            con.notificar(s);
        } else if (s.startsWith("Event iter")) {
            vis.notificar(s);
        } else if (s.startsWith("Aturar")) {
            con.notificar(s);
            vis.notificar(s);
        }
    }

    // Mètode public de retorn de la instància del model de dades
    public Model getModel() {
        return mod;
    }
}
