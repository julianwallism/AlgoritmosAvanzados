package capitulo7.control;

import static capitulo7.Error.informaError;
import capitulo7.PorEventos;
import capitulo7.main;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Control extends Thread implements PorEventos {

    private final main prog;
    private boolean executat = false, donali = true;

    public Control(main p) {
        this.prog = p;
    }

    @Override
    public void run() {
        while (true) {
            if (donali) {

            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                informaError(ex);
            }
        }
    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Ejecutar")) {
            if (!this.executat) {
                this.start();
                this.executat = true;
            } else {
                this.donali = true;
            }
        }
    }
}
