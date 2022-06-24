package capitulo7.control;

import static capitulo7.Error.informaError;

import java.util.HashMap;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import capitulo7.PorEventos;
import capitulo7.main;
import java.awt.Color;
import ltimbase.dades.colores.Paleta;

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
                    // Count time
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
        double[] colores = new double[7];
        Paleta p = new Paleta();

        for (int i = 0; i < N_muestreo; i++) {
            int x = (int) (Math.random() * imagen.getWidth());
            int y = (int) (Math.random() * imagen.getHeight());
            Color color = new Color(imagen.getRGB(x, y));
            int idx = p.analizarColor(color);
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

    // Find the minimum distance between the color given and the colors in the bd
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

    // Calculate the Euclidean distance between two colors
    private double distanciaEuclidiana(double[] colores1, double[] colores2) {
        double suma = 0;
        for (int i = 0; i < 7; i++) {
            suma += Math.pow(colores1[i] - colores2[i], 2);
        }
        return Math.sqrt(suma);
    }

    private void unitTest() {
        // num of files in flags/
        int numFiles = new File("flags/").listFiles().length;
        String[] resultados = new String[numFiles];
        String[] paisesReales = new String[numFiles];
        String[] paisesPredichos = new String[numFiles];
        float[] tiempo = new float[numFiles];
        String paisPredict, paisReal;
        int idx = 0;
        try {
            for (File f : new File("flags/").listFiles()) {
                if (f.getName().endsWith(".png")) {
                    long start = System.currentTimeMillis();
                    BufferedImage imagen = ImageIO.read(f);
                    paisPredict = muestreo(imagen);
                    paisReal = f.getName();
                    if (paisPredict.equals(paisReal)) {
                        resultados[idx] = "✅";
                    } else {
                        resultados[idx] = "🚫";
                    }
                    paisesReales[idx] = paisReal;
                    paisesPredichos[idx] = paisPredict;
                    tiempo[idx] = (float) (System.currentTimeMillis() - start) / 1000;
                    idx++;
                    System.out.println(idx);
                }
            }
            prog.getModelo().setResultados(resultados);
            prog.getModelo().setPaisesReales(paisesReales);
            prog.getModelo().setPaisesPredichos(paisesPredichos);
            prog.getModelo().setTiempoUnitTest(tiempo);
            prog.notificar("Unit test terminados");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Ejecuta muestreo")) {
            if (!this.executat) {
                this.start();
                this.executat = true;
            } else {
                this.donali = true;
            }
        } else if (s.startsWith("Ejecuta unit test")) {
            unitTest();
        }
    }

}
