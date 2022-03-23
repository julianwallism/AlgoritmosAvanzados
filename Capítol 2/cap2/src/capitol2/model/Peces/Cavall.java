package capitol2.model.Peces;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Cavall extends Peça {
    private int MOV_X[] = {2, 1, -1, -2, -2, -1, 1, 2}; 
    private int MOV_Y[] = {1, 2, 2, 1, -1, -2, -2, -1};

    public Cavall() {
        super.setImatge("cavall.png");
        super.setNom("Cavall");
        super.setMovimentsX(MOV_X);
        super.setMovimentsY(MOV_Y);
    }  
}
