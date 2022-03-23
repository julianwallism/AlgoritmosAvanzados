package capitol2.model.Peces;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Somera extends Peça {

    static int movimentsX[] = {-1, -2, 2, 1, -1, -2, -2, -1, 1, 2};
    static int movimentsY[] = {0, 0, 1, 2, 2, 1, -1, -2, -2, -1};

    public Somera() {
        this.imatge = "somera.png";
        this.nom = "Somera";
    }
    
     public int[] getMovimentsX() {
        return movimentsX;
    }

    public int[] getMovimentsY() {
        return movimentsY;
    }
}
