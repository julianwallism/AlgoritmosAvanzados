package capitulo7.control;

import static capitulo7.Error.informaError;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import capitulo7.PorEventos;
import capitulo7.main;
import capitulo7.modelo.LUT;
import java.awt.Color;
import java.util.Locale;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Control extends Thread implements PorEventos {

    private final main prog;
    private boolean executat = false, donali = true;

    public Control(main p) {
        this.prog = p;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (donali) {
                    long startTime = System.currentTimeMillis();
                    muestreo(prog.getModelo().getImagen());
                    long time = System.currentTimeMillis() - startTime;
                    prog.getModelo().setTiempo(time);
                    this.prog.notificar("Ejecución terminada");
                    donali = false;
                }
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
            informaError(ex);
        }
    }

    /*
     * Método que predice el país de la bandera pasado por imagen.
     * Para ello cogemos N píxeles aleatorios de la imagen, calculamos el
     * porcentaje de cada color.
     *
     * Luego llamamos a un método que nos devuelve el país de la base de datos
     * que tiene una menor distancia con el porcentaje calculado.
     */
    private String muestreo(BufferedImage imagen) {
        int porcentajeMuestreo = prog.getModelo().getPorcentajeMuestreo();
        int N_muestreo = (imagen.getWidth() * imagen.getHeight() * porcentajeMuestreo) / 100;
        double[] colores = new double[14];
        LUT lut = new LUT();

        for (int i = 0; i < N_muestreo; i++) {
            int x = (int) (Math.random() * imagen.getWidth());
            int y = (int) (Math.random() * imagen.getHeight());
            Color color = new Color(imagen.getRGB(x, y));
            int idx = lut.lookUpColor(color);
            colores[idx]++;
        }

        for (int i = 0; i < 7; i++) {
            colores[i] /= N_muestreo;
        }

        // Menor distancia entre los colores
        String pais = menorDistancia(colores);
        prog.getModelo().setPais(pais);
        this.prog.notificar("Ejecución acabada");
        return pais;
    }

    /**
     * Método que devuleve el país de la base de datos que tiene menor distancia
     * al array de colores pasado por parámetro
     * 
     * @param colores
     * @return 
     */
    private String menorDistancia(double[] colores) {
        HashMap bd = prog.getModelo().getBD();
        String pais = "";
        double menor = Double.MAX_VALUE;
        // loop through the colors in the bd
        for (Object key : bd.keySet()) {
            double distancia = distanciaEuclidiana(colores, (double[]) bd.get(key));
            if (distancia < menor) {
                menor = distancia;
                pais = (String) key;
            }
        }
        return pais;
    }

    /**
     * Método que calcula la distancia euclidiana entre el array de colores 1 y el 2
     * @param colores1
     * @param colores2
     * @return 
     */
    private double distanciaEuclidiana(double[] colores1, double[] colores2) {
        double suma = 0;
        for (int i = 0; i < 7; i++) {
            suma += Math.pow(colores1[i] - colores2[i], 2);
        }
        return Math.sqrt(suma);
    }

    /**
     * Método que ejecuta la predicción para todos las banderas guardadas. 
     * 
     * Esto nos sirve para poder ver holísticamente el rendimiento de nuestro 
     * programa
     * 
     * Guardamos los resultados en el modelo
     */
    private void unitTest() {
        int numFiles = new File("flags/").listFiles().length;
        String[] resultados = new String[numFiles];
        String[] paisesReales = new String[numFiles];
        String[] paisesPredichos = new String[numFiles];
        float[] tiempo = new float[numFiles];
        String paisPredict, paisReal;
        int correctas= 0;
        int idx = 0;
        try {
            for (File f : new File("flags/").listFiles()) {
                if (f.getName().endsWith(".png")) {
                    long start = System.currentTimeMillis();
                    BufferedImage imagen = ImageIO.read(f);
                    paisPredict = muestreo(imagen);

                    Locale loc = new Locale("", f.getName().substring(0, f.getName().lastIndexOf(".")));
                    paisReal = loc.getDisplayCountry();

                    if (paisPredict.equals(paisReal)) {
                        resultados[idx] = "✅";
                        correctas++;
                    } else {
                        resultados[idx] = "🚫";
                    }
                    paisesReales[idx] = paisReal;
                    paisesPredichos[idx] = paisPredict;
                    tiempo[idx] = (float) (System.currentTimeMillis() - start) / 1000;
                    idx++;
                }
            }
            prog.getModelo().setCorrectas(correctas);
            prog.getModelo().setResultados(resultados);
            prog.getModelo().setPaisesReales(paisesReales);
            prog.getModelo().setPaisesPredichos(paisesPredichos);
            prog.getModelo().setTiempoUnitTest(tiempo);
            prog.notificar("Unit test terminados");
        } catch (IOException ex) {
            informaError(ex);
        }
    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Ejecuta control muestreo")) {
            if (!this.executat) {
                this.start();
                this.executat = true;
            } else {
                this.donali = true;
            }
        } else if (s.startsWith("Ejecuta control unit tests")) {
            unitTest();
        }
    }
}
