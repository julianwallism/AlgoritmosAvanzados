package capitol2.vista;

import capitol2.PerEsdeveniments;
import capitol2.main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Vista extends JFrame implements PerEsdeveniments {

    private final ImageIcon logo = new ImageIcon("logo.png");
    private final PanellCentral panellCentral;
    private final JPanel barraSuperior, barraSelectors, barraBotones, barraInferior;
    private final JProgressBar barraProgres;
    private final JButton resol, aturar, grafica;
    private final JLabel label_peça, label_tamany, info;
    private final JSpinner tamany;
    private final JComboBox peces;
    private main prog;
    private Dimension dim;
    private Boolean solucio = false;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(logo.getImage());

        panellCentral = new PanellCentral(p);
        barraProgres = new JProgressBar();
        barraSelectors = new JPanel();
        barraBotones = new JPanel();
        barraSuperior = new JPanel();
        barraInferior = new JPanel();

        tamany = new JSpinner(new SpinnerNumberModel(8, 3, 25, 1));

        resol = new JButton("Resol");
        aturar = new JButton("Atura");
        grafica = new JButton("Grafica");

        label_tamany = new JLabel();
        label_peça = new JLabel();
        info = new JLabel();
        label_tamany.setText("Tamany:");
        label_peça.setText("Peça:");
        info.setText(" ");

        peces = new JComboBox();
        peces.setModel(new DefaultComboBoxModel(prog.getModel().peces));

        tamany.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                panellCentral.removeListener();
                String num = tamany.getValue().toString();
                prog.notificar("Tamany tauler: " + num);
            }
        });

        peces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resposta = peces.getSelectedItem().toString();
                prog.notificar("Canvi peça a " + resposta);
                panellCentral.posarImatge(p.getModel().getX(), p.getModel().getY());
            }
        });

        resol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                info.setText(" ");
                prog.notificar("Resoldre");
            }
        });

        aturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                prog.notificar("Aturar");
            }
        });

        grafica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (solucio) {
                    try {
                        panellCentral.grafica();
                    } catch (InterruptedException ex) {
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No resolt, per favor resol el problema abans de graficar", "No resolt", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        barraSelectors.setLayout(new FlowLayout(FlowLayout.LEFT));
        barraSelectors.add(label_tamany);
        barraSelectors.add(tamany);
        barraSelectors.add(label_peça);
        barraSelectors.add(peces);

        barraBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        barraBotones.add(resol);
        barraBotones.add(aturar);
        barraBotones.add(grafica);

        barraSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        barraSuperior.add(barraSelectors);
        barraSuperior.add(barraBotones);

        barraInferior.setLayout(new BorderLayout());
        barraInferior.add(info, BorderLayout.NORTH);
        barraInferior.add(barraProgres, BorderLayout.SOUTH);

        this.getContentPane().add(barraSuperior, BorderLayout.NORTH);
        this.getContentPane().add(panellCentral, BorderLayout.CENTER);
        this.getContentPane().add(barraInferior, BorderLayout.SOUTH);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        dim = new Dimension(p.getModel().getTamanyTriat() * 80, p.getModel().getTamanyTriat() * 80 + 30);
        this.setSize(dim);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Actualitzar tauler")) {
            panellCentral.inicialitzarTauler();
        } else if (s.startsWith("Solució")) {
            barraProgres.setIndeterminate(false);
            String[] sol = s.split(" ");
            if (sol[1].equals("si")) {
                solucio = true;
                panellCentral.pintarSolucio();
                info.setText("L'algorisme ha tardat: " + this.prog.getModel().getTime() + " segons");
            } else {
                JOptionPane.showMessageDialog(null, "Solució no trobada, per favor torna a intentar-ho amb una altra posició i/o peça.", "Solució no trobada", JOptionPane.WARNING_MESSAGE);
            }
        } else if (s.startsWith("Error: PI")) {
            JOptionPane.showMessageDialog(null, "Posició inicial no especificada, per favor torna a intentar-ho fent click a la casella desitjada.", "Posició inicial no especificada", JOptionPane.ERROR_MESSAGE);
        } else if (s.startsWith("Resoldre")) {
            this.barraProgres.setIndeterminate(true);
        } else if (s.startsWith("Aturar")) {
            this.barraProgres.setIndeterminate(false);
        }
    }
}
