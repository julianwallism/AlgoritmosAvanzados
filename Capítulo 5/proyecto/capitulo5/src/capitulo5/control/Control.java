package capitulo5.control;

import capitulo5.PorEventos;
import capitulo5.main;

/**
 *
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Control extends Thread implements PorEventos {
    private final main prog;

    public Control(main p) {
        this.prog = p;
    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        
    }
}
