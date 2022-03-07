package capitol1.vista.Vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import capitol1.MeuError;
import capitol1.PerEsdeveniments;
import capitol1.model.Model;
import java.awt.Color;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class PanellDibuix extends JPanel implements PerEsdeveniments {

    private int w;
    private int h;
    private Model mod;
    private Vista vis;
    protected final int FPS = 144;  // 24 frames per segon
    private final ProcesPintat procpin;
    private BufferedImage bima;
    private String opcioTriada;

    public PanellDibuix(int x, int y, Model m, Vista v) {
        w = x;
        h = y;
        mod = m;
        vis = v;
        opcioTriada = "";
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

    @Override
    public void notificar(String s) {
        if (s.startsWith("Executar")) {
            opcioTriada = mod.getOpcioTriada();
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
        gr.setColor(Color.black);
        gr.fillRect(10, 10, 5, this.getHeight() - 25);
        gr.fillRect(10, this.getHeight() - 20, this.getWidth() - 25, 5);
        // Aquests dos fors afegeix els strokes al gràfics.
        for (int i = 1; i <= 4; i++) {
            gr.fillRect(i * (this.getWidth() / 4) - 25, this.getHeight() - 25, 4, 5);
        }
        for (int i = 1; i <= 7; i++) {
            gr.fillRect(15, i * (this.getHeight() / 8) - 15, 5, 4);
        }
    }

    public void pintaGrafic(int n, long temps, int nAnterior, long tempsAnterior) {
        this.getGraphics().drawLine((nAnterior / 100) * (this.getWidth() / 4) - 25, (int) tempsAnterior, (n / 100) * (this.getWidth() / 4) - 25, (int) temps);
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
