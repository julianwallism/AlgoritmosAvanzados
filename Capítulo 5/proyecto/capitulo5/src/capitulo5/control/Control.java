package capitulo5.control;

import static capitulo5.Error.informaError;
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
    private boolean executat = false, donali = true;

    public Control(main p) {
        this.prog = p;
    }

    @Override
    public void run() {
        while (true) {
            if (donali) {
                this.idioma_y_errores();
                if (this.prog.getModelo().getIdioma().equals(Idioma.DESCONOCIDO)) {
                    this.prog.notificar("No idioma detectado");
                    this.donali = false;
                } else {
                    this.buscarSugerencias();
                    this.prog.notificar("Texto comprobado");
                    this.donali = false;
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                informaError(ex);
            }
        }
    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        if (s.startsWith("Comprobar texto")) {
            if (!this.executat) {
                this.start();
                this.executat = true;
            } else {
                this.donali = true;
            }
        } else if (s.startsWith("Actualizar")) {
            this.comprobarTexto();
            this.prog.notificar("Actualizado");
        }
    }

    /**
     * Método que devuelve un string array con todas las lineas del fichero 
     * pasado por parámetro
     * @param file
     * @return
     * @throws IOException 
     */
    private String[] readLines(File file) throws IOException {
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

    /**
     * Este método decide el idioma del texto basandose en la cantidad de
     * palabras que pertenecen a cada idioma, y en caso de que no se encuentre
     * ninguno se asigna el idioma DESCONOCIDO.
     *
     * @return void
     */
    private void idioma_y_errores() {
        File esp = new File("esp.dic");
        File cat = new File("cat.dic");
        File eng = new File("eng.dic");
         File[] diccionarios = {esp, cat, eng};

        String[] palabrasTexto = this.prog.getModelo().getPalabrasTexto();
        
        String[] palabrasErroneasEsp = new String[palabrasTexto.length];
        String[] palabrasErroneasCat = new String[palabrasTexto.length];
        String[] palabrasErroneasIng = new String[palabrasTexto.length];
        String[][] palabrasErroneasAux = {palabrasErroneasEsp, palabrasErroneasCat, palabrasErroneasIng};
        
        int[] frec = new int[3];
        int[] tamañoErroneas = new int[3];

        int ind1 = 0;
        for (File diccionario : diccionarios) {
            
            String[] palabrasDiccionario = null;
            
            try {
                palabrasDiccionario = readLines(diccionario);
            } catch (IOException ex) {
                informaError(ex);
            }
            
            int ind2 = 0;
            for (String palabra : palabrasTexto) {
                if (Arrays.asList(palabrasDiccionario).contains(palabra)) {
                    frec[ind1]++;
                } else {
                    palabrasErroneasAux[ind1][ind2] = palabra;
                    tamañoErroneas[ind1]++;
                    ind2++;
                }
            }
            ind1++;
        }

        try {
            String[] palabrasErroneas = null;
            if (frec[0] == 0 && frec[1] == 0 && frec[2] == 0) {
                this.prog.getModelo().setIdioma(Idioma.DESCONOCIDO);
            } else if (frec[0] > frec[1] && frec[0] > frec[2]) {
                this.prog.getModelo().setIdioma(Idioma.ESPAÑOL);
                this.prog.getModelo().setPalabrasDiccionario(readLines(esp));
                palabrasErroneas = new String[tamañoErroneas[0]];
                for (int i = 0; i < tamañoErroneas[0]; i++) {
                    palabrasErroneas[i] = palabrasErroneasAux[0][i];
                }
            } else if (frec[1] >= frec[0] && frec[1] > frec[2]) {
                this.prog.getModelo().setIdioma(Idioma.CATALÁN);
                this.prog.getModelo().setPalabrasDiccionario(readLines(cat));
                palabrasErroneas = new String[tamañoErroneas[1]];
                for (int i = 0; i < tamañoErroneas[1]; i++) {
                    palabrasErroneas[i] = palabrasErroneasAux[1][i];
                }
            } else if (frec[2] >= frec[0] && frec[2] >= frec[1]) {
                this.prog.getModelo().setIdioma(Idioma.INGLÉS);
                this.prog.getModelo().setPalabrasDiccionario(readLines(eng));
                palabrasErroneas = new String[tamañoErroneas[2]];
                for (int i = 0; i < tamañoErroneas[2]; i++) {
                    palabrasErroneas[i] = palabrasErroneasAux[2][i];
                }
            }
            this.prog.getModelo().setPalabrasErroneas(palabrasErroneas);

        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Dadas las palabras de un diccionario el método calcula todas las palabras
     * erroneas del texto
     *
     */
    private void comprobarTexto() {
        String[] palabrasDiccionario = this.prog.getModelo().getPalabrasDiccionario();
        if (palabrasDiccionario != null) {
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
    }

    /**
     * Dado un array de palabras incorrectas y un array con las palabras del 
     * diccionario este método calcula sugerencias mediante la distancia de 
     * edición de levenshtein
     */
    private void buscarSugerencias() {
        String[] palabrasDiccionario = this.prog.getModelo().getPalabrasDiccionario();
        if (palabrasDiccionario != null) {
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
                sugerencias.put(palabra, sugerenciasPalabra);
            }
            this.prog.getModelo().setSugerencias(sugerencias);
        }
    }

    /**
     * Método que dadas dos palabras calcula la distancia de edición de Levenshtein
     * de manera iterativa
     * 
     * @param word1
     * @param word2
     * @return 
     */
    private int distDeLevenshtein(char[] word1, char[] word2) {
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
