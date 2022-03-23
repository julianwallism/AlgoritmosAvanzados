package capitol2.model.Peces;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Torre extends Peça {
    private static final int MOV_X[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7};
    private static final int MOV_Y[] = {1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public Torre() {
        super.setImatge("torre.png");
        super.setNom("Torre");
        super.setMovimentsX(MOV_X);
        super.setMovimentsY(MOV_Y);
    }
}
