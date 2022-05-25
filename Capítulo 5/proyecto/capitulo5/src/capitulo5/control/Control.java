package capitulo5.control;

import java.util.Arrays;

import capitulo5.PorEventos;
import capitulo5.main;
import capitulo5.modelo.Idioma;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Control extends Thread implements PorEventos {

    private final main prog;
    private boolean executat;

    public Control(main p) {
        this.prog = p;
    }

    @Override
    public void run() {
        this.executat = true;
        this.decideIdioma();
        this.comprobarTexto();
        this.buscarSugerencias();
    }

    public String[] readLines(File file) throws IOException {
        FileReader fileReader = new FileReader(file.getName());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Comprobar texto")) {

            if (!this.executat) {
                this.start();
            } else {
                System.out.println("Programa reanudat");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
            this.prog.notificar("Texto comprobado");
        } else if (s.startsWith("Actualizar")) {
            this.comprobarTexto();
            this.prog.notificar("Actualizado");
        }
    }

    /**
     * Este método decide el idioma del texto basandose en la cantidad de
     * palabras que pertenecen a cada idioma, y en caso de que no se encuentre
     * ninguno se asigna el idioma UNKNOWN.
     *
     * @return void
     */
    private void decideIdioma() {
        File esp = new File("esp.dic");
        File cat = new File("cat.dic");
        File eng = new File("eng.dic");
        String[] palabrasTexto = this.prog.getModelo().getPalabrasTexto();
        File[] diccionarios = {esp, cat, eng};
        int[] frec = new int[3];

        int ind = 0;
        for (File diccionario : diccionarios) {
            String[] palabrasDiccionario = null;
            try {
                palabrasDiccionario = readLines(diccionario);
            } catch (IOException ex) {
            }
            for (String palabra : palabrasTexto) {
                if (Arrays.asList(palabrasDiccionario).contains(palabra)) {
                    frec[ind]++;
                }
            }
            ind++;
        }

        // En caso de empate ingles>catalan>español
        if (frec[0] == 0 && frec[1] == 0 && frec[2] == 0) {
            this.prog.getModelo().setIdioma(Idioma.DESCONOCIDO);
            this.prog.getModelo().setDiccionario(null);
        } else if (frec[0] > frec[1] && frec[0] > frec[2]) {
            this.prog.getModelo().setIdioma(Idioma.ESPAÑOL);
            this.prog.getModelo().setDiccionario(esp);
        } else if (frec[1] >= frec[0] && frec[1] > frec[2]) {
            this.prog.getModelo().setIdioma(Idioma.CATALÁN);
            this.prog.getModelo().setDiccionario(cat);
        } else if (frec[2] >= frec[0] && frec[2] >= frec[1]) {
            this.prog.getModelo().setIdioma(Idioma.INGLÉS);
            this.prog.getModelo().setDiccionario(eng);
        }
    }

    /**
     * Given a text and a dictionary, this method checks if the text is
     * correctly written.
     *
     */
    private void comprobarTexto() {
        File dic = this.prog.getModelo().getDiccionario();
        String[] palabrasDiccionario = null;
        try {
            palabrasDiccionario = readLines(dic);
        } catch (IOException ex) {
        }
        String[] palabrasTexto = this.prog.getModelo().getPalabrasTexto();
        String[] palabrasErroneasAux = new String[palabrasTexto.length];
        int ind = 0;
        for (String palabra : palabrasTexto) {
            if (!Arrays.asList(palabrasDiccionario).contains(palabra)) {
                palabrasErroneasAux[ind] = palabra;
                ind++;
            }
        }
        String[] palabrasErroneas = new String[ind];
        for (int i = 0; i < ind; i++) {
            palabrasErroneas[i] = palabrasErroneasAux[i];
        }

        this.prog.getModelo().setPalabrasErroneas(palabrasErroneas);
    }

    /**
     * Given an array of incorrect words and a dictonary, this method corrects
     * the words using levenshtein distance.
     */
    private void buscarSugerencias() {
        File dic = this.prog.getModelo().getDiccionario();
        // read all lines from file into string array
        String[] palabrasDiccionario = null;
        try {
            palabrasDiccionario = readLines(dic);
        } catch (IOException ex) {
        }
        String[] palabrasErroneas = this.prog.getModelo().getPalabrasErroneas();
        // Create a map with string as key and arraylist as value
        HashMap<String, ArrayList<String>> sugerencias = new HashMap<String, ArrayList<String>>();
        for (String palabra : palabrasErroneas) {
            ArrayList<String> sugerenciasPalabra = new ArrayList<String>();
            int min = Integer.MAX_VALUE;
            for (String palabraDiccionario : palabrasDiccionario) {
                int distancia = distDeLevenshteinIterative(palabra.toCharArray(), palabraDiccionario.toCharArray());
                if (distancia == min) {
                    sugerenciasPalabra.add(palabraDiccionario);
                } else if (distancia < min) {
                    min = distancia;
                    sugerenciasPalabra.clear();
                    sugerenciasPalabra.add(palabraDiccionario);
                }
            }
            sugerencias.put(palabra, sugerenciasPalabra);
        }
        this.prog.getModelo().setSugerencias(sugerencias);
    }

    private int distDeLevenshteinIterative(char[] word1, char[] word2) {
        int[][] dist = new int[word1.length + 1][word2.length + 1];
        for (int i = 0; i <= word1.length; i++) {
            dist[i][0] = i;
        }
        for (int j = 0; j <= word2.length; j++) {
            dist[0][j] = j;
        }
        for (int i = 1; i <= word1.length; i++) {
            for (int j = 1; j <= word2.length; j++) {
                int cost = (word1[i - 1] == word2[j - 1]) ? 0 : 1;
                dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1), dist[i - 1][j - 1] + cost);
            }
        }
        return dist[word1.length][word2.length];
    }
}
