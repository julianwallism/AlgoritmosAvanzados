package capitol4.control;

import capitol4.main;
import capitol4.PerEsdeveniments;

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
            if (this.seguir) {
                resol();
                this.seguir = false;
            }
        }
    }

    private void resol() {
        
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Comprimir")) {
            this.seguir = true;
            if (!this.executat) this.start();
            System.out.println("Compressió iniciada");
        } else if (s.startsWith("Aturar")) {
            this.seguir = false;
            System.out.println("Programa aturat");
        }
    }
}
