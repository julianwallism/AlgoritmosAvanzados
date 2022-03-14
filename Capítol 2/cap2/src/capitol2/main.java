package capitol2;

import capitol2.control.Control;
import capitol2.model.Model;
import capitol2.vista.Vista;

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
        vis = new Vista("Pràctica Capítol 2 - Dawid Roch i Julià Wallis", this);
        vis.mostrar();
    }

    public static void main(String[] args) {
        (new main()).inicio();
    }

    // Funció símple de la comunicació per Patró d'esdeveniments
    @Override
    public void notificar(String s) {
        
    }

    // Mètode public de retorn de la instància del model de dades
    public Model getModel() {
        return mod;
    }
}
