package capitol2.model;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Casella {
    private boolean visitada;
    private int ordre;
    
    public Casella() {
        this.visitada = false;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }
}
