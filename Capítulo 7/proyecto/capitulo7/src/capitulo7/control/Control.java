package capitulo7.control;

import static capitulo7.Error.informaError;

import java.util.HashMap;
import java.awt.image.BufferedImage;

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
                    muestreo();
                    this.prog.notificar("Ejecución terminada");
                    donali = false;
                }
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
            informaError(ex);
        }
    }

    private void muestreo() {
        int porcentajeMuestreo = prog.getModelo().getPorcentajeMuestreo();
        BufferedImage imagen = prog.getModelo().getImagen();
        if (imagen != null) {
            int N_muestreo = (imagen.getWidth() * imagen.getHeight() * porcentajeMuestreo) / 100;
            double[] colores = new double[7];
            Paleta p = new Paleta();

            for (int i = 0; i < N_muestreo; i++) {
                int x = (int) (Math.random() * imagen.getWidth());
                int y = (int) (Math.random() * imagen.getHeight());
                Color c = new Color(imagen.getRGB(x, y));
                int idx = p.analizarColor(c);
                colores[idx]++;
            }

            for (int i = 0; i < 7; i++) {
                colores[i] /= N_muestreo;
            }

            // Menor distancia entre los colores
            menorDistancia(colores);
        }
    }

// Find the minimum distance between the color given and the colors in the bd
    private void menorDistancia(double[] colores) {
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
        this.prog.getModelo().setPais(pais.replaceAll(".png", ""));
    }

    // Calculate the Euclidean distance between two colors
    private double distanciaEuclidiana(double[] colores1, double[] colores2) {
        double suma = 0;
        for (int i = 0; i < 7; i++) {
            suma += Math.pow(colores1[i] - colores2[i], 2);
        }
        return Math.sqrt(suma);
    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Ejecutar")) {
            if (!this.executat) {
                this.start();
                this.executat = true;
            } else {
                this.donali = true;
            }
        }
    }

}
