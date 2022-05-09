package capitulo5.modelo;

import capitulo5.PorEventos;
import capitulo5.main;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Modelo implements PorEventos {
    private main prog;
    private String texto = "";
    private Idioma idioma = Idioma.ES;
    private int nPalabrasTotales = 0, nPalabrasErroneas = 0;
    // esto seguramente se tenga que implementar de otra forma pero para tener una idea inicial
    private String[] palabrasErroneas, palabrasTexto;

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

    public int getnPalabrasTotales() {
        return nPalabrasTotales;
    }

    public void setnPalabrasTotales(int nPalabrasTotales) {
        this.nPalabrasTotales = nPalabrasTotales;
    }

    public int getnPalabrasErroneas() {
        return nPalabrasErroneas;
    }

    public void setnPalabrasErroneas(int nPalabrasErroneas) {
        this.nPalabrasErroneas = nPalabrasErroneas;
    }

    public String[] getPalabrasErroneas() {
        return palabrasErroneas;
    }

    public void setPalabrasErroneas(String[] palabrasErroneas) {
        this.palabrasErroneas = palabrasErroneas;
    }

    public String[] getPalabrasTexto() {
        return palabrasTexto;
    }

    public void setPalabrasTexto(String[] palabrasTexto) {
        this.palabrasTexto = palabrasTexto;
    }

    // Método notificar de la intefaz por eventos
    @Override
    public void notificar(String s) {
        
    }
}