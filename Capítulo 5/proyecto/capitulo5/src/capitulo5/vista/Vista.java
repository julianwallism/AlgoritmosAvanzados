package capitulo5.vista;

import static capitulo5.Error.informaError;
import capitulo5.PorEventos;
import capitulo5.main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

public class Vista extends JFrame implements PorEventos {
    private main prog;
    private JPanel panelSuperior, panelInferior;
    private PanelCentral panelCentral;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());
        panelCentral = new PanelCentral(p);
        panelSuperior = new JPanel();
        panelInferior = new JPanel();

        this.setLayout(new BorderLayout());
        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(800, 600));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            informaError(ex);
        }
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Método notificar de la interfaz de eventos
     *
     * @param s
     */
    @Override
    public void notificar(String s) {
        
    }
}
