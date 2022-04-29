package capitol4;

import capitol4.control.Control;
import capitol4.model.Model;
import capitol4.vista.Vista;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class main implements PerEsdeveniments {
    private Model mod; // Punter al Model
    private Vista vis; // Punter a la Vista
    private Control con; // Punter al Control

    // Construcció de l'esquema MVC
    private void inicio() {
        mod = new Model(this);
        con = new Control(this);
        vis = new Vista("Pràctica Capítol 4 - Dawid Roch i Julià Wallis", this);
    }

    public static void main(String[] args) {
        (new main()).inicio();
    }

    // Funció símple de la comunicació per Patró d'esdeveniments
    @Override
    public void notificar(String s) {
        if (s.startsWith("Fitxer") || s.startsWith("Compresion realizada")) {
            vis.notificar(s);
        } else if (s.startsWith("Comprime") || s.startsWith("Descomprime") || s.startsWith("Aturar")) {
            con.notificar(s);
        } 
    }

    // Mètode public de retorn de la instància del model de dades
    public Model getModel() {
        return mod;
    }
}
