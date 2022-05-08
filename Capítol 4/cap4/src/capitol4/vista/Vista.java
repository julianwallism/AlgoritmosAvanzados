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
    private JPanel panelSuperior, botones, resultados, central;
    private JButton comprimir, descomprimir, info;
    private JProgressBar panelInferior;
    public JLabel original, comprimido;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());

        panelCentral = new PanelCentral(p);
        panelCentral.setBorder(BorderFactory.createLineBorder(new Color(153, 217, 234), 2));

        botones = new JPanel();
        resultados = new JPanel();

        panelSuperior = new JPanel();
        panelSuperior.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(153, 217, 234)));

        central = new JPanel();
        central.setBackground(Color.white);
        central.setLayout(new FlowLayout(FlowLayout.CENTER));
        central.add(panelCentral);

        panelInferior = new JProgressBar();
        panelInferior.setBorderPainted(true);

        comprimir = new JButton("Comprimir");
        descomprimir = new JButton("Descomprimir");
        info = new JButton("Info");

        original = new JLabel();
        comprimido = new JLabel();

        resultados.setLayout(new FlowLayout(FlowLayout.RIGHT));
        resultados.add(original);
        resultados.add(comprimido);

        botones.setLayout(new FlowLayout(FlowLayout.LEFT));
        botones.add(comprimir);
        botones.add(descomprimir);
        botones.add(info);

        panelSuperior.add(botones);
        panelSuperior.add(resultados);

        // Click sobre el botón comprime
        comprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File ficheroInput = prog.getModelo().getFicheroInput();
                if (ficheroInput == null) {
                    JOptionPane.showMessageDialog(null, "Selecciona un fichero!");
                    return;
                }
                panelInferior.setIndeterminate(true);
                prog.notificar("Comprime");
            }
        });

        // Click sobre el botón descomprime
        descomprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File ficheroInput = prog.getModelo().getFicheroInput();
                if (ficheroInput == null || !ficheroInput.getName().endsWith(".huff")) {
                    JOptionPane.showMessageDialog(null, "Selecciona un fichero .huff válido!");
                    return;
                }
                panelInferior.setIndeterminate(true);
                prog.notificar("Descomprime");
            }
        });

        // Create an actionListener on the button info that creates a jdialog with the
        // info
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<Byte, String> codes = prog.getModelo().getCodes();
                File archivoInput = prog.getModelo().getFicheroInput();
                File archivoOutput = prog.getModelo().getFicheroOutput();
                Double entropia = prog.getModelo().getEntropia();
                Double entropiaReal = prog.getModelo().getEntropiaReal();
                Double expectedSize = prog.getModelo().getExpectedSize();

                JDialog dialog = new JDialog(Vista.this, "Informacion", true);
                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                // If input is not null, print the file size
                if (archivoInput != null) {
                    textArea.append("Tamaño del fichero input:\t" + printFileSize(archivoInput.length()) + "\n");
                }
                if (archivoOutput != null) {
                    textArea.append("Tamaño del fichero output:\t" + printFileSize(archivoOutput.length()) + "\n");
                }
                if (expectedSize != 0.0) {
                    textArea.append("Tamaño esperado del fichero:\t" + printFileSize(expectedSize.longValue()) + "\n");
                }
                if (archivoInput != null && archivoOutput != null) {
                    double compressionRate = ((double) archivoOutput.length() / (double) archivoInput.length()) * 100;
                    String compressionRateString = String.format("%.3f", compressionRate);
                    textArea.append("Ratio de compresion:\t\t" + compressionRateString + "%\n");
                }
                if (entropia != 0.0) {
                    String entropiaString = String.format("%.3f", entropia);
                    textArea.append("Entropia teórica:\t\t" + entropiaString + "\n");
                }
                if (entropiaReal != 0.0) {
                    String entropiaRealString = String.format("%.3f", entropiaReal);
                    textArea.append("Entropia real:\t\t\t" + entropiaRealString + "\n");
                }
                if (codes != null) {
                    textArea.append("\nCódigos");
                    for (Byte key : codes.keySet()) {
                        textArea.append((char) (key & 0xFF) + ": " + codes.get(key) + "\n");
                    }
                }
                // textArea.setText(codes.toString());
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 500));
                dialog.add(scrollPane);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

        this.setLayout(new BorderLayout());
        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(central, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(800, 600));

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
     * Actualiza los labels de la vista
     *
     * @param text
     */
    private void updateEtiqueta(String text) {
        switch (text) {
            case "Original": {
                File archivoInput = prog.getModelo().getFicheroInput();
                printFileSize(archivoInput.length());
                String peso = printFileSize(archivoInput.length());
                original.setText("El archivo Input pesa " + peso);
                break;
            }
            case "Original eliminado": {
                original.setText("");
                comprimido.setText("");
                break;
            }
            case "Realizado": {
                File archivoOutput = prog.getModelo().getFicheroOutput();
                printFileSize(archivoOutput.length());
                String peso = printFileSize(archivoOutput.length());
                comprimido.setText("El archivo Output pesa " + peso);
                break;
            }
            default:
                break;
        }
    }

    /**
     * Método que a partir del tamaño del fichero en bytes, hace la conversión a
     * kilobytes, megabytes, etc.
     *
     * @param file
     * @return
     */
    public static String printFileSize(Long bytes) {
        long kilobytes = (bytes / 1024);
        long megabytes = (kilobytes / 1024);
        long gigabytes = (megabytes / 1024);
        long terabytes = (gigabytes / 1024);

        if (terabytes != 0) {
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
     * Método notificar de la interfaz de eventos
     *
     * Puede recibir los siguientes eventos: - "Fichero Subido": llama a
     * updateEtiqueta("Original") - "Fichero Eliminado": llama a
     * updateEtiqueta("Original eliminado") - "Compresión realizada": llama a
     * updateEtiqueta("Comprimido") - "Entropia": llama a
     * updateEtiqueta("Entropia")
     *
     * @param s
     */
    @Override
    public void notificar(String s) {
        if (s.startsWith("Fichero subido")) {
            updateEtiqueta("Original");
        } else if (s.startsWith("Fichero eliminado")) {
            updateEtiqueta("Original eliminado");
        } else if (s.startsWith("Compresion realizada")) {
            panelInferior.setIndeterminate(false);
            updateEtiqueta("Realizado");
        } else if (s.startsWith("Descompresion realizada")) {
            panelInferior.setIndeterminate(false);
            updateEtiqueta("Realizado");
        }
    }
}
