package capitol3.vista;

import capitol3.PerEsdeveniments;
import capitol3.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Vista extends JFrame implements PerEsdeveniments {
    private final PanellCentral panellCentral;
    private final JPanel panellSuperior, barraInputs, barraBotons;
    private final JComboBox selector;
    private final JButton executar, aturar, buidar;
    private final JTextField num1, num2;
    private final JProgressBar panellInferior;
    private main prog;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());

        this.panellSuperior = new JPanel();
        this.panellSuperior.setBackground(new Color(102,178,255));
        this.barraBotons = new JPanel();
        this.barraBotons.setBackground(new Color(102,178,255));
        this.barraInputs = new JPanel();
        this.barraInputs.setBackground(new Color(102,178,255));
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
        this.num1.setColumns(25);
        this.num2 = new JTextField();
        this.num2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Nombre 2: "+num2.getText());
            }
        });
        this.num2.setToolTipText("Segon nombre a sumar");
        this.num2.setColumns(25);
        this.selector = new JComboBox();
        this.selector.setBackground(Color.white);
        this.selector.setModel(new DefaultComboBoxModel(this.prog.getModel().getAlgorismes()));
        this.selector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Algorisme: "+selector.getSelectedItem());
            }
        });
        
        this.executar = new JButton("Executar");
        this.executar.setBackground(Color.white);
        this.executar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panellInferior.setIndeterminate(true);
                prog.notificar("Executar");
            }
        });
        this.aturar = new JButton("Aturar");
        this.aturar.setBackground(Color.white);
        this.aturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panellInferior.setIndeterminate(false);
                prog.notificar("Aturar");
            }
        });
        this.buidar = new JButton("Buidar");
        this.buidar.setBackground(Color.white);
        this.buidar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panellCentral.buidar();
            }
        });
        
        barraInputs.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lab1 = new JLabel("Número 1 ");
        lab1.setForeground(Color.white);
        barraInputs.add(lab1);
        barraInputs.add(this.num1);
        JLabel lab2 = new JLabel("Número 2 ");
        lab2.setForeground(Color.white);
        barraInputs.add(lab2);
        barraInputs.add(this.num2);
        
        barraBotons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel lab3 = new JLabel("Algorisme ");
        lab3.setForeground(Color.white);
        barraBotons.add(lab3);
        barraBotons.add(this.selector);
        barraBotons.add(this.executar);
        barraBotons.add(this.aturar);
        barraBotons.add(this.buidar);
        
        this.panellSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        this.panellSuperior.add(this.barraInputs);
        this.panellSuperior.add(this.barraBotons);

        this.add(panellSuperior, BorderLayout.NORTH);
        this.add(panellCentral, BorderLayout.CENTER);
        this.add(panellInferior, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(1250, 800));
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Vista")) {
            s = s.replaceAll("Vista: ", "");
            panellInferior.setIndeterminate(false);
            this.panellCentral.notificar(s);
        }
    }
}
