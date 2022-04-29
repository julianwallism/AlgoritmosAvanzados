package capitol4.vista;

import static capitol4.MeuError.informaError;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JPanel;
import capitol4.main;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class PanellCentral extends JPanel {
    public static final int FPS = 24;
    private main p;
    public JLabel label, label2, imageLabel;
    public JButton boton;

    public PanellCentral(main p) {
        this.p = p;
        label = new JLabel("Arrastra un archivo");
        label2 = new JLabel("o");
        BufferedImage fileImage = null;
        try {
            fileImage = ImageIO.read(new File("icon2.png"));
        } catch (IOException ex) {
            informaError(ex);
        }
        imageLabel = new JLabel(new ImageIcon(fileImage));
        boton = new JButton("Elige un archivo");
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (boton.getText().contains("Eliminar")) {
                    label.setText("Arrastra un archivo");
                    label2.setText("o");
                    boton.setText("Elige un archivo");
                    p.notificar("Fichero eliminado");
                } else {
                    JFileChooser escogerArchivo = new JFileChooser();
                    int res = escogerArchivo.showOpenDialog(PanellCentral.this);
                    if (res == JFileChooser.APPROVE_OPTION) {
                        File file = escogerArchivo.getSelectedFile();
                        label.setText("Archivo seleccionado: " + file.getName());
                        label2.setText("");
                        boton.setText("Eliminar archivo seleccionado");
                        p.getModel().setFitxerTriat(file);
                        p.notificar("Fichero subido");
                    }
                }
            }
        });

        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(label)
                        .addComponent(imageLabel)
                        .addComponent(label2)
                        .addComponent(boton)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(50)
                        .addComponent(label)
                        .addComponent(imageLabel)
                        .addComponent(label2)
                        .addComponent(boton)
                        .addGap(50)
        );
        this.setLayout(layout);
        this.setBackground(Color.white);
        this.setVisible(true);
    }

    DropTarget dropTarget = new DropTarget(this, new DropTargetListener() {
        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            setBackground(new Color(153, 217, 234));
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
            setBackground(Color.white);
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            setBackground(Color.white);
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            Transferable tr = dtde.getTransferable();
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            for (DataFlavor flavor : flavors) {
                try {
                    if (flavor.isFlavorJavaFileListType()) {
                        List files = (List) tr.getTransferData(flavor);
                        File file = (File) files.get(0);
                        label.setText("Archivo seleccionado: " + file.getName());
                        label2.setText("");
                        boton.setText("Eliminar fichero seleccionado");
                        p.getModel().setFitxerTriat(file);
                        p.notificar("Fichero subido");
                    }
                } catch (UnsupportedFlavorException | IOException ex) {
                    informaError(ex);
                }
            }
        }
    });
    
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    public void paint(Graphics gr) {
        super.paint(gr);
    }
}

class ProcesPintat extends Thread {
    private PanellCentral pan;

    public ProcesPintat(PanellCentral pc) {
        pan = pc;
    }

    public void run() {
        long temps = System.nanoTime();
        long tram = 1000000000L / pan.FPS;
        while (true) {
            if ((System.nanoTime() - temps) > tram) {
                pan.repaint();
                temps = System.nanoTime();
                espera((long) (tram / 2000000));
            }
        }
    }

    private void espera(long t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {
            informaError(e);
        }
    }
}
