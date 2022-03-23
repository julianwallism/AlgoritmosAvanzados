/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitol2.model.Peces;

/**
 *
 * @author walli
 */
// Cavall + alfil
public class Cardenal extends Peça {

    static int movimentsX[] = {1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, -1, -2, -3, -4, -5, -6, -7, 2, 1, -1, -2, -2, -1, 1, 2};
    static int movimentsY[] = {1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 1, 2, 2, 1, -1, -2, -2, -1};

    public Cardenal() {
        this.imatge = "cardenal.png";
        this.nom = "Cardenal";
    }

    public int[] getMovimentsX() {
        return movimentsX;
    }

    public int[] getMovimentsY() {
        return movimentsY;
    }

}
