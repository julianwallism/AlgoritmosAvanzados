package capitol2.model.Peces;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public abstract class Peça {
    public String nom, imatge;
    public static int movimentsX[];
    public static int movimentsY[];

    public int[] getMovimentsX() {
        return movimentsX;
    }

    public int[] getMovimentsY() {
        return movimentsY;
    }
}
