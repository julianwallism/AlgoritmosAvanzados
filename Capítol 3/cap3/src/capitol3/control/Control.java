package capitol3.control;

import capitol3.main;
import capitol3.PerEsdeveniments;

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
                // resoldre
                this.seguir = false;
            }
        }
    }

    @Override
    public void notificar(String s) {
        
    }
}
