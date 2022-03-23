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
    private final JMenuBar barraBotons;
    private final JButton resol, aturar;
    private final JLabel label_peça, label_tamany;
    private final JSpinner tamany;
    private final JComboBox peces;
    private main prog;
    private Dimension dim;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(logo.getImage());
        panellCentral = new PanellCentral(p);
        barraSuperior = new JPanel();
        barraBotons = new JMenuBar();
        tamany = new JSpinner();
        label_tamany = new JLabel();
        label_peça = new JLabel();
        peces = new JComboBox();
        resol = new JButton("Resol");
        aturar = new JButton("Atura");
        tamany.setValue(8);
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

        resol.addActionListener(new ActionListener() {
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
        barraBotons.add(resol);
        barraBotons.add(aturar);
        barraBotons.setLayout(new GridBagLayout());
        dim = new Dimension(p.getModel().getTamanyTriat() * 80, p.getModel().getTamanyTriat() * 80 + 30);
        
        this.getContentPane().add(barraSuperior, BorderLayout.NORTH);
        this.getContentPane().add(panellCentral, BorderLayout.CENTER);
        this.getContentPane().add(barraBotons, BorderLayout.SOUTH);
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
        } else if (s.startsWith("Solució trobada")) {
            System.out.println(s);
            Casella[][] taulerModel = this.prog.getModel().getTauler();
            int xInici = this.prog.getModel().getX();
            int yInici = this.prog.getModel().getY();
            
            for (int i = 0; i < taulerModel.length; i++) {
                for (int j = 0; j < taulerModel.length; j++) {
                    if (xInici != i || yInici != j) {
                        panellCentral.pintarOrdreCasella(i, j, taulerModel[i][j].getOrdre());
                    }
                }
            }
        } else if (s.startsWith("Solució no trobada")) {
            JOptionPane.showMessageDialog(null, "Solució no trobada, per favor torna a intentar-ho amb una altra posició i/o peça de les disponibles a la barra superior.", "Solució no trobada", JOptionPane.WARNING_MESSAGE);
        } else if (s.startsWith("Error: PI")) {
            JOptionPane.showMessageDialog(null, "Posició inicial no especificada, per favor torna a intentar-ho fent click a la casella desitjada.", "Posició inicial no especificada", JOptionPane.ERROR_MESSAGE);
        }
    }
}
