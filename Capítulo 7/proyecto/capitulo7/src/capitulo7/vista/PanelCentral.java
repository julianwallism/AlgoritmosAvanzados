package capitulo7.vista;

import static capitulo7.Error.informaError;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JPanel;
import capitulo7.main;
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
import javax.swing.LayoutStyle;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @authors Dawid Roch, Julià Wallis y Víctor Blanes
 */
public class PanelCentral extends JPanel {
    public static final int FPS = 24;
    private main p;
    public JFileChooser fileChooser;
    public JLabel label, label2, imageLabel;
    public JButton boton;

    public PanelCentral(main p) {
        this.p = p;
        label = new JLabel("Arrastra un archivo");
        label2 = new JLabel("o");
        fileChooser = new JFileChooser();
        try {
            fileChooser.setCurrentDirectory(new File((new File("./flags").getCanonicalPath())));
        } catch (IOException ex) {
            informaError(ex);
        }
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Imágenes", "png"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        try {
            imageLabel = new JLabel(new ImageIcon(ImageIO.read(new File("logo.png"))));
        } catch (IOException ex) {
            informaError(ex);
        }
        boton = new JButton("Elige un archivo");
        boton.setBackground(Color.white);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (boton.getText().contains("Eliminar")) {
                    try {
                        imageLabel.setIcon(new ImageIcon(ImageIO.read(new File("logo.png"))));
                    } catch (IOException ex) {
                        informaError(ex);
                    }
                    label.setText("Arrastra una imagen");
                    label2.setText("o");
                    boton.setText("Elige una imagen");
                    p.getModelo().setImagen(null);
                } else {
                    int res = fileChooser.showOpenDialog(PanelCentral.this);
                    if (res == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        try {
                            BufferedImage imagen = ImageIO.read(file);
                            imageLabel.setIcon(new ImageIcon(imagen));
                            p.getModelo().setImagen(imagen);
                        } catch (IOException ex) {
                            informaError(ex);
                        }
                        label.setText("Imagen seleccionada: " + file.getName());
                        label2.setText("");
                        boton.setText("Eliminar imagen seleccionada");
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
                        .addComponent(label)
                        .addGap(25)
                        .addComponent(imageLabel)
                        .addComponent(label2)
                        .addGap(25)
                        .addComponent(boton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 35, 100)
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
                        BufferedImage bufferedImage;
                        try {
                            bufferedImage = ImageIO.read(file);
                            imageLabel.setIcon(new ImageIcon(bufferedImage));
                            p.getModelo().setImagen(bufferedImage);
                        } catch (IOException ex) {
                            informaError(ex);
                        }
                        label.setText("Imagen seleccionada: " + file.getName());
                        label2.setText("");
                        boton.setText("Eliminar imagen seleccionada");
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
    private PanelCentral pan;

    public ProcesPintat(PanelCentral pc) {
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
