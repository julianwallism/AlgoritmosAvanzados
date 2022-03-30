package capitol3.vista;

import capitol3.PerEsdeveniments;
import capitol3.main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Vista extends JFrame implements PerEsdeveniments {
    private final PanellCentral panellCentral;
    private final JPanel panellSuperior, barraInputs, barraBotons;
    private final JComboBox selector;
    private final JButton executar, aturar;
    private final JTextField num1, num2;
    private final JProgressBar panellInferior;
    private main prog;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);

        this.panellSuperior = new JPanel();
        this.barraBotons = new JPanel();
        this.barraInputs = new JPanel();
        this.panellCentral = new PanellCentral(p, this.getWidth(), this.getHeight());
        this.panellInferior = new JProgressBar();
        
        // Creació dels components del panell superior
        this.num1 = new JTextField();
        this.num1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Nombre 1: "+num1.getText());
            }
        });
        this.num1.setToolTipText("Primer nombre a sumar");
        this.num1.setColumns(10);
        this.num2 = new JTextField();
        this.num2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Nombre 2: "+num2.getText());
            }
        });
        this.num2.setToolTipText("Segon nombre a sumar");
        this.num2.setColumns(10);
        this.selector = new JComboBox();
        this.selector.setModel(new DefaultComboBoxModel(this.prog.getModel().getAlgoritmes()));
        this.selector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Algoritme: "+selector.getSelectedItem());
            }
        });
        
        this.executar = new JButton("Executar");
        this.executar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Executar");
            }
        });
        this.aturar = new JButton("Aturar");
        this.aturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Aturar");
            }
        });
        
        barraInputs.setLayout(new FlowLayout(FlowLayout.LEFT));
        barraInputs.add(new JLabel("Número 1 "));
        barraInputs.add(this.num1);
        barraInputs.add(new JLabel("Número 2 "));
        barraInputs.add(this.num2);
        barraInputs.add(new JLabel("Algoritme "));
        barraInputs.add(this.selector);
        
        barraBotons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        barraBotons.add(this.executar);
        barraBotons.add(this.aturar);
        
        this.panellSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        this.panellSuperior.add(this.barraInputs);
        this.panellSuperior.add(this.barraBotons);
        
        this.panellInferior.setIndeterminate(true);

        this.add(panellSuperior, BorderLayout.NORTH);
        this.add(panellCentral, BorderLayout.CENTER);
        this.add(panellInferior, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(900, 750));
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Vista")) {
            s = s.replaceAll("Vista: ", "");
            this.panellCentral.notificar(s);
        }
    }
}
