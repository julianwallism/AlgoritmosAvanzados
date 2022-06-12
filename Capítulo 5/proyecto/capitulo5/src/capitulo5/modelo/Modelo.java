package capitulo5.modelo;

import capitulo5.main;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
<<<<<<< HEAD
public class Modelo implements PorEventos {
    private main prog;
    private String texto = "";
    private Idioma idioma = Idioma.DESCONOCIDO;
    private File diccionario = null;
    private Palabra[] palabrasTexto = {};
    private Map<Palabra, ArrayList<String>> sugerencias;
=======
public class Modelo {

    private final main prog;
    private String texto;
    private Idioma idioma = Idioma.DESCONOCIDO;
    private String[] palabrasErroneas = {}, palabrasTexto = {}, palabrasDiccionario = {};
    private Map<String, ArrayList<String>> sugerencias;
	
    public String[] getPalabrasDiccionario() {
        return palabrasDiccionario;
    }

    public void setPalabrasDiccionario(String[] palabrasDiccionario) {
        this.palabrasDiccionario = palabrasDiccionario;
    }
>>>>>>> sinPalabra

    public Modelo(main p) {
        prog = p;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
        parseaPalabras();
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

<<<<<<< HEAD
    public File getDiccionario() {
        return diccionario;
    }

    public void setDiccionario(File diccionario) {
        this.diccionario = diccionario;
    }

    public Palabra[] getPalabrasTexto() {
=======
    public String[] getPalabrasErroneas() {
        return palabrasErroneas;
    }

    public void setPalabrasErroneas(String[] palabrasErroneas) {
        this.palabrasErroneas = palabrasErroneas;
    }

    public String[] getPalabrasTexto() {
>>>>>>> sinPalabra
        return palabrasTexto;
    }

    public void setPalabrasTexto(Palabra[] palabrasTexto) {
        this.palabrasTexto = palabrasTexto;
    }

    public Map<Palabra, ArrayList<String>> getSugerencias() {
        return sugerencias;
    }

    public void setSugerencias(Map<Palabra, ArrayList<String>> sugerencias) {
        this.sugerencias = sugerencias;
    }

    public void parseaPalabras() {
        this.palabrasTexto = texto.toLowerCase().split(
                "[\\s,\\.\\?\\!\\:\\;\\-\\_\\=\\+\\(\\)\\{\\}\\[\\]\\|\\\\\\/\\*\\&\\^\\%\\$\\#\\@\\~\\'\"\\`\\´\\¨\\¬\\¦\\¡\\¿]");
        // create new array with only the words that are not empty
        ArrayList<String> palabras = new ArrayList<>();
        for (String palabra : palabrasTexto) {
            if (!palabra.isEmpty()) {
                palabras.add(palabra);
            }
        }
        palabrasTexto = palabras.toArray(new String[palabras.size()]);
    }
}
