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
    private int[] movX, movY;
    private Tauler tauler = null;
    private Casella[][] caselles = null;
    private int x, y;

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
        this.movX = this.peça.getMovimentsX();
        this.movY = this.peça.getMovimentsY();
        this.tauler = this.prog.getModel().getTauler();
        this.x = this.prog.getModel().getX();
        this.y = this.prog.getModel().getY();
        System.out.println("X: " + x + ", Y: " + y);
        if (BT(x, y, 1)) {
            prog.notificar("Solució si");       
        } else {
            prog.notificar("Solució no");
        }
    }

    private boolean BT(int x, int y, int mov) {
        int prox_x, prox_y;
        if (mov == tauler.getDim() * tauler.getDim()) {
            return true;
        }
        for (int k = 0; k < movX.length; k++) {
            prox_x = x + movX[k];
            prox_y = y + movY[k];
            if (prox_x < tauler.getDim() && prox_x >= 0
                    && prox_y >= 0 && prox_y < tauler.getDim()
                    && tauler.getCasella(prox_x, prox_y) == 0) {
                System.out.println("Prox X: " + prox_x + ", Prox Y: " + prox_y + ", Mov: " + mov);
                tauler.setCasella(x, y, mov);
                if (BT(prox_x, prox_y, mov + 1)) {
                    return true;
                } else {
                    tauler.clearCasella(prox_x, prox_y);  // backtracking
                }
            }
        }
        return false;
    }
}
