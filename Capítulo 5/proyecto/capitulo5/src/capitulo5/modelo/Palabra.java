package capitulo5.modelo;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Palabra {
    private String texto;
    private boolean erronea;
    
    public Palabra(String texto, boolean isErronea) {
        this.texto = texto;
        this.erronea = isErronea;
    }
    
    public Palabra(String texto) {
        this.texto = texto;
        this.erronea = false;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isErronea() {
        return erronea;
    }

    public void setErronea(boolean isErronea) {
        this.erronea = isErronea;
    }
}
