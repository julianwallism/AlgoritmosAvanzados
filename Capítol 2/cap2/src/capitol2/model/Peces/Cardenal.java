package capitol2.model.Peces;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */

// Cavall + alfil
public class Cardenal extends Peça {
    private int MOV_X[] = {1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, -1, -2, -3, -4, -5, -6, -7, 2, 1, -1, -2, -2, -1, 1, 2};
    private int MOV_Y[] = {1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 1, 2, 2, 1, -1, -2, -2, -1};

    public Cardenal() {
        super.setImatge("cardenal.png");
        super.setNom("Cardenal");
        super.setMovimentsX(MOV_X);
        super.setMovimentsY(MOV_Y);
    }
}
