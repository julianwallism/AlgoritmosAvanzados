package capitol2.control;

import capitol2.MeuError;
import capitol2.main;
import capitol2.PerEsdeveniments;
import capitol2.model.Casella;
import capitol2.model.Peces.Peça;
import java.util.Random;

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
        Casella[][] tauler = this.prog.getModel().getTauler();
        int x = this.prog.getModel().getX();
        int y = this.prog.getModel().getY();

        // Posar totes les caselles a no visitades menys l'inicial
        if (x != -1 && y != -1) {
            for (int i = 0; i < tauler.length; i++) {
                for (int j = 0; j < tauler.length; j++) {
                    if (i == x && j == y) {
                        tauler[i][j].setVisitada(true);
                    } else {
                        tauler[i][j].setVisitada(false);
                        tauler[i][j].setOrdre(new Random().nextInt(tauler.length*tauler.length));
                    }
                }
            }
            this.prog.getModel().setTauler(tauler);

            // Algorisme backtracking per recòrrer el tauler
            this.prog.notificar("Solució trobada");
        } else {
            this.prog.notificar("Error: PI");
        }
    }
}
