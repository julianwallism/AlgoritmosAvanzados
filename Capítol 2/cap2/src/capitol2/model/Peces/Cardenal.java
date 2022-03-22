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
    static int movimentsX[] = {1, 1, -1, -1, 2, 2, -2, -2, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5,
        6, 6, 7, 7, -1, -1, -2, -2, -3, -3, -4, -4, -5, -5, -6, -6, -7, -7};
    static int movimentsY[] = {2, -2, 2, -2, 1, -1, 1, -1, 1, -1, 2, -2, 3, -3, 4, -4,
        5, -5, 6, -6, 7, -7, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7};

    public Cardenal() {
        this.imatge = "/cardenal.jpg";
    }
}
