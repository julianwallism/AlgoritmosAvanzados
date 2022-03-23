package capitol2.vista;

import capitol2.PerEsdeveniments;
import capitol2.main;
import capitol2.model.Casella;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Vista extends JFrame implements PerEsdeveniments {

    private final ImageIcon logo = new ImageIcon("logo.png");
    private final PanellCentral panellCentral;
    private final JPanel barraSuperior;
    private final JMenuBar barraBotones;
    private final JButton resuelve, aturar, grafica;
    private final JLabel label_peça, label_tamany;
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
        barraSuperior = new JPanel();
        barraBotones = new JMenuBar();
        tamany = new JSpinner(new SpinnerNumberModel(8, 3, 25, 1));
        label_tamany = new JLabel();
        label_peça = new JLabel();
        peces = new JComboBox();
        resuelve = new JButton("Resol");
        aturar = new JButton("Atura");
        grafica = new JButton("Grafica");
        label_tamany.setText("Tria el tamany");
        peces.setModel(new DefaultComboBoxModel(prog.getModel().peces));
        label_peça.setText("Tria la peça: ");

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

        barraSuperior.add(label_tamany);
        barraSuperior.add(tamany);
        barraSuperior.add(label_peça);
        barraSuperior.add(peces);

        barraBotones.add(resuelve);
        barraBotones.add(aturar);
        barraBotones.add(grafica);
        barraBotones.setLayout(new GridBagLayout());
        dim = new Dimension(p.getModel().getTamanyTriat() * 80, p.getModel().getTamanyTriat() * 80 + 30);

        this.getContentPane().add(barraSuperior, BorderLayout.NORTH);
        this.getContentPane().add(panellCentral, BorderLayout.CENTER);
        this.getContentPane().add(barraBotones, BorderLayout.SOUTH);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setSize(dim);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Actualitzar tauler")) {
            panellCentral.inicialitzarTauler();
        } else if (s.startsWith("Solució")) {
            String[] sol = s.split(" ");
            switch (sol[1]) {
                case ("si"):
                    solucio = true;
                    panellCentral.solucio();
                    break;
                case ("no"):
                    JOptionPane.showMessageDialog(null, "Solució no trobada, per favor torna a intentar-ho amb una altra posició i/o peça de les disponibles a la barra superior.", "Solució no trobada", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        } else if (s.startsWith("Error: PI")) {
            JOptionPane.showMessageDialog(null, "Posició inicial no especificada, per favor torna a intentar-ho fent click a la casella desitjada.", "Posició inicial no especificada", JOptionPane.ERROR_MESSAGE);
        }
    }
}
