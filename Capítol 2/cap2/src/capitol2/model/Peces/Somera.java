package capitol2.model.Peces;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
// Cavall + peó
public class Somera extends Peça {

    static int movimentsX[] = {1, 1, -1, -1, 2, 2, -2, -2, 0, 0};
    static int movimentsY[] = {2, -2, 2, -2, 1, -1, 1, -1, 1, 2};

    public Somera() {
        this.imatge = "somera.png";
        this.nom = "Somera";
    }
}
