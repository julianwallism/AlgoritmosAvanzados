package capitol4.control;

import capitol4.main;
import capitol4.PerEsdeveniments;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
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
    private File fitxerOriginal, fitxerOutput;
    private int bufferSize;
    private int lengthCode = 0;
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
        fitxerOutput = new File(fitxerOriginal.getName() + ".huff");
        try {
            FileOutputStream fos = new FileOutputStream(fitxerOutput);
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
                System.out.println(offSetFinal);
                System.out.println(buffAux);
                fos.write(parse(buffAux));
                RandomAccessFile raf = new RandomAccessFile(fitxerOutput, "rw");
//                raf.open();
                raf.write(offSetFinal);
                raf.close();
            }
            fis.close();
            fos.close();
            prog.getModel().setFitxerOutput(fitxerOutput);
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

    // Parse string withy binary values to byte
    public byte[] parse(String s) {
        byte[] b = new byte[s.length() / 8];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) Integer.parseInt(s.substring(i * 8, (i + 1) * 8), 2);
        }
        return b;
    }

    public void decompress() {
        File fitxerComprimit = prog.getModel().getFitxerTriat();
        bufferSize = prog.getModel().getBufferSize();

        // Get things between first and second dot on the file name
        String extensio = fitxerComprimit.getName().substring(fitxerComprimit.getName().indexOf("."),
                fitxerComprimit.getName().lastIndexOf("."));
        String nomFitxer = fitxerComprimit.getName().substring(0, fitxerComprimit.getName().indexOf("."));
        File fitxerDescomprimit = new File(nomFitxer + "descomprimit." + extensio);
        try {
            // Abrimos el archivo
            FileInputStream fis = new FileInputStream(fitxerComprimit);
            FileOutputStream fos = new FileOutputStream(fitxerDescomprimit);
            // Leemos el primer byte -> Offset del último byte
            int offset = fis.readNBytes(1)[0];
            // Leemos el objeto serializado
            ObjectInputStream ois = new ObjectInputStream(fis);
            Node root = (Node) ois.readObject();

            int valorRetorn = 0;
            byte[] buffer = new byte[bufferSize];
            String buffAux = new String();
            String offAux;
            Byte ret;

            while (valorRetorn != -1) {
                ret = 0;
                valorRetorn = fis.read(buffer);
                for (int i = 0; i < valorRetorn; i++) {
                    buffAux += String.format("%8s", Integer.toBinaryString(buffer[i] & 0xFF)).replace(' ', '0');
                }

                while (ret != null) {
                    offAux = buffAux;
                    ret = decode(root, buffAux);
                    if (ret != null) {
                        System.out.print((char) (ret & 0xFF));
                        fos.write(ret);
                        buffAux = buffAux.substring(lengthCode, buffAux.length());
                    } else {
                        buffAux = offAux;
                    }
                    lengthCode = 0;
                }
            }
            
            //                if (buffAux.length() > 0) {
//                    offAux = buffAux;
//                }

//                // Convert all bytes in the buffer to String
//                for (int i = 0; i < valorRetorn; i++) {
//                    buffAux += Integer.toBinaryString(buffer[i]);
//                }
//                while(!ret.equals("-1")){
//                    ret = decode(root, buffAux);
//                    if(!ret.equals("-1")){
//                        fos.write(parse(ret));
//                        ret = 0;
//                    }
//                }
//                for (int i = 0; i < valorRetorn; i++) {
//                   // ret = (String) buffer[i];
//                    // Given the code, get the character
//                    buffAux += getChar(b, root);
//                }
//                int offSet = buffAux.length() % 8;
//                String offAux = buffAux.substring(0, buffAux.length() - offSet);
//                buffAux = buffAux.substring(buffAux.length() - offSet, buffAux.length());
//                fos.write(parse(offAux));
            fis.close();
            ois.close();
            fos.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error");
        }
    }

    // Decode the string with binary values
    private Byte decode(Node root, String s) {
        if (root.getLeft() == null && root.getRight() == null) {
            return root.getB();
        } else {
            lengthCode++;
            if (s.length() == 0) {
                return null;
            } else if (s.charAt(0) == '0') {
                return decode(root.getLeft(), s.substring(1));
            } else {
                return decode(root.getRight(), s.substring(1));
            }
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Comprime")) {
            compress();
            System.out.println("Compressió iniciada");
        } else if (s.startsWith("Descomprime")) {
            decompress();
            System.out.println("Descompression iniciada");
        } else if (s.startsWith("Aturar")) {
            this.seguir = false;
            System.out.println("Programa aturat");
        }
    }
}
