package capitol2.vista;

import capitol2.PerEsdeveniments;
import capitol2.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Vista extends JFrame implements PerEsdeveniments {

    private main prog;
    private final ImageIcon logo = new ImageIcon("logo.png");
    private JLabel[][] tablero;
    private final JPanel ventana, barraSuperior;
    private final JMenuBar barraBotones;
    private final JButton resuelve, aturar;
    private final JLabel label_peça, label_tamany;
    private final JSpinner tamany;
    private final JComboBox peces;
    private Dimension dim;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(logo.getImage());
        ventana = new JPanel();
        ventana.setLayout(new GridLayout(p.getModel().getTamanyTriat(), p.getModel().getTamanyTriat()));
        barraSuperior = new JPanel();
        barraBotones = new JMenuBar();
        tamany = new JSpinner();
        label_tamany = new JLabel();
        label_peça = new JLabel();
        peces = new JComboBox();
        resuelve = new JButton("Resol");
        aturar = new JButton("Atura");

        tamany.setValue(8);
        label_tamany.setText("Tria el tamany");

        peces.setModel(new DefaultComboBoxModel(prog.getModel().peces));
        label_peça.setText("Tria la peça: ");

        inicializaTablero(p.getModel().getTamanyTriat());

        tamany.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                removeListener();
                String num = tamany.getValue().toString();
                prog.notificar("Tamany tauler: " + num);
            }
        });

        peces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resposta = peces.getSelectedItem().toString();
                prog.notificar("Canvi peça a " + resposta);

            }
        });

        resuelve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                prog.notificar("Resoldre");
            }
        });
        
        aturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                prog.notificar("Aturar");
            }
        });

        barraSuperior.add(label_tamany);
        barraSuperior.add(tamany);
        barraSuperior.add(label_peça);
        barraSuperior.add(peces);
        barraBotones.add(resuelve);
        barraBotones.add(aturar);
        barraBotones.setLayout(new GridBagLayout());
        dim = new Dimension(p.getModel().getTamanyTriat() * 80, p.getModel().getTamanyTriat() * 80 + 30);
        this.getContentPane().add(barraSuperior, BorderLayout.NORTH);
        this.getContentPane().add(ventana, BorderLayout.CENTER);
        this.getContentPane().add(barraBotones, BorderLayout.SOUTH);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setSize(dim);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializaTablero(int dimension) {
        ventana.removeAll();
        tablero = new JLabel[dimension][dimension];
        ventana.setLayout(new GridLayout(dimension, dimension));
        dim = new Dimension(dimension * 80, dimension * 80 + 30);
        ventana.setSize(dim);
        initCasillas(dimension, tablero);
        ventana.updateUI();
    }

    private void initCasillas(int dimension, JLabel[][] tablero) {
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
                                if (me.getSource() == tablero[i][j]) {
                                    posarImatge(i, j);
                                    prog.notificar("Peça " + i + ", " + j);
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
                pintarCasillas(i, j);
                ventana.add(tablero[i][j]);
            }
        }
    }

    private void pintarCasillas(int i, int j) {
        if ((i + j) % 2 == 0) {
            tablero[i][j].setBackground(new Color(232, 235, 239));
        } else {
            tablero[i][j].setBackground(new Color(125, 135, 150));
        }
        tablero[i][j].setOpaque(true);
    }

    private void posarImatge(int i, int j) {
        for (int fila = 0; fila < this.prog.getModel().getTamanyTriat(); fila++) {
            for (int columna = 0; columna < this.prog.getModel().getTamanyTriat(); columna++) {
                if (tablero[fila][columna].getIcon() != null) {
                    tablero[fila][columna].setIcon(null);
                    tablero[fila][columna].revalidate();
                    pintarCasillas(fila, columna);
                }
            }
        }
        ImageIcon icon = new ImageIcon(new ImageIcon(this.prog.getModel().getPeçaTriada().imatge).getImage().getScaledInstance(tablero[i][j].getWidth(), tablero[i][j].getHeight(), Image.SCALE_DEFAULT));
        tablero[i][j].setIcon(icon);
    }

    public void removeListener() {
        for (int i = 0; i < prog.getModel().getTamanyTriat(); i++) {
            for (int j = 0; j < prog.getModel().getTamanyTriat(); j++) {
                for (MouseListener ml : tablero[i][j].getMouseListeners()) {
                    tablero[i][j].removeMouseListener(ml);
                }
            }
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Actualitzar tauler")) {
            this.inicializaTablero(prog.getModel().getTamanyTriat());
        }
    }
}
