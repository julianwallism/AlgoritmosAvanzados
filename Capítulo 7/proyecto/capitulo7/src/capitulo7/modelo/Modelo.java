package capitulo7.modelo;

import static capitulo7.Error.informaError;
import capitulo7.PorEventos;
import capitulo7.main;
import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Locale;
import javax.imageio.ImageIO;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Modelo implements PorEventos {

    private final main prog;
    private String pais = "";
    private BufferedImage imagen = null;
    private HashMap bd = null;
    private int porcentajeMuestreo = 20;
    private String[] resultados, paisesPredichos, paisesReales;
    private float[] tiempoUnitTest;
    private float tiempo;
    private int correctas;

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

    public String[] getResultados() {
        return resultados;
    }

    public void setResultados(String[] resultados) {
        this.resultados = resultados;
    }

    public String[] getPaisesPredichos() {
        return paisesPredichos;
    }

    public void setPaisesPredichos(String[] paisesPredichos) {
        this.paisesPredichos = paisesPredichos;
    }

    public String[] getPaisesReales() {
        return paisesReales;
    }

    public void setPaisesReales(String[] paisesReales) {
        this.paisesReales = paisesReales;
    }

    public float[] getTiempoUnitTest() {
        return tiempoUnitTest;
    }

    public void setTiempoUnitTest(float[] tiempoUnitTest) {
        this.tiempoUnitTest = tiempoUnitTest;
    }

    public float getTiempo() {
        return tiempo;
    }

    public void setTiempo(float tiempo) {
        this.tiempo = tiempo;
    }

    public int getCorrectas() {
        return correctas;
    }

    public void setCorrectas(int correctas) {
        this.correctas = correctas;
    }

    /**
     * Método que carga la base de datos de países.
     *
     * Si el base de datos ya esta cargada no hace nada. Si no esta cargada y no
     * existe la crea y la graba. Si no esta cargada y existe la lee.
     *
     */
    private void cargarBD() {
        try {
            // Si no tenemos la base de datos cargada, la cargamos
            if (bd == null) {
                // Si la base de datos no existe, la creamos
                File af = new File("basedatos.txt");
                if (!af.exists()) {
                    bd = new HashMap<>();
                    int i = 0;
                    // Para cada bandera llamamos a la función que la carga.
                    for (File f : new File("flags/").listFiles()) {
                        if (f.getName().endsWith(".png")) {
                            procesarBandera(f);
                        }
                    }
                    // Guardamos la base de datos en un archivo
                    grabarBD();
                } else {
                    // Si la base de datos existe, la leemos
                    leerBD();
                }
            }
        } catch (Exception ex) {
            informaError(ex);
        }
    }

    /**
     * Método que pasado un fichero de bandera, lo procesa y guarda el
     * porcentaje de colores que tiene la bandera
     *
     * Para hacerlo usamos la clase auxiliar dada por el profesor que nos
     * aproxima los colores de la bandera a: - Azul - Rojo - Verde - Blanco -
     * Negro - Naranja - Amarillo
     *
     * Una vez tenemos los porcentajes de colores los guardamos en el hashMap
     * con el nombre del fichero como llave y los porcentajes como valor.
     *
     * @param f
     */
    private void procesarBandera(File f) {
        try {
            LUT lut = new LUT();
            double[] colores = new double[14];
            BufferedImage img = ImageIO.read(f);
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color c = new Color(img.getRGB(i, j));
                    int idx = lut.lookUpColor(c);
                    colores[idx]++;
                }
            }
            // Divide por el número de pixeles
            for (int i = 0; i < colores.length; i++) {
                colores[i] /= (img.getWidth() * img.getHeight());
            }

            Locale loc = new Locale("", f.getName().substring(0, f.getName().lastIndexOf(".")));
            String nombre = loc.getDisplayCountry();

            bd.put(nombre, colores);
        } catch (Exception ex) {
            informaError(ex);
        }
    }

    /**
     * Método que lee la base de datos de un archivo
     */
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

    /**
     * Método que graba la base de datos en un archivo
     */
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
        if (s.startsWith("Ejecuta muestreo")) {
            cargarBD();
            prog.notificar("Ejecuta control muestreo");
        } else if (s.startsWith("Ejecuta unit tests")) {
            cargarBD();
            prog.notificar("Ejecuta control unit tests");
        }
    }
}
