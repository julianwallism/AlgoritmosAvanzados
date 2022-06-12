package capitulo7.modelo;

import capitulo7.main;
import java.awt.image.BufferedImage;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class Modelo {

    private final main prog;
    private String pais = "";
    private BufferedImage imagen;

    public Modelo(main p) {
        prog = p;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }
}
