package capitulo5.vista;

import static capitulo5.Error.informaError;
import capitulo5.PorEventos;
import capitulo5.main;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    private final main prog;
    private JButton botonComprobar, botonAbrirTXT, botonGuardarTXT;
    private JFileChooser fileChooser;
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

    // Método notificar de la interfaz de eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Texto comprobado")) {
            this.actualizaLabels();
            this.resaltaPalabrasErroneas();
            this.barraProgreso.setIndeterminate(false);
        } else if (s.startsWith("Actualizado")) {
            this.actualizaLabels();
        }
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
                            int indxInici = textPane.getSelectionStart();
                            abrirDialog(pal, indxInici);
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
        botonAbrirTXT = new JButton();
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        botonGuardarTXT = new JButton();
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
            this.barraProgreso.setIndeterminate(true);
            this.prog.notificar("Comprobar texto");
        });

        // Cuando se clickea el botón abrir, se abre un JFileChooser
        botonAbrirTXT.setText("Abrir fichero");
        botonAbrirTXT.setBackground(new Color(255, 255, 255));
        botonAbrirTXT.addActionListener((ActionEvent e) -> {
            // Solo se pueden elegir archivos .txt
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                // Si se elige un fichero se carga en el textPane
                //Get content from selected file
                try {
                    String content = new String(Files.readAllBytes(fileChooser.getSelectedFile().toPath()));
                    textPane.setText(content);
                    this.prog.getModelo().setTexto(content);
                } catch (IOException ex) {
                    informaError(ex);
                }
            }
        });

        //Cuando se clica en el botón guardar, se abre un JFileChooser para guardar el texto
        botonGuardarTXT.setText("Guardar fichero");
        botonGuardarTXT.setBackground(new Color(255, 255, 255));
        botonGuardarTXT.addActionListener((ActionEvent e) -> {
            // Solo se pueden elegir archivos .txt
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                // Si se elige un fichero se guarda el texto
                //Get content from selected file
                try {
                    Files.write(fileChooser.getSelectedFile().toPath(), textPane.getText().getBytes());
                } catch (IOException ex) {
                    informaError(ex);
                }
            }
        });

        labelPalabrasTotales.setText("Palabras totales: " + this.prog.getModelo().getPalabrasTexto().length);
        labelPalabrasErroneas.setText("Palabras erróneas: " + this.prog.getModelo().getPalabrasErroneas().length);
        labelIdioma.setText("Idioma: " + this.prog.getModelo().getIdioma());
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
                                .addComponent(botonAbrirTXT)
                                .addGap(25, 25, 25)
                                .addComponent(botonGuardarTXT)
                                .addGap(100, 100, 100)
                                .addComponent(labelIdioma)
                                .addGap(25, 25, 25)
                                .addComponent(labelPalabrasTotales)
                                .addGap(25, 25, 25)
                                .addComponent(labelPalabrasErroneas)
                                .addGap(100, 100, 100))
                        .addComponent(barraProgreso, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(botonComprobar, GroupLayout.PREFERRED_SIZE, 52,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(botonAbrirTXT, GroupLayout.PREFERRED_SIZE, 52,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(botonGuardarTXT, GroupLayout.PREFERRED_SIZE, 52,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelPalabrasTotales)
                                        .addComponent(labelPalabrasErroneas)
                                        .addComponent(labelIdioma))
                                .addGap(25, 25, 25)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(barraProgreso, GroupLayout.PREFERRED_SIZE, 10,
                                        GroupLayout.PREFERRED_SIZE)));

        this.getContentPane().setLayout(layout);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void actualizaLabels() {
        this.labelIdioma.setText("Idioma: " + this.prog.getModelo().getIdioma().toString());
        this.labelPalabrasTotales.setText("Palabras totales: " + this.prog.getModelo().getPalabrasTexto().length);
        this.labelPalabrasErroneas.setText("Palabras erróneas: " + this.prog.getModelo().getPalabrasErroneas().length);
    }

    private void resaltaPalabrasErroneas() {
        String[] palabrasTexto = this.prog.getModelo().getPalabrasTexto();
        String[] palabrasErroneas = this.prog.getModelo().getPalabrasErroneas();
        String texto = this.textPane.getText();
        //indexAux guarda por que parte del texto vamos, indice guarda el índice
        //del último caracter de la palabra a marcar
        int indexAux = 0, index = 0;
        // Por cada palabra del texto
        for (String palabra : palabrasTexto) {
            //Si no es nula
            if (palabra != "") {
                // Si palabras erroneas contains palabra
                //Buscamos su primera aparición despues de indiceAux
                index = texto.indexOf(palabra, indexAux) + palabra.length();
                if (Arrays.asList(palabrasErroneas).contains(palabra)) {
                    // Como indexAux guarda el índice del primer caracter de la 
                    // palabra y indice el del útlimo sacamos la palabra del texto con un substring
                    String aux = texto.substring(indexAux + 1, index);
                    System.out.println(aux);
                    try {
                        //Una vez tenemos la palabra podemos pintarla
                        this.document.replace(indexAux + 1, aux.length(), aux, styleErroneas);
                    } catch (BadLocationException ex) {
                        informaError(ex);
                    }
                }
                //hAcemos un update del indice
                indexAux = index;
            }
        }
    }

    private void abrirDialog(String palabraErronea, int indxInici) {
        String palabraSeleccionada = (String) JOptionPane.showInputDialog(
                null,
                "¿Qué palabra quieres sustituir por la errónea?",
                "Corregir palabra",
                JOptionPane.QUESTION_MESSAGE,
                null,
                this.prog.getModelo().getSugerencias().get(palabraErronea).toArray(),
                this.prog.getModelo().getSugerencias().get(palabraErronea).toArray()[0]);
        int dif = palabraErronea.length() - palabraSeleccionada.length();
        try {
            this.document.replace(indxInici, palabraSeleccionada.length() + dif,
                    palabraSeleccionada, styleCorrectas);
        } catch (BadLocationException ex) {
            informaError(ex);
        }
        this.prog.getModelo().setTexto(this.textPane.getText());
        this.prog.notificar("Actualizar");
    }
}
