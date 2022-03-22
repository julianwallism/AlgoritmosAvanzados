package capitol2.model.Peces;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Cavall extends Peça {
    int movimentsX[] = {1, 1, -1, -1, 2, 2, -2, -2};
    int movimentsY[] = {2, -2, 2, -2, 1, -1, 1, -1};

    public Cavall() {
        this.imatge = "cavall.png";
        this.nom = "Cavall";
    }
}
