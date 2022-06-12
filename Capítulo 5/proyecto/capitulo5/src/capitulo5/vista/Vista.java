package capitulo5.vista;

import static capitulo5.Error.informaError;
import capitulo5.PorEventos;
import capitulo5.main;
import capitulo5.modelo.Palabra;
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
<<<<<<< HEAD
=======
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
        } else if (s.startsWith("No idioma detectado")) {
            JOptionPane.showMessageDialog(null, "No se ha podido detectar ningun idioma.");
            this.barraProgreso.setIndeterminate(false);
        }
>>>>>>> sinPalabra
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

<<<<<<< HEAD
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
=======
        botonComprobar.setText("Comprobar texto");
        botonComprobar.setBackground(new Color(255, 255, 255));
        botonComprobar.addActionListener((ActionEvent e) -> {
            if (this.textPane.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "El texto a detectar no puede ser vacio!");
                return;
            }
            this.prog.getModelo().setTexto(this.textPane.getText());
            this.barraProgreso.setIndeterminate(true);
            this.prog.notificar("Comprobar texto");
>>>>>>> sinPalabra
        });

        // Cuando se clickea el botón abrir, se abre un JFileChooser
        botonAbrirTXT.setText("Abrir fichero");
        botonAbrirTXT.setBackground(new Color(255, 255, 255));
        botonAbrirTXT.addActionListener((ActionEvent e) -> {
            // Solo se pueden elegir archivos .txt
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                // Si se elige un fichero se carga en el textPane
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
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION && this.textPane.getText().length() > 0) {
                // Si se elige un fichero se guarda el texto
                try {
                    Files.write(fileChooser.getSelectedFile().toPath(), textPane.getText().getBytes());
                } catch (IOException ex) {
                    informaError(ex);
                }
            }
        });

<<<<<<< HEAD
        labelPalabrasTotales.setText("Palabras totales: "+this.prog.getModelo().getPalabrasTexto().length);
        labelPalabrasErroneas.setText("Palabras erróneas: "+this.prog.getControl().getPalabrasErroneas(this.prog.getModelo().getPalabrasTexto()).length);
        labelIdioma.setText("Idioma: "+this.prog.getModelo().getIdioma());
        barraProgreso.setIndeterminate(true);
=======
        labelPalabrasTotales.setText("Palabras totales: " + this.prog.getModelo().getPalabrasTexto().length);
        labelPalabrasErroneas.setText("Palabras erróneas: " + this.prog.getModelo().getPalabrasErroneas().length);
        labelIdioma.setText("Idioma: " + this.prog.getModelo().getIdioma());
>>>>>>> sinPalabra
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

<<<<<<< HEAD
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
=======
    private void actualizaLabels() {
        this.labelIdioma.setText("Idioma: " + this.prog.getModelo().getIdioma().toString());
        this.labelPalabrasTotales.setText("Palabras totales: " + this.prog.getModelo().getPalabrasTexto().length);
        this.labelPalabrasErroneas.setText("Palabras erróneas: " + this.prog.getModelo().getPalabrasErroneas().length);
    }

    private void resaltaPalabrasErroneas() {
        String[] palabrasTexto = this.prog.getModelo().getPalabrasTexto();
        String[] palabrasErroneas = this.prog.getModelo().getPalabrasErroneas();
        String texto = this.textPane.getText();
        // indexAux guarda por qué parte del texto vamos, indice guarda el índice
        // del último caracter de la palabra a marcar
        int indexAux = 0, index = 0;
        // Por cada palabra del texto
        for (String palabra : palabrasTexto) {
            // Si no es nula
            if (palabra != "") {
                // Si palabra es una palabra errónea
                // Buscamos su primera aparición despues de indiceAux
                index = texto.indexOf(palabra, indexAux) + palabra.length();
                if (Arrays.asList(palabrasErroneas).contains(palabra)) {
                    // Como indexAux guarda el índice del primer caracter de la 
                    // palabra y indice el del útlimo sacamos la palabra del 
                    // texto con un substring
                    String aux = texto.substring(indexAux + 1, index);
                    try {
                        // Una vez tenemos la palabra podemos pintarla
                        this.document.replace(indexAux + 1, aux.length(), aux, styleErroneas);
>>>>>>> sinPalabra
                    } catch (BadLocationException ex) {
                        informaError(ex);
                    }
                }
<<<<<<< HEAD
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
=======
                // Hacemos un update del indice
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
        if (palabraSeleccionada != null) {
            int dif = palabraErronea.length() - palabraSeleccionada.length();
            try {
                this.document.replace(indxInici, palabraSeleccionada.length() + dif,
                        palabraSeleccionada, styleCorrectas);
            } catch (BadLocationException ex) {
                informaError(ex);
            }
            this.prog.getModelo().setTexto(this.textPane.getText());
            this.prog.notificar("Actualizar");
>>>>>>> sinPalabra
        }
    }
}
