package capitol2.control;

import capitol2.main;
import capitol2.PerEsdeveniments;
import capitol2.model.Tauler;
import capitol2.model.Peces.Peça;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Control extends Thread implements PerEsdeveniments {

    private final main prog;
    private boolean seguir, executat;
    private Tauler tauler;
    private int x, y;
    private int[] movX, movY;

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
   
    // Métode encarregat de fer les comprovacions d'error, cridar al métode 
    // de resolució backtracking i notificar al programa principal dels outputs 
    // obtinguts
    private void resol() {
        Peça p = this.prog.getModel().getPeçaTriada();
        tauler = this.prog.getModel().getTauler();
        // Obtenim posició inicial de la peça
        x = this.prog.getModel().getX();
        y = this.prog.getModel().getY();
        if (x == -1 || y == -1) {
            prog.notificar("Error: PI");
            prog.notificar("Aturar");
        } else {
            // Obtenim moviments de la peça
            movX = p.getMovimentsX();
            movY = p.getMovimentsY();
            tauler.setCasella(x, y, 1);
            long inici = System.nanoTime();
            if (BT(x, y, 2)) {
                prog.getModel().setTime((System.nanoTime() - inici) / 1000000000.0);
                prog.notificar("Solució si");
            } else if (this.seguir) {
                prog.notificar("Solució no");
            }
        }
    }
    
    // Métode backtracking que soluciona el problema, donat una posició i el 
    // nombre de moviments que du
    private boolean BT(int x, int y, int mov) {
        if (this.seguir) {
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
                    tauler.setCasella(prox_x, prox_y, mov);
                    if (BT(prox_x, prox_y, mov + 1)) {
                        return true;
                    } else {
                        tauler.clearCasella(prox_x, prox_y); // tornada enrera
                    }
                }
            }
            this.prog.getModel().setTauler(tauler);
        }
        return false;
    }
}
