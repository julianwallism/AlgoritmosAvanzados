package capitol2.model.Peces;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Reina extends Peça {
    static int movimentsX[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3,
        4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6,
        6, 7, 7, -1, -1, -2, -2, -3, -3, -4, -4, -5, -5, -6, -6, -7, -7};
    static int movimentsY[] = {1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 2, -2, 3, -3, 4, -4, 5,
        -5, 6, -6, 7, -7, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7};

    public Reina() {
        this.imatge = "reina.png";
        this.nom = "Reina";
    }

    public int[] getMovimentsX() {
        return movimentsX;
    }

    public int[] getMovimentsY() {
        return movimentsY;
    }
    
}
