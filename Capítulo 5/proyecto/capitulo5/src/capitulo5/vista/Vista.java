package capitulo5.vista;

import static capitulo5.Error.informaError;
import capitulo5.PorEventos;
import capitulo5.main;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.Utilities;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Vista extends JFrame implements PorEventos {
    private main prog;
    private JButton botonCorregir, botonComprobar;
    private JLabel labelIdioma, labelPalabrasErroneas, labelPalabrasTotales;
    private JScrollPane jScrollPane1;
    private JTextPane textPane;
    private JProgressBar barraProgreso;
    private StyleContext styleContextCorrectas, styleContextErroneas;
    private Style styleCorrectas, styleErroneas;
    private DefaultStyledDocument document;

    public Vista(String titol, main p) {
        super(titol);
        this.prog = p;
        this.setIconImage(new ImageIcon("logo.png").getImage());
        this.initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        jScrollPane1 = new JScrollPane();
        document = new DefaultStyledDocument();
        textPane = new JTextPane(document);
        textPane.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    String pal = null;
                    int pt = textPane.viewToModel2D(evt.getPoint());
                    int spt = Utilities.getWordStart(textPane, pt);
                    int ept = Utilities.getWordEnd(textPane, pt);
                    textPane.setSelectionStart(spt);
                    textPane.setSelectionEnd(ept);
                    pal = textPane.getSelectedText();
                    for (String err : prog.getModelo().getPalabrasErroneas()) {
                        if (err.equals(pal)) {
                            abrirDialog(pal);
                        }
                    }
                } catch (Exception e) {
                    informaError(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        botonComprobar = new JButton();
        botonCorregir = new JButton();
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

        botonComprobar.setText("Comprobar texto");
        botonComprobar.setBackground(new Color(255, 255, 255));
        botonComprobar.addActionListener((ActionEvent e) -> {
            this.prog.getModelo().setTexto(this.textPane.getText());
            this.prog.notificar("Comprobar texto");
        });
        botonCorregir.setText("Buscar sugerencias");
        botonCorregir.setBackground(new Color(255, 255, 255));
        botonCorregir.addActionListener((ActionEvent e) -> {
            this.prog.notificar("Buscar sugerencias");
        });

        labelPalabrasTotales.setText("Palabras totales: " + this.prog.getModelo().getPalabrasTexto().length);
        labelPalabrasErroneas.setText("Palabras erróneas: " + this.prog.getModelo().getPalabrasErroneas().length);
        labelIdioma.setText("Idioma: " + this.prog.getModelo().getIdioma());
        barraProgreso.setIndeterminate(true);
        barraProgreso.setBackground(new Color(0, 255, 255));
        barraProgreso.setForeground(new Color(0, 51, 204));

        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
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
        if (s.startsWith("Texto comprobado")) {
            this.actualizaLabels();
            this.resaltaPalabrasErroneas();
        } else if (s.startsWith("Actualizado")) {
            this.actualizaLabels();
        }
    }
    
    private void actualizaLabels() {
        this.labelIdioma.setText("Idioma: " + this.prog.getModelo().getIdioma().toString());
        this.labelPalabrasTotales.setText("Palabras totales: " + this.prog.getModelo().getPalabrasTexto().length);
        this.labelPalabrasErroneas.setText("Palabras erróneas: " + this.prog.getModelo().getPalabrasErroneas().length);
    }

    private void resaltaPalabrasErroneas() {
        for (String err : this.prog.getModelo().getPalabrasErroneas()) {
            int i = this.textPane.getText().indexOf(err);
            int prevI;
            while (i != -1) { // cambiamos todas las ocurrencias de la palabra
                prevI = i;
                try {
                    this.document.replace(i, err.length(), err, styleErroneas);
                } catch (BadLocationException ex) {
                    informaError(ex);
                }
                i = this.textPane.getText().indexOf(err, prevI+1);
            }
        }
    }

    private void abrirDialog(String palabra) {
        String palabraSeleccionada = (String) JOptionPane.showInputDialog(
                null,
                "¿Qué palabra quieres sustituir por la errónea?",
                "Corregir palabra",
                JOptionPane.QUESTION_MESSAGE,
                null,
                this.prog.getModelo().getSugerencias().get(palabra).toArray(),
                this.prog.getModelo().getSugerencias().get(palabra).toArray()[0]);
        int dif = palabra.length() - palabraSeleccionada.length();
        try {
            this.document.replace(this.textPane.getText().indexOf(palabra), palabraSeleccionada.length()+dif, palabraSeleccionada, styleCorrectas);
        } catch (BadLocationException ex) {
            informaError(ex);
        }
        this.prog.getModelo().setTexto(this.textPane.getText());
        this.prog.notificar("Actualizar");
    }
}
