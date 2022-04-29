package capitol4.vista;

import capitol4.PerEsdeveniments;
import capitol4.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class Vista extends JFrame implements PerEsdeveniments {

    private main prog;
    private DragAndDrop dnd;
    private JPanel botons, resultats, info;
    private JButton comprimir, descomprimir;
    private JLabel etiqueta, original, comprimido;
    public JLabel label;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());

        dnd = new DragAndDrop(p);
        botons = new JPanel();
        resultats = new JPanel();
        info = new JPanel();

        comprimir = new JButton("Comprimir");
        descomprimir = new JButton("Descomprimir");

        etiqueta = new JLabel("Dropea un archivo aquí");
        original = new JLabel("El archivo original pesa: ");
        comprimido = new JLabel("El archivo comprimido pesa: ");

        resultats.add(original);
        resultats.add(comprimido);

        botons.add(comprimir);
        botons.add(descomprimir);
        info.add(botons, BorderLayout.NORTH);
        info.add(resultats, BorderLayout.SOUTH);

        dnd.add(etiqueta, BorderLayout.NORTH);

        // Click sobre el botó comprimeix
        comprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Comprime");
            }
        });

        // Click sobre el botó descomprimeix
        descomprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Descomprime");
            }
        });

        this.add(dnd, BorderLayout.CENTER);
        this.add(info, BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(800, 500));
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void updateEtiqueta(String text) {
        if (text.equals("Original")) {
            File archivo = prog.getModel().getFitxerTriat();
            printFileSize(archivo);
            String peso = printFileSize(archivo);
            original.setText("El archivo original pesa: " + peso);
        } else if (text.equals("Comprimido")) {
            File archivo = prog.getModel().getFitxerCompressat();
            printFileSize(archivo);
            String peso = printFileSize(archivo);
            comprimido.setText("El archivo comprimido pesa: " + peso);
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
        if (s.startsWith("Fitxer pujat")) {
            updateEtiqueta("Original");
        } else if (s.startsWith("Compresion realizada")) {
            updateEtiqueta("Comprimido");
        }
    }
}
