package capitulo7.vista;

import capitulo7.PorEventos;
import capitulo7.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Vista extends JFrame implements PorEventos {

    private final main prog;
    private JButton botonEjecutar, botonUnitTest;
    private JSpinner muestreo;
    private JLabel labelPais, labelMuestreo, tiempo;
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
            tiempo.setText("Tiempo: " + this.prog.getModelo().getTiempo() + " ms");
        } else if (s.startsWith("Unit test terminados")) {
            barraProgreso.setIndeterminate(false);
            muestraResultados();
        }
    }

    private void initComponents() {
        separador = new JSeparator();
        barraProgreso = new JProgressBar();
        botonEjecutar = new JButton("Adivinar país");
        botonEjecutar.setBackground(new Color(255, 255, 255));
        botonUnitTest = new JButton("Ejecutar unit tests");
        botonUnitTest.setBackground(new Color(255, 255, 255));
        labelPais = new JLabel();
        tiempo = new JLabel("Tiempo: ");
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
            if (prog.getModelo().getImagen() == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona una imagen antes de continuar.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                barraProgreso.setIndeterminate(true);
                this.prog.notificar("Ejecuta muestreo");
            }
        });

        botonUnitTest.addActionListener((ActionEvent e) -> {
            barraProgreso.setIndeterminate(true);
            this.prog.notificar("Ejecuta unit tests");
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
                                .addGap(25)
                                .addComponent(botonUnitTest)
                                .addGap(25)
                                .addComponent(labelMuestreo)
                                .addGap(5)
                                .addComponent(muestreo, 50, 50, 50)
                                .addGap(50)
                                .addComponent(labelPais)
                                .addGap(10)
                                .addComponent(tiempo)
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
                                        .addComponent(botonUnitTest, GroupLayout.PREFERRED_SIZE, 52,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelMuestreo, GroupLayout.PREFERRED_SIZE, 52,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(muestreo, 25, 25, 25)
                                        .addComponent(labelPais)
                                        .addComponent(tiempo))
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

    private void muestraResultados() {
        JDialog dialog = new JDialog(Vista.this, "Informacion", true);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        String[] paisesReales = this.prog.getModelo().getPaisesReales();
        String[] paisesPredichos = this.prog.getModelo().getPaisesPredichos();
        String[] resultados = this.prog.getModelo().getResultados();
        float[] tiempos = this.prog.getModelo().getTiempoUnitTest();
        int correctas = this.prog.getModelo().getCorrectas();
        textArea.append("Real \t| Predicho \t| Tiempo \t| Resultado\n");
        for (int i = 0; i < paisesReales.length; i++) {
            textArea.append(paisesReales[i] + "\t| " + paisesPredichos[i] + "\t| " + tiempos[i] + " s\t| " + resultados[i] + "\n");
        }
        textArea.append("Total correctas: \t "+ correctas+ " de 196 ("+(float) 100*correctas/196+"%)");
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        dialog.add(scrollPane);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
