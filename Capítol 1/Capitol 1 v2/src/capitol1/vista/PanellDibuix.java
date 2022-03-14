package capitol1.vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import capitol1.MeuError;
import capitol1.model.Model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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
        gr.setColor(Color.red);
        gr.fillRect(0, 0, 5, this.getHeight());
        gr.fillRect(0, this.getHeight() - 5, this.getWidth(), 5);
        // Aquests dos fors afegeix els strokes al gràfics.
        for (int i = 1; i <= 8; i++) {
            gr.fillRect(i * (this.getWidth() / 8) - 10, this.getHeight() - 10, 4, 5);
        }
        for (int i = 1; i <= 8; i++) {
            gr.fillRect(5, i * (this.getHeight() / 8) - 15, 5, 4);
        }
        gr.setColor(Color.black);
    }

    public void pintaGrafic(int n, double temps, int nAnterior, double tempsAnterior, int c) {
        Graphics2D grap = (Graphics2D) this.getGraphics();
        grap.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = new Color(c);
        grap.setColor(color);
        grap.fillOval((n / 2) * (this.getWidth() / 8) - 10, this.getHeight() - (int) (this.getHeight() * (temps)) - 3, 6, 6);
        grap.drawLine((nAnterior / 2) * (this.getWidth() / 8) - 10,
                this.getHeight() - (int) (this.getHeight() * (tempsAnterior)),
                (n / 2) * (this.getWidth() / 8) - 10,
                this.getHeight() - (int) (this.getHeight() * (temps)));
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
