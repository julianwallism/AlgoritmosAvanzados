package capitulo5.control;

import capitulo5.PorEventos;
import capitulo5.main;

/**
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
        if (s.startsWith("Corregir texto")) {
            this.corregirTexto();
        } else if (s.startsWith("Texto guardado")) {
            this.analizarTexto();
        }
    }
    
    private void corregirTexto() {}
    
    private void analizarTexto() {}
}
