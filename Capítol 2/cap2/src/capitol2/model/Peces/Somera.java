package capitol2.model.Peces;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Somera extends Peça {
    private int MOV_X[] = {-1, -2, 2, 1, -1, -2, -2, -1, 1, 2};
    private int MOV_Y[] = {0, 0, 1, 2, 2, 1, -1, -2, -2, -1};

    public Somera() {
        super.setImatge("somera.png");
        super.setNom("Somera");
        super.setMovimentsX(MOV_X);
        super.setMovimentsY(MOV_Y);
    }
}
