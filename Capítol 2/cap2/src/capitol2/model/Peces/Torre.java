package capitol2.model.Peces;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Torre extends Peça {

    static int movimentsX[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7};
    static int movimentsY[] = {1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public Torre() {
        this.imatge = "torre.png";
        this.nom = "Torre";
    }
    
     public int[] getMovimentsX() {
        return movimentsX;
    }

    public int[] getMovimentsY() {
        return movimentsY;
    }
}
