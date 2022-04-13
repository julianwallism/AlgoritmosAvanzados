package capitol4.vista;

import capitol4.PerEsdeveniments;
import capitol4.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Vista extends JFrame implements PerEsdeveniments {
    private final PanellCentral panellCentral;
    private final JPanel panellSuperior;
    private final JButton triaFitxer, comprimeix, atura;
    private final JFileChooser selectorFitxer;
    private final JProgressBar panellInferior;
    private main prog;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());

        this.panellSuperior = new JPanel();
        this.panellSuperior.setBackground(new Color(102,178,255));
        this.selectorFitxer = new JFileChooser();
        this.selectorFitxer.setApproveButtonText("Tria");
        this.panellCentral = new PanellCentral(p, this.getWidth(), this.getHeight());
        this.panellInferior = new JProgressBar();
        
        this.panellSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        this.triaFitxer = new JButton("Tria fitxer");
        this.comprimeix = new JButton("Comprimeix");
        this.atura = new JButton("Atura");
        this.triaFitxer.setBackground(Color.WHITE);
        this.comprimeix.setBackground(Color.WHITE);
        this.atura.setBackground(Color.WHITE);
        this.triaFitxer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int retorn = selectorFitxer.showOpenDialog(Vista.this);
                if (retorn == 0) p.notificar("Fitxer: "+selectorFitxer.getSelectedFile().getPath());
            }
        });
        this.comprimeix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.notificar("Comprimir");
            }
        });
        this.atura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.notificar("Aturar");
            }
        });
        this.panellSuperior.add(this.triaFitxer);
        this.panellSuperior.add(this.comprimeix);
        this.panellSuperior.add(this.atura);

        this.add(panellSuperior, BorderLayout.NORTH);
        this.add(panellCentral, BorderLayout.CENTER);
        this.add(panellInferior, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(1400, 800));
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void notificar(String s) {
        
    }
}
