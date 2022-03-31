package capitol3.vista;

import capitol3.MeuError;
import capitol3.PerEsdeveniments;
import capitol3.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.math.BigInteger;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class PanellCentral extends JPanel implements PerEsdeveniments {
    public static final int FPS = 24;
    private main p;
    private final JTextArea text;

    public PanellCentral(main p, int width, int height) {
        this.p = p;
        this.setBackground(Color.WHITE);
        this.text = new JTextArea("Sortida del programa: \n");
        this.text.setFont(new Font("Serif", Font.PLAIN, 16));
        this.text.setWrapStyleWord(true);
        
        this.add(text);
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
        if (s.startsWith("Resultat")) {
            s = s.replaceAll("Resultat: ", "");
            s = s.replaceAll("Temps: ", "");
            String[] res = s.split(" ");
            BigInteger resultat = new BigInteger(res[0]);
            double temps = Double.parseDouble(res[1]);
            this.text.append("Resultat ("+this.p.getModel().getAlgorismeTriat()+"): "+resultat.toString()+"\tTemps: "+temps+'\n');
        }
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
