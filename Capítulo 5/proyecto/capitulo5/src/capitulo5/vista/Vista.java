package capitulo5.vista;

import static capitulo5.Error.informaError;
import capitulo5.PorEventos;
import capitulo5.main;
import capitulo5.modelo.Palabra;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Vista extends JFrame implements PorEventos {
    private main prog;
    private JButton botonCorregir, botonGuardar, botonComprobar;
    private JLabel labelIdioma, labelPalabrasErroneas, labelPalabrasTotales;
    private JScrollPane jScrollPane1;
    private JTextPane textPane;
    private JProgressBar barraProgreso;
    private StyleContext styleContextCorrectas, styleContextErroneas;
    private Style styleCorrectas, styleErroneas;
    private DefaultStyledDocument document;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());
        this.initComponents();
    }
    
    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        jScrollPane1 = new JScrollPane();
        document = new DefaultStyledDocument();
        textPane = new JTextPane(document);
        botonComprobar = new JButton();
        botonCorregir = new JButton();
        botonGuardar = new JButton();
        labelPalabrasTotales = new JLabel();
        labelPalabrasErroneas = new JLabel();
        labelIdioma = new JLabel();
        barraProgreso = new JProgressBar();
        styleContextCorrectas = new StyleContext();
        styleCorrectas = styleContextCorrectas.addStyle("correctas", null);
        StyleConstants.setForeground(styleCorrectas, Color.black);
        styleContextErroneas = new StyleContext();
        styleErroneas = styleContextErroneas.addStyle("erroneas", null);
        StyleConstants.setForeground(styleErroneas, Color.red);
        StyleConstants.setUnderline(styleErroneas, true);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        textPane.setFont(new java.awt.Font("Dubai", 0, 14));
        jScrollPane1.setViewportView(textPane);

        botonGuardar.setText("Guardar texto");
        botonGuardar.setBackground(new Color(255, 255, 255));
        botonGuardar.addActionListener((ActionEvent e) -> {
            String texto =  this.textPane.getText();
            if (texto.isBlank()) {
                JOptionPane.showMessageDialog(null, "El texto a detectar no puede ser vacio!");
                return;
            }
            this.prog.getModelo().setTexto(texto);
            this.prog.notificar("Texto guardado");
        });
        botonComprobar.setText("Comprobar texto");
        botonComprobar.setBackground(new Color(255, 255, 255));
        botonComprobar.addActionListener((ActionEvent e) -> {
          if(this.prog.getModelo().getIdioma().name().equals("DESCONOCIDO") || this.prog.getModelo().getTexto().isEmpty()){
              JOptionPane.showMessageDialog(null, "Primero guarda un texto valido para corregir.");
              return;
          }
          this.prog.notificar("Comprobar texto");
        });
        botonCorregir.setText("Corregir palabras erróneas");
        botonCorregir.setBackground(new Color(255, 255, 255));
        botonCorregir.addActionListener((ActionEvent e) -> {
          this.prog.notificar("Corregir palabras");
        });

        labelPalabrasTotales.setText("Palabras totales: "+this.prog.getModelo().getPalabrasTexto().length);
        labelPalabrasErroneas.setText("Palabras erróneas: "+this.prog.getControl().getPalabrasErroneas(this.prog.getModelo().getPalabrasTexto()).length);
        labelIdioma.setText("Idioma: "+this.prog.getModelo().getIdioma());
        barraProgreso.setIndeterminate(true);
        barraProgreso.setBackground(new Color(0, 255, 255));
        barraProgreso.setForeground(new Color(0, 51, 204));

        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(botonGuardar)
                .addGap(25, 25, 25)
                .addComponent(botonComprobar)
                .addGap(25, 25, 25)
                .addComponent(botonCorregir)
                .addGap(100, 100, 100)
                .addComponent(labelIdioma)
                .addGap(25, 25, 25)
                .addComponent(labelPalabrasTotales)
                .addGap(25, 25, 25)
                .addComponent(labelPalabrasErroneas)
                .addGap(100, 100, 100))
            .addComponent(barraProgreso, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonGuardar, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonComprobar, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCorregir, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPalabrasTotales)
                    .addComponent(labelPalabrasErroneas)
                    .addComponent(labelIdioma))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(barraProgreso, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
        );
        
        this.getContentPane().setLayout(layout);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    // Método notificar de la interfaz de eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Idioma detectado")) {
            this.labelIdioma.setText("Idioma: "+this.prog.getModelo().getIdioma().toString());
            this.labelPalabrasTotales.setText("Palabras totales: "+this.prog.getModelo().getPalabrasTexto().length);
        } else if (s.startsWith("Texto comprobado")) {
            int erroneas = 0;
            boolean esPrimera = true;
            this.textPane.setText("");
            for (Palabra palabra : this.prog.getModelo().getPalabrasTexto()) {
                if (!esPrimera) {
                    try {
                        this.document.insertString(this.document.getLength(), " ", styleCorrectas);
                    } catch (BadLocationException ex) {
                        informaError(ex);
                    }
                }
                if (palabra.isErronea()) {
                    erroneas++;
                    try {
                        this.document.insertString(this.document.getLength(), palabra.getTexto(), styleErroneas);
                    } catch (BadLocationException ex) {
                        informaError(ex);
                    }
                } else {
                    try {
                        this.document.insertString(this.document.getLength(), palabra.getTexto(), styleCorrectas);
                    } catch (BadLocationException ex) {
                        informaError(ex);
                    }
                }
                if (esPrimera) esPrimera = !esPrimera;
            }
            labelPalabrasErroneas.setText("Palabras erróneas: "+erroneas);
        }
    }
}
