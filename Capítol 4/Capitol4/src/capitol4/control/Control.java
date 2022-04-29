package capitol4.control;

import capitol4.main;
import capitol4.PerEsdeveniments;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Control extends Thread implements PerEsdeveniments {

    private final main prog;
    private boolean seguir, executat;
    private File fitxerOriginal, fitxerCompressat;
    private int bufferSize;
    private HashMap<Byte, String> codes;

    public Control(main p) {
        this.prog = p;
        this.executat = false;
    }

    @Override
    public void run() {
        this.executat = true;
        while (true) {
            if (this.seguir) {
                resol();
                this.seguir = false;
            }
        }
    }

    private void resol() {
        System.out.println("resol");
    }

    // Create frequencies2 that uses the bufferSize
    public HashMap<Byte, Integer> frequencies() {
        fitxerOriginal = prog.getModel().getFitxerTriat();
        bufferSize = prog.getModel().getBufferSize();
        HashMap<Byte, Integer> freq = new HashMap<Byte, Integer>();
        try {
            FileInputStream fis = new FileInputStream(fitxerOriginal);
            int valorRetorn = 0;
            byte[] buffer = new byte[bufferSize];
            while (valorRetorn != -1) {
                valorRetorn = fis.read(buffer);
                for (int i = 0; i < valorRetorn; i++) {
                    byte b = buffer[i];
                    if (!freq.containsKey(b)) {
                        freq.put(b, 1);
                    } else {
                        freq.put(b, freq.get(b) + 1);
                    }
                }
            }
            fis.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
        return freq;
    }

    public void compress() {
        // Get frequency of each character in the file
        HashMap<Byte, Integer> freq = frequencies();
        bufferSize = prog.getModel().getBufferSize();
        // Create a priority queue of the characters in the file
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        for (byte b : freq.keySet()) {
            pq.add(new Node(b, freq.get(b)));
        }
        // Build Huffman tree
        while (pq.size() > 1) {
            Node left = pq.remove();
            Node right = pq.remove();
            Node parent = new Node(left, right);
            pq.add(parent);
        }
        // Get the root of the Huffman tree
        Node root = pq.remove();
        // Create a HashMap to store the codes of each character
        codes = new HashMap<Byte, String>();
        // Call the recursive method to build the codes
        buildCodes(root, "");

        // Create a new file to store the compressed file
        fitxerCompressat = new File(fitxerOriginal.getName() + ".huff");
        try {
            FileOutputStream fos = new FileOutputStream(fitxerCompressat);
            // Write the number of characters in the file
            fos.write(0);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(root);

            // Write the compressed file
            FileInputStream fis = new FileInputStream(fitxerOriginal);
            int valorRetorn = 0;
            byte[] buffer = new byte[bufferSize];
            String buffAux = new String();
            byte b;
            while (valorRetorn != -1) {
                valorRetorn = fis.read(buffer);
                for (int i = 0; i < valorRetorn; i++) {
                    b = buffer[i];
                    buffAux += codes.get(b);
                }
                int offSet = buffAux.length() % 8;
                String offAux = buffAux.substring(0, buffAux.length() - offSet);
                buffAux = buffAux.substring(buffAux.length() - offSet, buffAux.length());
                fos.write(parse(offAux));
            }

            if (buffAux.length() != 0) {
                int offSetFinal = 8 - buffAux.length();
                for (int i = 0; i < offSetFinal; i++) {
                    buffAux += "0";
                }
                fos.write(parse(buffAux));
                RandomAccessFile raf = new RandomAccessFile(fitxerCompressat, "rw");
//                raf.open();
                raf.write(offSetFinal);
            }

            fis.close();
            fos.close();
            prog.getModel().setFitxerCompressat(fitxerCompressat);
            prog.notificar("Compresion realizada");
        } catch (IOException e) {
            System.out.println("Error");
        }

    }

    private void buildCodes(Node root, String code) {
        if (root.getLeft() == null && root.getRight() == null) {
            codes.put(root.getB(), code);
        } else {
            buildCodes(root.getLeft(), code + "0");
            buildCodes(root.getRight(), code + "1");
        }
    }

    //Parse string withy binary values to byte
    public byte[] parse(String s) {
        byte[] b = new byte[s.length() / 8];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) Integer.parseInt(s.substring(i * 8, (i + 1) * 8), 2);
        }
        return b;
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Comprime")) {
            compress();
            System.out.println("Compressió iniciada");
        } else if (s.startsWith("Descomprime")) {
            System.out.println("Descompression iniciada");
        } else if (s.startsWith("Aturar")) {
            this.seguir = false;
            System.out.println("Programa aturat");
        }
    }
}
