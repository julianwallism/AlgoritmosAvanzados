package capitulo5.modelo;

import capitulo5.PorEventos;
import capitulo5.main;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Modelo implements PorEventos {
    private main prog;
    private String texto = "";
    private Idioma idioma = Idioma.DESCONOCIDO;
    private File diccionario = null;
    private Palabra[] palabrasTexto = {};
    private Map<Palabra, ArrayList<String>> sugerencias;

    public Modelo(main p) {
        prog = p;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public File getDiccionario() {
        return diccionario;
    }

    public void setDiccionario(File diccionario) {
        this.diccionario = diccionario;
    }

    public Palabra[] getPalabrasTexto() {
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

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {

    }
}