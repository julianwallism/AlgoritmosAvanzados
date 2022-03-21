package capitol2.vista;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import capitol2.MeuError;
import capitol2.model.Model;
import java.awt.Color;

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
    private JLabel[][] tauler;
    protected final int FPS = 24;  // 24 frames per segon
    private final ProcesPintat procpin;

    public PanellDibuix(int x, int y, Model m, Vista v) {
        this.w = x;
        this.h = y;
        this.mod = m;
        this.vis = v;
        this.tauler = new JLabel[this.N][this.N];
        this.setPreferredSize(new Dimension(w, h));
        initCasillas();
        procpin = new ProcesPintat(this);
        procpin.start();
    }

    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    public void paint(Graphics gr) {
        // Dibuixar rectangles
        for (int i = 0; i < tauler.length; i++) {
            for (int j = 0; j < tauler[0].length; j++) {
                gr.setColor(tauler[i][j].getBackground());
                gr.fillRect(i*(this.w/N), j*(this.h)/N, (this.w)/N, (this.h)/N);
            }
        }
    }

    private void initCasillas() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tauler[i][j] = new JLabel();
                if ((i + j) % 2 == 0) {
                    tauler[i][j].setBackground(Color.WHITE);
                } else {
                    tauler[i][j].setBackground(Color.BLACK);
                }
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
