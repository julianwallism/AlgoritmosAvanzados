package capitol3.vista;

import capitol3.PerEsdeveniments;
import capitol3.main;
import java.awt.BorderLayout;
import javax.swing.*;

public class Vista extends JFrame implements PerEsdeveniments {
    private final PanellCentral panellCentral;
    private final JPanel panellSuperior, panellInferior;
    private final JButton executar, aturar;
    private final JTextField num1, num2;
    private main prog;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);

        this.panellSuperior = new JPanel();
        this.panellCentral = new PanellCentral(p);
        this.panellInferior = new JPanel();
        
        // Creació dels components del panell superior
        this.num1 = new JTextField();
        this.num1.setToolTipText("Primer nombre a sumar");
        this.num2 = new JTextField();
        this.num2.setToolTipText("Segon nombre a sumar");
        this.executar = new JButton();
        this.executar.setText("Executar");
        this.aturar = new JButton();
        this.aturar.setText("Aturar");
        
        this.panellSuperior.add(this.num1);
        this.panellSuperior.add(this.num2);
        this.panellSuperior.add(this.executar);
        this.panellSuperior.add(this.aturar);

        this.getContentPane().add(panellSuperior, BorderLayout.NORTH);
        this.getContentPane().add(panellCentral, BorderLayout.CENTER);
        this.getContentPane().add(panellInferior, BorderLayout.SOUTH);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void notificar(String s) {
        
    }
}
