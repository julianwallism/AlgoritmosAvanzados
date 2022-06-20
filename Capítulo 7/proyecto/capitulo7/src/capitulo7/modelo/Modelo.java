package capitulo7.modelo;

import static capitulo7.Error.informaError;
import capitulo7.PorEventos;
import capitulo7.main;
import java.awt.Color;
import ltimbase.dades.colores.Paleta;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Modelo implements PorEventos {

    private final main prog;
    private String pais = "";
    private BufferedImage imagen = null;
    private HashMap bd;
    private int porcentajeMuestreo = 20;

    public HashMap getBD() {
        return bd;
    }

    public int getPorcentajeMuestreo() {
        return porcentajeMuestreo;
    }

    public void setPorcentajeMuestreo(int porcentajeMuestreo) {
        this.porcentajeMuestreo = porcentajeMuestreo;
    }

    public Modelo(main p) {
        prog = p;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    private void cargarBD() {
        try {
            if (bd == null) {
                File af = new File("basedatos.txt");
                if (!af.exists()) { // la base de datos no está hecha
                    bd = new HashMap<>();
                    // For each file in the directory call the function to process it
                    for (File f : new File("flags/").listFiles()) {
                        if (f.getName().endsWith(".png")) {
                            procesarBandera(f);
                        }
                    }
                    grabarBD();
                } else {
                    leerBD();
                }
            }
        } catch (Exception ex) {
            informaError(ex);
        }
    }

    private void procesarBandera(File f) {
        try {
            Paleta p = new Paleta();
            double[] colores = new double[7];
            BufferedImage img = ImageIO.read(f);
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color c = new Color(img.getRGB(i, j));
                    int idx = p.analizarColor(c);
                    colores[idx]++;
                }
            }
            // Divide por el número de pixeles
            for (int i = 0; i < colores.length; i++) {
                colores[i] /= img.getWidth() * img.getHeight();
            }

            bd.put(f.getName(), colores);
        } catch (Exception ex) {
            informaError(ex);
        }
    }

    private void leerBD() {
        try {
            FileInputStream fis = new FileInputStream("basedatos.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            bd = (HashMap) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            informaError(ex);
        }
    }

    private void grabarBD() {
        try {
            FileOutputStream fos = new FileOutputStream("basedatos.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bd);
            oos.close();
        } catch (Exception ex) {
            informaError(ex);
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Ejecutar")) {
            cargarBD();
        }
    }
}
