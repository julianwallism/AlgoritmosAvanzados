package capitol2.model.Peces;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public abstract class Peça {
    private String nom, imatge;
    private int movimentsX[];
    private int movimentsY[];

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public void setMovimentsX(int[] movimentsX) {
        this.movimentsX = movimentsX;
    }

    public void setMovimentsY(int[] movimentsY) {
        this.movimentsY = movimentsY;
    }

    public int[] getMovimentsX() {
        return movimentsX;
    }

    public int[] getMovimentsY() {
        return movimentsY;
    }
}
