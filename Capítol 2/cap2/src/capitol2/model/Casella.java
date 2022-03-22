package capitol2.model;

import capitol2.model.Peces.Peça;
import java.awt.Color;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Casella {
    private Peça peça;
    private Color color;
    private boolean visitada = false;
    
    public Casella(Color color){
        this.color = color;
    }

    public Peça getPeça() {
        return peça;
    }

    public void setPeça(Peça peça) {
        this.peça = peça;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }
}
