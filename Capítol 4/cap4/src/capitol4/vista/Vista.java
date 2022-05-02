package capitol4.vista;

import static capitol4.MeuError.informaError;
import capitol4.PerEsdeveniments;
import capitol4.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import javax.swing.*;

public class Vista extends JFrame implements PerEsdeveniments {
    private main prog;
    private PanelCentral panelCentral;
    private JPanel botones, resultados, info, central;
    private JButton comprimir, descomprimir, muestraCódigos;
    private JLabel original, comprimido;
    private JProgressBar panelInferior;
    public JLabel label;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());

        panelCentral = new PanelCentral(p);
        panelCentral.setBorder(BorderFactory.createLineBorder(new Color(153, 217, 234), 2));

        botones = new JPanel();
        resultados = new JPanel();

        info = new JPanel();
        info.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(153, 217, 234)));

        central = new JPanel();
        central.setBackground(Color.white);
        central.setLayout(new FlowLayout(FlowLayout.CENTER));
        central.add(panelCentral);

        panelInferior = new JProgressBar();
        panelInferior.setBorderPainted(true);
        panelInferior.setIndeterminate(true);

        comprimir = new JButton("Comprimir");
        descomprimir = new JButton("Descomprimir");
        muestraCódigos = new JButton("Mostrar códigos");

        original = new JLabel();
        comprimido = new JLabel();

        resultados.setLayout(new FlowLayout(FlowLayout.RIGHT));
        resultados.add(original);
        resultados.add(comprimido);

        botones.setLayout(new FlowLayout(FlowLayout.LEFT));
        botones.add(comprimir);
        botones.add(descomprimir);
        botones.add(muestraCódigos);

        info.add(botones);
        info.add(resultados);

        // Click sobre el botón comprime
        comprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File ficheroInput = prog.getModelo().getFicheroInput();
                if (ficheroInput==null) {
                    JOptionPane.showMessageDialog(null, "Selecciona un fichero!");
                    return;
                }
                prog.notificar("Comprime");
            }
        });

        // Click sobre el botón descomprime
        descomprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File ficheroInput = prog.getModelo().getFicheroInput();
                if (ficheroInput==null || !ficheroInput.getName().endsWith(".huff")) {
                    JOptionPane.showMessageDialog(null, "Selecciona un fichero .huff válido!");
                    return;
                }
                prog.notificar("Descomprime");
            }
        });

        // Click sobre el botón muestra códigos
        muestraCódigos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<Byte, String> codes = prog.getModelo().getCodes();
                if (codes==null) {
                    JOptionPane.showMessageDialog(null, "No hay códigos!");
                    return;
                }
                JDialog dialog = new JDialog(Vista.this, "Códigos", true);
                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                for (Byte b : codes.keySet()) {
                    textArea.append((char) (b & 0xFF) + ": " + codes.get(b) + "\n");
                }
                //textArea.setText(codes.toString());
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 500));
                dialog.add(scrollPane);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

        this.setLayout(new BorderLayout());
        this.add(info, BorderLayout.NORTH);
        this.add(central, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(500, 500));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            informaError(ex);
        }
        SwingUtilities.updateComponentTreeUI(this);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Actualitza els labels de la vista
     * @param text
     */
    private void updateEtiqueta(String text) {
        switch (text) {
            case "Original": {
                File archivoInput = prog.getModelo().getFicheroInput();
                printFileSize(archivoInput);
                String peso = printFileSize(archivoInput);
                original.setText("El archivo Input pesa " + peso);
                break;
            }
            case "Original eliminado":
                original.setText("");
                comprimido.setText("");
                break;
            case "Comprimido": {
                File archivoOutput = prog.getModelo().getFicheroOutput();
                printFileSize(archivoOutput);
                String peso = printFileSize(archivoOutput);
                comprimido.setText("El archivo Output pesa " + peso);
                break;
            }
            default:
                break;
        }
    }
    /**
     * Método que a partir del tamanyo del fichero en bytes, hace la conversión 
     * a kilobytes, megabytes, etc.
     * @param file
     * @return
     */
    public static String printFileSize(File file) {
        long bytes = file.length();
        long kilobytes = (bytes / 1024);
        long megabytes = (kilobytes / 1024);
        long gigabytes = (megabytes / 1024);
        long terabytes = (gigabytes / 1024);
        long petabytes = (terabytes / 1024);
        long exabytes = (petabytes / 1024);
        long zettabytes = (exabytes / 1024);
        long yottabytes = (zettabytes / 1024);

        if (yottabytes != 0) {
            return yottabytes + " YB";
        } else if (zettabytes != 0) {
            return zettabytes + " ZB";
        } else if (petabytes != 0) {
            return petabytes + " PB";
        } else if (exabytes != 0) {
            return exabytes + " EB";
        } else if (terabytes != 0) {
            return terabytes + " TB";
        } else if (gigabytes != 0) {
            return gigabytes + " GB";
        } else if (megabytes != 0) {
            return megabytes + " MB";
        } else if (kilobytes != 0) {
            return kilobytes + " KB";
        } else if (bytes != 0) {
            return bytes + " B";
        } else {
            return "0";
        }
    }

    /**
     * Método notificar de la interfaz de esdevenimientos
     * 
     * Puede recibir los siguientes eventos:
     * - "Fichero Subido": llama a updateEtiqueta("Original")
     * - "Fichero Eliminado": llama a updateEtiqueta("Original eliminado")
     * - "Compresión realizada": llama a updateEtiqueta("Comprimido")
     * @param s
     */
    @Override
    public void notificar(String s) {
        if (s.startsWith("Fichero subido")) {
            updateEtiqueta("Original");
        } else if (s.startsWith("Fichero eliminado")) {
            updateEtiqueta("Original eliminado");
        } else if (s.startsWith("Compresion realizada")) {
            updateEtiqueta("Comprimido");
        }
    }
}
