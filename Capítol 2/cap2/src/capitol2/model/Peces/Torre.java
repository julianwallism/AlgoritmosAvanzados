package capitol2.model.Peces;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Torre extends Peça {
<<<<<<< Updated upstream
    int movimentsX[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7};
    int movimentsY[] = {1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
=======

    static int movimentsX[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7};
    static int movimentsY[] = {1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
>>>>>>> Stashed changes

    public Torre() {
        this.imatge = "torre.png";
        this.nom = "Torre";
    }
}
