package capitol2.vista;

import capitol2.MeuError;
import capitol2.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class PanellCentral extends JPanel {
    public static final int FPS = 24;
    
    private JLabel[][] tauler;
    private main p;
    
    public PanellCentral(main p) {
        this.p = p;
        this.setLayout(new GridLayout(p.getModel().getTamanyTriat(), p.getModel().getTamanyTriat()));
        inicialitzarTauler();
    }
    
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    public void paint(Graphics gr) {
        super.paint(gr);
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
    
    public void inicialitzarTauler() {
        int dimension = this.p.getModel().getTamanyTriat();
        this.removeAll();
        tauler = new JLabel[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        Dimension dim = new Dimension(dimension * 80, dimension * 80 + 30);
        this.setSize(dim);
        inicialitzarCaselles(dimension, tauler);
        this.updateUI();
    }

    private void inicialitzarCaselles(int dimension, JLabel[][] tablero) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tablero[i][j] = new JLabel();
                tablero[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
                        for (int i = 0; i < dimension; i++) {
                            for (int j = 0; j < dimension; j++) {
                                pintarCasella(i, j);
                                if (me.getSource() == tablero[i][j]) {
                                    posarImatge(i, j);
                                    p.notificar("Peça " + i + ", " + j);
                                }
                            }
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {
                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                    }
                }
                );
                pintarCasella(i, j);
                this.add(tablero[i][j]);
            }
        }
    }

    private void pintarCasella(int i, int j) {
        if ((i + j) % 2 == 0) {
            tauler[i][j].setBackground(new Color(232, 235, 239));
        } else {
            tauler[i][j].setBackground(new Color(125, 135, 150));
        }
        tauler[i][j].setText("");
        tauler[i][j].setOpaque(true);
    }

    private void posarImatge(int i, int j) {
        for (int fila = 0; fila < this.p.getModel().getTamanyTriat(); fila++) {
            for (int columna = 0; columna < this.p.getModel().getTamanyTriat(); columna++) {
                if (tauler[fila][columna].getIcon() != null) {
                    tauler[fila][columna].setIcon(null);
                    tauler[fila][columna].revalidate();
                    pintarCasella(fila, columna);
                }
            }
        }
        ImageIcon icon = new ImageIcon(new ImageIcon(this.p.getModel().getPeçaTriada().imatge).getImage().getScaledInstance(tauler[i][j].getWidth(), tauler[i][j].getHeight(), Image.SCALE_DEFAULT));
        tauler[i][j].setIcon(icon);
    }
    
    public void pintarOrdreCasella(int i, int j, int nombre) {
        tauler[i][j].setText(""+nombre);
    }

    public void removeListener() {
        for (int i = 0; i < p.getModel().getTamanyTriat(); i++) {
            for (int j = 0; j < p.getModel().getTamanyTriat(); j++) {
                for (MouseListener ml : tauler[i][j].getMouseListeners()) {
                    tauler[i][j].removeMouseListener(ml);
                }
            }
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
