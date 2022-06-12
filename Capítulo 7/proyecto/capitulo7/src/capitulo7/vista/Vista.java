package capitulo7.vista;

import static capitulo7.Error.informaError;
import capitulo7.PorEventos;
import capitulo7.main;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Vista extends JFrame implements PorEventos {

    private final main prog;
    private JButton botonAbrirImagen, botonEjecutar;
    private JFileChooser fileChooser;
    private JLabel labelPais, imagenBandera;
    private JProgressBar barraProgreso;

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
            labelPais.setText("País: "+this.prog.getModelo().getPais());
        }
    }

    private void initComponents() {
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Imágenes", "png", "jpg"));
        barraProgreso = new JProgressBar();
        botonAbrirImagen = new JButton("Escoger imagen");
        botonAbrirImagen.setBackground(new Color(255, 255, 255));
        botonEjecutar = new JButton("Ejecutar");
        botonEjecutar.setBackground(new Color(255, 255, 255));
        labelPais = new JLabel();
        imagenBandera = new JLabel();

        // Cuando se presiona el botón abrir imagen, se abre un JFileChooser
        botonAbrirImagen.addActionListener((ActionEvent e) -> {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(fileChooser.getSelectedFile());
                    imagenBandera.setIcon(new ImageIcon(bufferedImage));
                    this.prog.getModelo().setImagen(bufferedImage);
                } catch (IOException ex) {
                    informaError(ex);
                }
            }
        });

        botonEjecutar.addActionListener((ActionEvent e) -> {
            this.prog.notificar("Ejecutar");
            barraProgreso.setIndeterminate(true);
        });

        labelPais.setText("País: " + this.prog.getModelo().getPais());
        barraProgreso.setBackground(new Color(0, 255, 255));
        barraProgreso.setForeground(new Color(0, 51, 204));

        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(imagenBandera)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(botonAbrirImagen)
                                .addGap(25, 25, 25)
                                .addComponent(botonEjecutar)
                                .addGap(100, 100, 100)
                                .addComponent(labelPais)
                                .addGap(100, 100, 100))
                        .addComponent(barraProgreso, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(botonAbrirImagen, GroupLayout.PREFERRED_SIZE, 52,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(botonEjecutar, GroupLayout.PREFERRED_SIZE, 52,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelPais))
                                .addGap(25, 25, 25)
                                .addComponent(imagenBandera, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(barraProgreso, GroupLayout.PREFERRED_SIZE, 10,
                                        GroupLayout.PREFERRED_SIZE)));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(layout);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}
