package capitol4.vista;

import capitol4.PerEsdeveniments;
import capitol4.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;


public class Vista extends JFrame implements PerEsdeveniments {
    private final PanellCentral panellCentral;
    private final JPanel panellSuperior;
    private final JProgressBar panellInferior;
    private main prog;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());

        this.panellSuperior = new JPanel();
        this.panellSuperior.setBackground(new Color(102,178,255));
        this.panellCentral = new PanellCentral(p, this.getWidth(), this.getHeight());
        this.panellInferior = new JProgressBar();
        
        this.panellSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));

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
