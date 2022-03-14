package capitol2.vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
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
    private Model mod;
    private Vista vis;
    protected final int FPS = 24;  // 24 frames per segon
    private final ProcesPintat procpin;
    private BufferedImage bima;

    public PanellDibuix(int x, int y, Model m, Vista v) {
        w = x;
        h = y;
        mod = m;
        vis = v;
        bima = null;
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
        // Dibuixar rectangles
        if (bima == null) {
            bima = new BufferedImage(this.w, this.h, BufferedImage.TYPE_INT_ARGB);
            bima.getGraphics().setColor(Color.white);
            bima.getGraphics().fillRect(0, 0, this.w, this.h);
            gr.drawImage(bima, 0, 0, this);
        }
        gr.setColor(Color.BLACK);
        int n = this.mod.getTamanyTriat();
        int x = 0, y = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                gr.fillRect(j*x, i*y, this.w/n, this.h/n);
                x += j * this.w/n;
            }
            y += i * this.h/n;
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
