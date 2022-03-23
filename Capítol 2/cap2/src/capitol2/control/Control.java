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

    //nou
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
            this.seguir = false;
            System.out.println("Programa aturat");
        }
    }

    private void resol() {
        peça = prog.getModel().getPeçaTriada();
        movX = peça.getMovimentsX();
        movY = peça.getMovimentsY();

        tauler = prog.getModel().getTauler();
        tauler.clear();

        x = prog.getModel().getX();
        y = prog.getModel().getY();

        System.out.println("X: " + x + ", Y: " + y);
        if (x == -1 || y == -1) {
            prog.notificar("Error: PI");

        } else {
            tauler.setCasella(x, y, 1);
            if (BT(x, y, 2)) {
                prog.notificar("Solució si");
            } else {
                prog.notificar("Solució no");
            }
        }
    }

    private boolean BT(int x, int y, int mov) {
        int prox_x, prox_y;
        if (mov > tauler.getDim() * tauler.getDim()) {
            tauler.setCasella(x, y, tauler.getDim() * tauler.getDim());
            return true;
        }
        for (int k = 0; k < movX.length; k++) {
            prox_x = x + movX[k];
            prox_y = y + movY[k];
            if (prox_x < tauler.getDim() && prox_x >= 0
                    && prox_y >= 0 && prox_y < tauler.getDim()
                    && tauler.getCasella(prox_x, prox_y) == 0) {
                System.out.println("Prox X: " + prox_x + ", Prox Y: " + prox_y + ", Mov: " + mov);
                tauler.setCasella(prox_x, prox_y, mov);
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
