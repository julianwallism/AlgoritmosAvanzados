package capitulo5.vista;

import static capitulo5.Error.informaError;
import capitulo5.main;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
 *
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class PanelCentral extends JPanel {
    public static final int FPS = 24;
    private main p;

    public PanelCentral(main p) {
        this.p = p;
        this.setVisible(true);
    }

    @Override
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
    }
}

class ProcesPintat extends Thread {
    private PanelCentral pan;

    public ProcesPintat(PanelCentral pc) {
        pan = pc;
    }

    @Override
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
        } catch (InterruptedException e) {
            informaError(e);
        }
    }
}
