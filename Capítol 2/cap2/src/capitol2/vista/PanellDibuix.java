package capitol2.vista;

import java.awt.Graphics;
import javax.swing.JPanel;
import capitol2.MeuError;
import capitol2.model.Model;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class PanellDibuix extends JPanel {

    private int w;
    private int h;
    private final static int N = 8;
    private Model mod;
    private Vista vis;
    protected final int FPS = 24;  // 24 frames per segon
    private final ProcesPintat procpin;

    public PanellDibuix(int x, int y, Model m, Vista v) {
        this.w = x;
        this.h = y;
        this.mod = m;
        this.vis = v;
        this.setPreferredSize(new Dimension(w, h));
        procpin = new ProcesPintat(this);
        procpin.start();
    }

    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    public void paint(Graphics gr) {
        // Pintar fons
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, w, h);
        // Pintar tauler
        for (int i = 0; i < this.mod.getTauler().length; i++) {
            for (int j = 0; j < this.mod.getTauler()[0].length; j++) {
                gr.setColor(this.mod.getTauler()[i][j].getColor());
                gr.fillRect(i*(this.w/N), j*(this.h)/N, (this.w)/N, (this.h)/N);
            }
        }
    }
}

class ProcesPintat extends Thread {
    private PanellDibuix pan;

    public ProcesPintat(PanellDibuix pd) {
        pan = pd;
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
