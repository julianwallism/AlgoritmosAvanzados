/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitol4.vista;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JPanel;
import capitol4.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.util.List;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author walli
 */
public class DragAndDrop extends JPanel {

    private main p;
    public JLabel label;

    public DragAndDrop(main p) {
        this.p = p;
        label = new JLabel("Arxiu: ");

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("icon2.png"));
        } catch (IOException ex) {
        }

        add(new JLabel(new ImageIcon(myPicture)), BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
        this.setBackground(Color.white);
        this.setVisible(true);

    }

    DropTarget dropTarget = new DropTarget(this, new DropTargetListener() {
        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            System.out.println("dragEnter");
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            System.out.println("dragOver");
            setBackground(new Color(153, 217, 234));
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
            System.out.println("dropActionChanged");
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
            System.out.println("dragExit");
            setBackground(Color.white);
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            // Get the file name out of the incoming data
            setBackground(Color.white);
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            Transferable tr = dtde.getTransferable();
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            for (DataFlavor flavor : flavors) {
                try {
                    if (flavor.isFlavorJavaFileListType()) {
                        List files = (List) tr.getTransferData(flavor);
                        File file = (File) files.get(0);
                        label.setText("Arxiu: " + file.getName());
                        p.getModel().setFitxerTriat(file);
                        p.notificar("Fitxer pujat");
                    }
                } catch (UnsupportedFlavorException | IOException ex) {
                    Logger.getLogger(DragAndDrop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });
}
