package capitulo7.vista;

import capitulo7.PorEventos;
import capitulo7.main;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Vista extends JFrame implements PorEventos {

    private final main prog;
    private JButton botonEjecutar;
    private JSpinner muestreo;
    private JLabel labelPais, labelMuestreo;
    private PanelCentral pCentral;
    private JProgressBar barraProgreso;
    private JSeparator separador;

    public Vista(String titol, main p) {
        super(titol);
        this.prog = p;
        this.setIconImage(new ImageIcon("logo.png").getImage());
        this.initComponents();
    }

    // Método notificar de la interfaz de eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Ejecución terminada")) {
            barraProgreso.setIndeterminate(false);
            labelPais.setText("País: " + this.prog.getModelo().getPais());
        }
    }

    private void initComponents() {
        separador = new JSeparator();
        barraProgreso = new JProgressBar();
        botonEjecutar = new JButton("Adivinar país");
        botonEjecutar.setBackground(new Color(255, 255, 255));
        labelPais = new JLabel();
        pCentral = new PanelCentral(prog);
        labelMuestreo = new JLabel("% de muestreo: ");
        muestreo = new JSpinner(new SpinnerNumberModel(20, 1, 100, 5));

        muestreo.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int num = (int) muestreo.getValue();
                prog.getModelo().setPorcentajeMuestreo(num);
            }
        });
        botonEjecutar.addActionListener((ActionEvent e) -> {
            this.prog.notificar("Ejecutar");
            barraProgreso.setIndeterminate(true);
        });
        labelPais.setText("País: " + this.prog.getModelo().getPais());
        barraProgreso.setBackground(new Color(255, 255, 255));
        barraProgreso.setForeground(new Color(127, 127, 127));

        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(pCentral)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(botonEjecutar)
                                .addGap(100)
                                .addComponent(labelMuestreo)
                                .addGap(10)
                                .addComponent(muestreo, GroupLayout.PREFERRED_SIZE, 50, 50)
                                .addGap(25)
                                .addComponent(labelPais)
                                .addGap(10))
                        .addComponent(barraProgreso, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(separador, javax.swing.GroupLayout.PREFERRED_SIZE, 700,
                                javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(botonEjecutar, GroupLayout.PREFERRED_SIZE, 52,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelMuestreo, GroupLayout.PREFERRED_SIZE, 52,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(muestreo, GroupLayout.PREFERRED_SIZE, 25,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelPais))
                                .addGap(10)
                                .addComponent(separador, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pCentral, 500, 600, 900)
                                .addComponent(barraProgreso, GroupLayout.PREFERRED_SIZE, 10,
                                        GroupLayout.PREFERRED_SIZE)));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(layout);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}
