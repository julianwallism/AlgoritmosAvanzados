package capitol2.vista;

import capitol2.MeuError;
import capitol2.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
    private ImageIcon icon;

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
                        p.getModel().getTauler().clear();
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
            tauler[i][j].setBackground(new Color(238, 238, 210));
        } else {
            tauler[i][j].setBackground(new Color(118, 150, 86));
        }
        tauler[i][j].setText("");
        tauler[i][j].setOpaque(true);
    }

    public void posarImatge(int i, int j) {
        for (int fila = 0; fila < p.getModel().getTamanyTriat(); fila++) {
            for (int columna = 0; columna < p.getModel().getTamanyTriat(); columna++) {
                if (tauler[fila][columna].getIcon() != null) {
                    tauler[fila][columna].setIcon(null);
                    tauler[fila][columna].revalidate();
                    pintarCasella(fila, columna);
                }
            }
        }

        this.icon = new ImageIcon(new ImageIcon(this.p.getModel().getPeçaTriada().getImatge()).getImage().getScaledInstance(tauler[i][j].getWidth(), tauler[i][j].getHeight(), Image.SCALE_SMOOTH));
        tauler[i][j].setIcon(icon);

    }

    public void pintarSolucio() {
        int[][] tauler_solucio = p.getModel().getTauler().getCaselles();
        for (int fila = 0; fila < p.getModel().getTamanyTriat(); fila++) {
            for (int columna = 0; columna < p.getModel().getTamanyTriat(); columna++) {
                if (tauler_solucio[fila][columna] != 1) {
                    tauler[fila][columna].setForeground(Color.RED);
                    tauler[fila][columna].setFont(new Font("Times New Roman", Font.BOLD, (256/p.getModel().getTamanyTriat())));
                    tauler[fila][columna].setText("   " + tauler_solucio[fila][columna]);
                }
            }
        }
    }

    public void grafica() throws InterruptedException {
        Graphics gr = this.getGraphics();
        gr.setColor(Color.red);

        int[] ordreX = p.getModel().getTauler().getOrderX();
        int[] ordreY = p.getModel().getTauler().getOrderY();

        int pre_x = tauler[ordreX[0]][ordreY[0]].getLocation().x + tauler[0][0].getWidth() / 2;
        int pre_y = tauler[ordreX[0]][ordreY[0]].getLocation().y + tauler[0][0].getHeight() / 2;

        int x, y;
        for (int i = 0; i < p.getModel().getTamanyTriat() * p.getModel().getTamanyTriat(); i++) {
            x = tauler[ordreX[i]][ordreY[i]].getLocation().x + tauler[0][0].getWidth() / 2;
            y = tauler[ordreX[i]][ordreY[i]].getLocation().y + tauler[0][0].getHeight() / 2;
            gr.fillOval(x - 4, y - 5, 8, 8);
            gr.drawLine(pre_x, pre_y, x, y);
            pre_x = x;
            pre_y = y;
            Thread.sleep(200);
        }
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
