package capitol2.control;

import capitol2.main;
import capitol2.model.Peces.Peça;
import capitol2.model.Tauler;
import capitol2.model.Casella;
import capitol2.PerEsdeveniments;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Control extends Thread implements PerEsdeveniments {

    private final main prog;
    private boolean seguir, executat;
    private Peça peça = null;
    private Tauler tauler = null;
    private Casella[][] caselles = null;

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
            System.out.println("Programa aturat");
            this.seguir = false;
        }
    }

    private void resol() {
        this.peça = this.prog.getModel().getPeçaTriada();
        this.tauler = this.prog.getModel().getTauler();
        this.caselles = this.tauler.getCaselles();
    }
}
