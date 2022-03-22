package capitol2.control;

import capitol2.main;
import capitol2.PerEsdeveniments;
import capitol2.model.Peces.Peça;

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
                resol();
                this.seguir = false;
            }
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Resoldre")) {
            this.seguir = true;
            if (!this.executat) {
                this.start();
            }
        } else if (s.startsWith("Aturar")) {
            this.seguir = false;
            System.out.println("Programa aturat");
        }
    }

    private void resol() {
        Peça p = this.prog.getModel().getPeçaTriada();
        int x = this.prog.getModel().getX();
        int y = this.prog.getModel().getY();
        
        // Algorisme backtracking per recòrrer el tauler
        
    }
}
