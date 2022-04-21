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
import javax.swing.JLabel;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class PanellGrafic extends JPanel {

    private final int w;
    private final int h;
    private final Model mod;
    private int umbral;
    protected final int FPS = 24; // 24 frames per segon

    public PanellGrafic(int x, int y, Model m) {
        w = x;
        h = y;
        mod = m;
        umbral = mod.getUmbral();
        this.setPreferredSize(new Dimension(w, h));

    }

    @Override
    public void paint(Graphics gr) {
        Graphics2D gr2d = (Graphics2D) gr;
        gr2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Cercam el temps màxim per poder graficart respecte a ell
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

        //Graficam l'algorisme tradicional
        gr2d.setColor(Color.BLUE);
        double x = 0;
        int y = 0;
        for (int i = 0; i < tradicional.length; i++) {
            gr2d.drawLine((int) (((double) y / 700) * this.getWidth()),
                    this.getHeight() - (int) Math.floor((x / max) * this.getHeight()),
                    (int) (((double) i / 700) * this.getWidth()),
                    this.getHeight() - (int) Math.floor((tradicional[i] / max) * this.getHeight()));
            x = tradicional[i];
            y = i;
        }

        //Graficam l'algorisme de karatsuba
        gr2d.setColor(Color.ORANGE.darker());
        x = 0;
        y = 0;
        for (int i = 0; i < karatsuba.length; i++) {
            gr2d.drawLine((int) (((double) y / 700) * this.getWidth()),
                    this.getHeight() - (int) Math.floor((x / max) * this.getHeight()),
                    (int) (((double) i / 700) * this.getWidth()),
                    this.getHeight() - (int) Math.floor((karatsuba[i] / max) * this.getHeight()));
            x = karatsuba[i];
            y = i;
        }

        //pintam els eixos
        this.setLayout(null);
        gr2d.setColor(Color.gray);
        for (int i =1; i < 7; i++) {
            JLabel eix_X = new JLabel(Integer.toString(i * 100));
            this.add(eix_X);
            eix_X.setLocation((int) (((double) i / 7) * this.getWidth()) - 10, 0);
            eix_X.setSize(50, 15);
            eix_X.setOpaque(true);
            this.add(eix_X);
            gr2d.drawLine((int) (((double) i / 7) * this.getWidth()), 10, (int) (((double) i / 7) * this.getWidth()), this.getHeight());
        }

        JLabel info_umbral = new JLabel("Umbral recomanat: " + Integer.toString(umbral));
        this.add(info_umbral);
        info_umbral.setLocation(0, 0);
        info_umbral.setSize(150, 15);
        info_umbral.setOpaque(true);
        gr2d.setColor(Color.red);
        gr2d.drawOval((int) (((double) umbral / 700) * this.getWidth()) - 10,
                this.getHeight() - (int) Math.floor((karatsuba[umbral] / max) * this.getHeight())-10, 20, 20);
    }
}
