package capitol2.model.Peces;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
//Cavall + rei
public class Centauro extends Peça {
    private int MOV_X[] = {0, 0, 1, 1, 1, -1, -1, -1, 2, 1, -1, -2, -2, -1, 1, 2};
    private int MOV_Y[] = {1, -1, 0, 1, -1, 0, 1, -1, 1, 2, 2, 1, -1, -2, -2, -1};

    public Centauro() {
        super.setImatge("centauro.png");
        super.setNom("Centauro");
        super.setMovimentsX(MOV_X);
        super.setMovimentsY(MOV_Y);
    }
}
