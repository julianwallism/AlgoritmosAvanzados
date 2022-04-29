package capitol4.vista;

import capitol4.MeuError;
import capitol4.PerEsdeveniments;
import capitol4.main;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class PanellCentral extends JPanel implements PerEsdeveniments {
    public static final int FPS = 24;
    private main p;

    public PanellCentral(main p, int width, int height) {
        this.p = p;
        this.setBackground(Color.WHITE);
        this.setVisible(true);
    }

    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    public void paint(Graphics gr) {
        super.paint(gr);
    }

    @Override
    public void notificar(String s) {
        
    }
}

class ProcesPintat extends Thread {
    private PanellCentral pan;

    public ProcesPintat(PanellCentral pc) {
        pan = pc;
    }

    public void run() {
        long temps = System.nanoTime();
        long tram = 1000000000L / pan.FPS;
        while (true) {
            if ((System.nanoTime() - temps) > tram) {
                pan.repaint();
                temps = System.nanoTime();
                espera((long) (tram / 2000000));
            }
        }
    }

    private void espera(long t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {
            MeuError.informaError(e);
        }
    }
}
