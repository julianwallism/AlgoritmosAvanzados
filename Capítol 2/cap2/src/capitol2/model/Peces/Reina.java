package capitol2.model.Peces;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Reina extends Peça {
    private int MOV_X[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3,
        4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6,
        6, 7, 7, -1, -1, -2, -2, -3, -3, -4, -4, -5, -5, -6, -6, -7, -7};
    private int MOV_Y[] = {1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 2, -2, 3, -3, 4, -4, 5,
        -5, 6, -6, 7, -7, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7};

    public Reina() {
        super.setImatge("reina.png");
        super.setNom("Reina");
        super.setMovimentsX(MOV_X);
        super.setMovimentsY(MOV_Y);
    }
}
