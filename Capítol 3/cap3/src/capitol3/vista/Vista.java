package capitol3.vista;

import capitol3.PerEsdeveniments;
import capitol3.main;
import java.awt.BorderLayout;
import javax.swing.*;

public class Vista extends JFrame implements PerEsdeveniments {
    private final PanellCentral panellCentral;
    private final JPanel barraSuperior, barraInferior;
    private main prog;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);

        panellCentral = new PanellCentral(p);
        barraSuperior = new JPanel();
        barraInferior = new JPanel();

        this.getContentPane().add(barraSuperior, BorderLayout.NORTH);
        this.getContentPane().add(panellCentral, BorderLayout.CENTER);
        this.getContentPane().add(barraInferior, BorderLayout.SOUTH);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void notificar(String s) {
        
    }
}
