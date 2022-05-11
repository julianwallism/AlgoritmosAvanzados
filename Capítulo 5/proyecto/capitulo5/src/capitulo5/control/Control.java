package capitulo5.control;

import static capitulo5.Error.informaError;
import java.util.Arrays;
import capitulo5.PorEventos;
import capitulo5.main;
import capitulo5.modelo.Idioma;
import capitulo5.modelo.Palabra;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Control extends Thread implements PorEventos {
    private final main prog;

    public Control(main p) {
        this.prog = p;
    }
    
    public String[] readLines(File file) {
        if (file == null) return null;
        
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file.getName());
            List<String> lines;
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                lines = new ArrayList<>();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }
            }
            fileReader.close();
            
            return lines.toArray(String[]::new);
        } catch (IOException ex) {
            informaError(ex);
        }
        
        return null;
    }
    
    public Palabra[] getPalabrasErroneas(Palabra[] palabras) {
        ArrayList<Palabra> p = new ArrayList<>();
        for (Palabra pal : palabras) {
            if (pal.isErronea()) p.add(pal);
        }
        Palabra[] res = new Palabra[p.size()];
        p.toArray(res);
        return res;
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
    
    private void textoAPalabras() {
        String[] textoPalabras = this.prog.getModelo().getTexto().replaceAll("[,.:;=?()!¡'/_]", "").split(" ");
        Palabra[] palabras = new Palabra[textoPalabras.length];
        for (int i = 0; i < textoPalabras.length; i++) {
            palabras[i] = new Palabra(textoPalabras[i]);
        }
        this.prog.getModelo().setPalabrasTexto(palabras);
    }

    /**
     * Este método decide el idioma del texto basandose en la cantidad de
     * palabras que pertenecen a cada idioma, y en caso de que no se encuentre
     * ninguno se asigna el idioma DESCONOCIDO.
     *
     * @return void
     */
    private void decideIdioma() {
        File esp = new File("esp.dic");
        File cat = new File("cat.dic");
        File eng = new File("eng.dic");
        this.textoAPalabras();
        Palabra[] palabrasTexto = this.prog.getModelo().getPalabrasTexto();
        File[] diccionarios = {esp, cat, eng};
        int[] frec = new int[3];

        int ind = 0;
        for (File diccionario : diccionarios) {
            String[] palabrasDiccionario = null;
            palabrasDiccionario = readLines(diccionario);
            for (Palabra palabra : palabrasTexto) {
                if (Arrays.asList(palabrasDiccionario).contains(palabra.getTexto())) {
                    frec[ind]++;
                }
            }
            ind++;
        }
        
        // En caso de empate inglés > catalán > español
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

        this.prog.notificar("Idioma detectado");
    }

    /**
     * Dado un texto y un diccionario, este método comprueba si el texto está
     * escrito correctamente.
     *
     */
    private void comprobarTexto() {
        File dic = this.prog.getModelo().getDiccionario();
        String[] palabrasDiccionario = null;
        palabrasDiccionario = readLines(dic);
        Palabra[] palabrasTexto = this.prog.getModelo().getPalabrasTexto();
        for (Palabra palabra : palabrasTexto) {
            palabra.setErronea(!Arrays.asList(palabrasDiccionario).contains(palabra.getTexto()));
        }
        
        this.prog.notificar("Texto comprobado");
    }

    /**
     * Dado un array de palabras incorrectas y un diccionario, este método corrige
     * las palabras usando la distancia de Levenshtein.
     */
    private void corregirPalabras() {
        File dic = this.prog.getModelo().getDiccionario();
        // leer todas las líneas del fichero al array de strings
        String[] palabrasDiccionario = null;
        palabrasDiccionario = readLines(dic);

        Palabra[] palabrasErroneas = getPalabrasErroneas(this.prog.getModelo().getPalabrasTexto());
        // Create a map with string as key and arraylist as value
        HashMap<Palabra, ArrayList<String>> sugerencias = new HashMap<>();
        for (Palabra palabra : palabrasErroneas) {
            ArrayList<String> sugerenciasPalabra = new ArrayList<>();
            int min = Integer.MAX_VALUE;
            for (String palabraDiccionario : palabrasDiccionario) {
                int distancia = distanciaLevenshtein(palabra.getTexto().toCharArray(), palabraDiccionario.toCharArray());
                if (distancia == min) {
                    sugerenciasPalabra.add(palabraDiccionario);
                } else if (distancia < min) {
                    min = distancia;
                    sugerenciasPalabra.clear();
                    sugerenciasPalabra.add(palabraDiccionario);
                }
            }
            //Print the suggestions for each word
            System.out.println("Sugerencias para " + palabra.getTexto() + ": ");
            System.out.println(sugerenciasPalabra);
            sugerencias.put(palabra, sugerenciasPalabra);
        }
        this.prog.getModelo().setSugerencias(sugerencias);
    }

    private int distanciaLevenshtein(char[] word1, char[] word2) {
        int coste = 0;
        if (word1.length == 0 && word2.length == 0) {
            return 0;
        } else if (word2.length == 0) {
            coste = distanciaLevenshtein(menosUltimaLetra(word1), word2) + 1;
        } else if (word1.length == 0) {
            coste = distanciaLevenshtein(word1, menosUltimaLetra(word2)) + 1;
        } else {
            int case1 = distanciaLevenshtein(menosUltimaLetra(word1), word2) + 1;
            int case2 = distanciaLevenshtein(word1, menosUltimaLetra(word2)) + 1;
            int case3;
            if (word1[word1.length - 1] == word2[word2.length - 1]) {
                case3 = distanciaLevenshtein(menosUltimaLetra(word1), menosUltimaLetra(word2));
            } else {
                case3 = distanciaLevenshtein(menosUltimaLetra(word1), menosUltimaLetra(word2)) + 1;
            }
            coste = Math.min(case1, Math.min(case2, case3));
        }
        return coste;
    }

    private char[] menosUltimaLetra(char[] word) {
        return Arrays.copyOfRange(word, 0, word.length - 1);
    }
}
