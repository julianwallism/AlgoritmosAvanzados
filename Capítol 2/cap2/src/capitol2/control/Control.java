package capitol2.control;

import capitol2.main;
import capitol2.PerEsdeveniments;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Control extends Thread implements PerEsdeveniments {
    private final main prog;
    private boolean seguir, executat;

    public Control(main p) {
        this.prog = p;
        this.executat = false;
    }

    @Override
    public void run() {
        this.executat = true;
        while (true) {
            while (this.seguir) {
                // bla bla bla
                this.seguir = false;
            }
        }
    }

    @Override
    public void notificar(String s) {
        
    }
}
