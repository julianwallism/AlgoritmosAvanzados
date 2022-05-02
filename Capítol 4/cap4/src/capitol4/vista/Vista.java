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
import javax.swing.*;

public class Vista extends JFrame implements PerEsdeveniments {
    private main prog;
    private PanellCentral panellCentral;
    private JPanel botons, resultats, info, central;
    private JButton comprimir, descomprimir;
    private JLabel original, comprimido;
    private JProgressBar panellInferior;
    public JLabel label;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());

        panellCentral = new PanellCentral(p);
        panellCentral.setBorder(BorderFactory.createLineBorder(new Color(153, 217, 234), 2));
        botons = new JPanel();
        resultats = new JPanel();
        info = new JPanel();
        info.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(153, 217, 234)));
        central = new JPanel();
        central.setBackground(Color.white);
        central.setLayout(new FlowLayout(FlowLayout.CENTER));
        central.add(panellCentral);
        panellInferior = new JProgressBar();
        panellInferior.setBorderPainted(true);
        panellInferior.setIndeterminate(true);

        comprimir = new JButton("Comprimir");
        descomprimir = new JButton("Descomprimir");
        original = new JLabel();
        comprimido = new JLabel();

        resultats.setLayout(new FlowLayout(FlowLayout.RIGHT));
        resultats.add(original);
        resultats.add(comprimido);
        botons.setLayout(new FlowLayout(FlowLayout.LEFT));
        botons.add(comprimir);
        botons.add(descomprimir);
        info.add(botons);
        info.add(resultats);

        // Click sobre el botó comprimeix
        comprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File fitxerTriat = prog.getModel().getFitxerTriat();
                if (fitxerTriat==null) {
                    JOptionPane.showMessageDialog(null, "Selecciona un fitxer!");
                    return;
                }
                prog.notificar("Comprimeix");
            }
        });

        // Click sobre el botó descomprimeix
        descomprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File fitxerTriat = prog.getModel().getFitxerTriat();
                if (fitxerTriat==null || !fitxerTriat.getName().endsWith(".huff")) {
                    JOptionPane.showMessageDialog(null, "Selecciona un fitxer .huff vàlid");
                    return;
                }
                prog.notificar("Descomprimeix");
            }

        });

        this.setLayout(new BorderLayout());
        this.add(info, BorderLayout.NORTH);
        this.add(central, BorderLayout.CENTER);
        this.add(panellInferior, BorderLayout.SOUTH);
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

    private void updateEtiqueta(String text) {
        switch (text) {
            case "Original": {
                File archivo = prog.getModel().getFitxerTriat();
                printFileSize(archivo);
                String peso = printFileSize(archivo);
                original.setText("El archivo original pesa " + peso);
                break;
            }
            case "Original eliminado":
                original.setText("");
                comprimido.setText("");
                break;
            case "Comprimido": {
                File archivo = prog.getModel().getFitxerOutput();
                printFileSize(archivo);
                String peso = printFileSize(archivo);
                comprimido.setText("El archivo comprimido pesa " + peso);
                break;
            }
            default:
                break;
        }
    }

    public static String printFileSize(File file) {
        // size of a file (in bytes)
        long bytes = file.length();
        long kilobytes = (bytes / 1024);
        long megabytes = (kilobytes / 1024);
        long gigabytes = (megabytes / 1024);
        long terabytes = (gigabytes / 1024);
        long petabytes = (terabytes / 1024);
        long exabytes = (petabytes / 1024);
        long zettabytes = (exabytes / 1024);
        long yottabytes = (zettabytes / 1024);

        // return the first variable that isnt 0 starting from yottabytes to bytes
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
