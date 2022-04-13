package capitol3.vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import capitol3.MeuError;
import capitol3.model.Model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class PanellGrafic extends JPanel {

    private int w;
    private int h;
    private Model mod;
    private Vista vis;
    protected final int FPS = 24; // 24 frames per segon
    //private final ProcesPintat procpin;
    private BufferedImage bima;

    public PanellGrafic(int x, int y, Model m, Vista v) {
        w = x;
        h = y;
        mod = m;
        vis = v;
        bima = null;
        this.setPreferredSize(new Dimension(w, h));
        /*procpin = new ProcesPintat(this);
        procpin.start();*/
    }

    @Override
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    @Override
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

    public void pintaGrafic() {
        Graphics2D grap = (Graphics2D) this.getGraphics();
        grap.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double[][] estudi = mod.getEstudi();
        double[] tradicional = estudi[0];
        double[] karatsuba = estudi[1];
        //get mad max value from array
        double max = 0;
        for (int i = 0; i < tradicional.length; i++) {
            if (tradicional[i] > max) {
                max = tradicional[i];
            }
        }
        for (int i = 0; i < karatsuba.length; i++) {
            if (karatsuba[i] > max) {
                max = karatsuba[i];
            }
        }
        grap.setColor(Color.BLUE);
        double x = 0;
        int y = 0;
        grap.drawLine(100, 100, 200, 200);
        for (int i = 0; i < tradicional.length; i++) {
            grap.drawLine((int) Math.floor((x / max) * this.getHeight()), (int) (((double) y / 500) * this.getWidth()), (int) Math.floor((tradicional[i] / max) * this.getHeight()), (int) (((double) i / 500) * this.getWidth()));
            x = tradicional[i];
            y = i;
            System.out.println((int) (((double) y / 500) * this.getWidth()));
            System.out.println("x: " + (int) Math.floor((x / max) * this.getHeight()) + " y: " + (int) (((double) y / 500) * this.getWidth()));
        }

        grap.setColor(Color.GREEN);
        x = 0;
        y = 0;
        for (int i = 0; i < karatsuba.length; i++) {
            grap.drawLine((int) (x / max) * this.getHeight(), y, (int) (karatsuba[i] / max) * this.getHeight(), i);
            x = karatsuba[i];
            y = i;
        }
    }

    /*class ProcesPintat extends Thread {

    private PanellGrafic pan;

    public ProcesPintat(PanellGrafic pd) {
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
    }*/
}
