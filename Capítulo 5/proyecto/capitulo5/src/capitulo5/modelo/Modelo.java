package capitulo5.modelo;

import capitulo5.PorEventos;
import capitulo5.main;

/**
 *
 * @authors Dawid Roch, Julià Wallis y Víctor Blanes
 */
public class Modelo implements PorEventos {
    private main prog;

    public Modelo(main p) {
        prog = p;
    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        
    }
}