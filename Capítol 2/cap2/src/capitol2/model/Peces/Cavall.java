package capitol2.model.Peces;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Cavall extends Peça {

    static int movimentsX[] = {1, 1, -1, -1, 2, 2, -2, -2};
    static int movimentsY[] = {2, -2, 2, -2, 1, -1, 1, -1};

    public Cavall() {
        this.imatge = "cavall.png";
        this.nom = "Cavall";
    }
    
     public int[] getMovimentsX() {
        return movimentsX;
    }

    public int[] getMovimentsY() {
        return movimentsY;
    }
}
