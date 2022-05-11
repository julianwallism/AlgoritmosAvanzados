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

    public Control(main p) {
        this.prog = p;
    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Texto guardado")) {
            this.decideIdioma();
        } else if (s.startsWith("Comprobar texto")) {
            this.comprobarTexto();
        } else if (s.startsWith("Corregir palabras")) {
            this.corregirPalabras();
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
        String[] palabrasTexto = this.prog.getModelo().getTexto().split(" ");
        palabrasTexto = refinaPalabras(palabrasTexto);
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

        System.out.println(frec[0]);
        System.out.println(frec[1]);
        System.out.println(frec[2]);
        
        //En caso de empate ingles>catalan>español
        if (frec[0] == 0 && frec[1] == 0 && frec[2] == 0) {
            this.prog.getModelo().setIdioma(Idioma.UNKNOWN);
            this.prog.getModelo().setDiccionario(null);
            System.out.println("Ninguno");
        } else if (frec[0] > frec[1] && frec[0] > frec[2]) {
            this.prog.getModelo().setIdioma(Idioma.ES);
            this.prog.getModelo().setDiccionario(esp);
            System.out.println("Español");
        } else if (frec[1] >= frec[0] && frec[1] > frec[2]) {
            this.prog.getModelo().setIdioma(Idioma.CAT);
            this.prog.getModelo().setDiccionario(cat);
            System.out.println("Catalan");
        } else if (frec[2] >= frec[0] && frec[2] >= frec[1]) {
            this.prog.getModelo().setIdioma(Idioma.ENG);
            this.prog.getModelo().setDiccionario(eng);
            System.out.println("Ingles");
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
        String[] palabrasTexto = this.prog.getModelo().getTexto().split(" ");
        palabrasTexto = refinaPalabras(palabrasTexto);
        String[] palabrasErroneasAux = new String[palabrasTexto.length];
        int ind = 0;
        for (String palabra : palabrasTexto) {
            if (!Arrays.asList(palabrasDiccionario).contains(palabra)) {
                palabrasErroneasAux[ind] = palabra;
                ind++;
                System.out.println(palabra);
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
    private void corregirPalabras() {
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
                int distancia = distDeLevenshtein(palabra.toCharArray(), palabraDiccionario.toCharArray());
                if (distancia == min) {
                    sugerenciasPalabra.add(palabraDiccionario);
                } else if (distancia < min) {
                    min = distancia;
                    sugerenciasPalabra.clear();
                    sugerenciasPalabra.add(palabraDiccionario);
                }
            }
            //Print the suggestions for each word
            System.out.println("Sugerencias para " + palabra + ": ");
            System.out.println(sugerenciasPalabra);
            sugerencias.put(palabra, sugerenciasPalabra);
        }
        this.prog.getModelo().setSugerencias(sugerencias);
    }

    private int distDeLevenshtein(char[] word1, char[] word2) {
        int coste = 0;
        if (word1.length == 0 && word2.length == 0) {
            return 0;
        } else if (word2.length == 0) {
            coste = distDeLevenshtein(minusLastLetter(word1), word2) + 1;
        } else if (word1.length == 0) {
            coste = distDeLevenshtein(word1, minusLastLetter(word2)) + 1;
        } else {
            int case1 = distDeLevenshtein(minusLastLetter(word1), word2) + 1;
            int case2 = distDeLevenshtein(word1, minusLastLetter(word2)) + 1;
            int case3;
            if (word1[word1.length - 1] == word2[word2.length - 1]) {
                case3 = distDeLevenshtein(minusLastLetter(word1), minusLastLetter(word2));
            } else {
                case3 = distDeLevenshtein(minusLastLetter(word1), minusLastLetter(word2)) + 1;
            }
            coste = Math.min(case1, Math.min(case2, case3));
        }
        return coste;
    }

    private char[] minusLastLetter(char[] word) {
        return Arrays.copyOfRange(word, 0, word.length - 1);
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

    private String[] refinaPalabras(String[] palabras) {
        // Given a string[] of words delete the commas and dots
        String[] palabrasRefinadas = palabras;
        int ind = 0;
        for (String palabra : palabras) {
            // Create a regex for any type of word followed by a comma, a dot
            String regex = "([!-~]+)[,.;!?)}]";
            // Check if the word passes the first regex
            if (palabra.matches(regex)) {
                palabra = palabra.replaceFirst("[,.;!?)}]", "");
                palabrasRefinadas[ind] = palabra;
            }
            ind++;
        }
        return palabrasRefinadas;
    }

}
