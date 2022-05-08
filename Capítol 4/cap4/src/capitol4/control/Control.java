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
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Control extends Thread implements PerEsdeveniments {

    private final main prog;
    private int lengthCode = 0;
    private HashMap<Byte, String> codes;

    public Control(main p) {
        this.prog = p;
    }

    /**
     * Método que a partir de la frecuencia de cada Byte, calcula el código
     * Huffman y comprime el ficheroInput en el ficheroOutput.
     *
     * A la hora de escribir el fichero comprimido escribirá el offset del
     * último byte, el árbol de Huffman serializado y el contenido del
     * ficheroInput en sí. Para hacerlo hace uso de un buffer.
     */
    public void compress() {
        // Del modelo obtenemos el ficheroInput y el tamaño del buffer
        File ficheroInput = prog.getModelo().getFicheroInput();
        int bufferSize = prog.getModelo().getBufferSize();
        // Obtenemos la frecuencia de cada Byte en el ficheroInput mediant el
        // método frecuencias(File) y lo almacenamos en un hash
        HashMap<Byte, Integer> freq = frecuencias(ficheroInput);
        // Creamos una Cola de prioridad para almacenar los Nodos
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        // Para cada Byte del hash, creamos un Nodo con su frecuencia y lo
        // añadimos a la cola
        for (byte b : freq.keySet()) {
            pq.add(new Node(b, freq.get(b)));
        }
        // Construimos el árbol de Huffman
        while (pq.size() > 1) {
            Node left = pq.remove();
            Node right = pq.remove();
            Node parent = new Node(left, right);
            pq.add(parent);
        }
        // Obtenemos el árbol de Huffman
        Node root = pq.remove();
        // Creamos un hash para almacenar los códigos de cada Byte
        codes = new HashMap<Byte, String>();
        // Obtenemos el código Huffman de cada Byte mediante el método getCode(Node,
        // String)
        // y lo almacenamos en el hash
        buildCodes(root, "");

        // Creamos el ficheroOutput, donde comprimiremos el ficheroInput
        File ficheroOutput = new File(ficheroInput.getName() + ".huff");
        try {
            // Para realizar la compresión necesitaremos un fis del ficheroInput,
            // para leer el ficheroInput; un oos del ficheroOutput, para escribir
            // el árbol de Huffman serializado; y un fos del ficheroOutput, para
            // escribir el fichero comprimido.

            // Lo primero que haremos será escribir el offset del último byte,
            // que de momento será 0, luego escribiremos el árbol de Huffman.
            FileOutputStream fos = new FileOutputStream(ficheroOutput);
            fos.write(0);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(root);

            FileInputStream fis = new FileInputStream(ficheroInput);

            // Para realizar la escritura del fichero comprimido, necesitaremos:
            // un int que almacenará el número de bytes leídos, un array de bytes
            // para almacenar el buffer, una string para almacenar el código Huffman
            // y un byte auxiliar .
            int valorRetorn = 0;
            byte[] buffer = new byte[bufferSize];
            String buffAux = new String();
            byte b;
            // Mientras no se haya llegado al final del fichero
            while (valorRetorn != -1) {
                // Leemos el fichero y lo almacenamos en el buffer,
                // devuelve el número de bytes leídos
                valorRetorn = fis.read(buffer);
                // Recorremos el buffer. Almacenamos el código Huffman del byte en
                // buffAux
                for (int i = 0; i < valorRetorn; i++) {
                    b = buffer[i];
                    buffAux += codes.get(b);
                }

                // Como los códigos de Huffman son de tamaño variable y no se puede
                // escribir una cadena de bits, necesitamos una cadena auxiliar
                // que contenga los bits que quedan por escribir.
                // Lo que hacemos es guardar los bytes que sí que podemos escribir
                // en offAux, y los que no, en buffAux.
                // Luego escribimos en fichero el contenido de offAux. De esta
                // manera en la siguiente iteración buffAux contiene los bits que
                // no se han podido escribir en esta iteración.
                int offSet = buffAux.length() % 8;
                String offAux = buffAux.substring(0, buffAux.length() - offSet);
                buffAux = buffAux.substring(buffAux.length() - offSet, buffAux.length());
                fos.write(parse(offAux));
            }
            // Si cuando acabamos de leer el fichero, buffAux no está vacío,
            // significa que quedan bits por escribir.
            // Calcularemos el offSet del último byte, es decir, el número de
            // bits que le faltan para completar un byte. Y haremos padding de
            // ceros.
            // Luego escribimos este último byte y el número de bits que le faltan
            // para completar un byte, en la primera posición del fichero.
            if (buffAux.length() != 0) {
                int offSetFinal = 8 - buffAux.length();
                for (int i = 0; i < offSetFinal; i++) {
                    buffAux += "0";
                }
                fos.write(parse(buffAux));
                // Usamos un raf para escribir el número de bits que le faltan
                // para completar un byte en el fichero, ya que el raf se posicionará
                // en la primera posición del fichero.
                RandomAccessFile raf = new RandomAccessFile(ficheroOutput, "rw");
                raf.write(offSetFinal);
                raf.close();
            }
            // Cerramos los ficheros
            fis.close();
            fos.close();
            // Hacemos un set del ficheroOutput
            prog.getModelo().setFicheroOutput(ficheroOutput);
            prog.getModelo().setCodes(codes);
            entropy(freq);
            expectedSize(freq);
            // Notificamos al programa que se ha completado la compresión
            prog.notificar("Compresion realizada");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    /**
     * Metódo que decomprime el ficheroInput y crea uno nuevo con los contenidos
     * descomprimidos
     */
    public void decompress() {
        // Del modelo obtenemos el fichero escogido y el tamaño del buffer.
        File ficheroInput = prog.getModelo().getFicheroInput();
        int bufferSize = prog.getModelo().getBufferSize();

        // A partir del nombre del archivo escogido obtenemos la el nombre y su
        // extensión para crear un nuevo fichero (nombreOriginaldescomprimido.extension)
        String extension = ficheroInput.getName().substring(ficheroInput.getName().indexOf("."),
                ficheroInput.getName().lastIndexOf("."));
        String nombreFichero = ficheroInput.getName().substring(0, ficheroInput.getName().indexOf("."));

        File ficheroOutput = new File(nombreFichero + "_Descomprimido" + extension);
        try {
            // Abrimos un fis para el ficheroInput y un fos para el ficheroOutput
            FileInputStream fis = new FileInputStream(ficheroInput);
            FileOutputStream fos = new FileOutputStream(ficheroOutput);
            // Leemos el primer byte -> Offset del último byte
            int offset = fis.readNBytes(1)[0];
            // Leemos el objeto serializado
            ObjectInputStream ois = new ObjectInputStream(fis);
            Node root = (Node) ois.readObject();

            // Para la lectura del resto del fichero necesitaremos un int para el
            // valor de retorno de la lectura, un byte[] para el buffer, una String
            // donde meter los bytes leidos y un Byte donde guardar el byte descodificado
            int valorRetorno = 0;
            byte[] buffer = new byte[bufferSize];
            String buffAux = new String();
            Byte ret;
            // Mientras no se haya llegado al final del fichero
            while (valorRetorno != -1) {
                ret = 0;
                // Leemos el fichero y lo almacenamos en el buffer,
                // devuelve el número de bytes leídos
                valorRetorno = fis.read(buffer);
                // Convertimos los bytes del buffer a String y los concatenamos a buffAux
                for (int i = 0; i < valorRetorno; i++) {
                    buffAux += String.format("%8s", Integer.toBinaryString(buffer[i] & 0xFF)).replace(' ', '0');
                }
                // Si valor retorno es menor que bufferSize, borramos los ultimos "offset" bits
                // de buffAux
                if (valorRetorno < bufferSize && valorRetorno > offset) {
                    buffAux = buffAux.substring(0, buffAux.length() - offset);
                }

                // Mientras no se haya llegado al final del buffer
                while (ret != null) {
                    // Decodificamos un byte
                    ret = decode(root, buffAux);
                    // Si no es null, significa que se ha podido decodificar,
                    // así que lo escribimos en el fichero y lo borramos de buffAux
                    if (ret != null) {
                        fos.write(ret);
                        buffAux = buffAux.substring(lengthCode, buffAux.length());
                    }
                    lengthCode = 0;
                }
            }
            // Cerramos los ficheros
            fis.close();
            ois.close();
            fos.close();
            prog.getModelo().setFicheroOutput(ficheroOutput);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error");
        }
    }

    /**
     * Método que calcula la frecuencia de cada Byte en el fichero de entrada
     *
     * @param ficheroInput
     * @return HashMap<Byte, Integer>
     */
    public HashMap<Byte, Integer> frecuencias(File ficheroInput) {
        // Obtenemos el tamaño del buffer del modelo
        int bufferSize = prog.getModelo().getBufferSize();
        // Creamos un hash de bytes y sus frecuencias
        HashMap<Byte, Integer> freq = new HashMap<Byte, Integer>();
        try {
            // Creamos un FileInputStream para leer el fichero de entrada
            FileInputStream fis = new FileInputStream(ficheroInput);
            // Creamos un array de bytes para almacenar el buffer
            int valorRetorn = 0;
            byte[] buffer = new byte[bufferSize];

            // Mientras no se haya llegado al final del fichero
            while (valorRetorn != -1) {
                // Leemos el fichero y lo almacenamos en el buffer,
                // devuelve el número de bytes leídos
                valorRetorn = fis.read(buffer);
                // Recorremos el buffer. Si el byte no está en el hash lo añadimos,
                // si está, incrementamos su frecuencia
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

    /**
     * Método recursivo que construye el código de Huffman
     *
     * @param root
     * @param code
     */
    private void buildCodes(Node root, String code) {
        if (root.getLeft() == null && root.getRight() == null) {
            codes.put(root.getB(), code);
        } else {
            buildCodes(root.getLeft(), code + "0");
            buildCodes(root.getRight(), code + "1");
        }
    }

    /**
     * Método recursivo que decodifica el primer byte del buffer
     *
     * Le pasamos un String que contiene los bits a decodificar y un nodo raíz
     * del árbol de Huffman. Va decodficando los bits hasta que encuentra un
     * nodo hoja, en ese momento retornará el byte decodificado. Vamos guardando
     * la longitud del código en lengthCode para poder borrarlo del buffer. En
     * el caso de que no encuentre un nodo hoja ya que el código no es completo
     * retornará null, para que se pueda decodificar en la siguiente lectura
     *
     * @param root
     * @param s
     * @return Byte decodificado
     */
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

    /**
     * Método que parsea un String a un array de bytes
     *
     * @param s
     * @return
     */
    public byte[] parse(String s) {
        byte[] b = new byte[s.length() / 8];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) Integer.parseInt(s.substring(i * 8, (i + 1) * 8), 2);
        }
        return b;
    }

    // Method that calculates the entropy
    private void entropy(HashMap<Byte, Integer> freq) {
        double entropy = 0;
        File input = prog.getModelo().getFicheroInput();
        File output = prog.getModelo().getFicheroOutput();

        for (Map.Entry<Byte, Integer> entry : freq.entrySet()) {
            double probability = (double) entry.getValue() / input.length();
            entropy += probability * (Math.log(probability) / Math.log(2));
        }
        entropy = -entropy;
        prog.getModelo().setEntropia(entropy);

        entropy = 0;
        for (Map.Entry<Byte, Integer> entry : freq.entrySet()) {
            double probability = (double) entry.getValue() / output.length();
            entropy += probability * (Math.log(probability) / Math.log(2));
        }

        double entropiaReal;
        double numSimbolos = 0.0;
        // Calculate the number of symbols in the file given the frequency map
        for (Map.Entry<Byte, Integer> entry : freq.entrySet()) {
            numSimbolos += (double) entry.getValue();

        }
        System.out.println(output.length() * 8);
        System.out.println(numSimbolos);
        entropiaReal = (output.length() * 8) / numSimbolos;
        prog.getModelo().setEntropiaReal(entropiaReal);
    }

    /**
     * Método que calcula el tamaño esperado del fichero de output mediante a la
     * tabla de frecuencias
     *
     * @param freq
     */
    private void expectedSize(HashMap<Byte, Integer> freq) {
        double expectedSize = 0;
        for (Map.Entry<Byte, Integer> entry : freq.entrySet()) {
            expectedSize += entry.getValue() * codes.get(entry.getKey()).length();
        }
        expectedSize = expectedSize / 8;
        prog.getModelo().setExpectedSize(expectedSize);
    }

    /**
     * Método notificar de la interfaz de esdevenimientos.
     *
     * Puede recibir dos tipos de mensajes: - Comprime: Se llama al método
     * compress() - Descomprime: Se llama al método decompress()
     *
     * @param s
     */
    @Override
    public void notificar(String s) {
        if (s.startsWith("Comprime")) {
            compress();
            System.out.println("Compressió iniciada");
        } else if (s.startsWith("Descomprime")) {
            decompress();
            System.out.println("Descompression iniciada");
        }
    }
}
