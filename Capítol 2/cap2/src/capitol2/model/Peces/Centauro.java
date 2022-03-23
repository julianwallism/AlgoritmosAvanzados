/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitol2.model.Peces;

/**
 *
 * @author walli
 */
//Cavall + rei
public class Centauro extends Peça {

    static int movimentsX[] = {0, 0, 1, 1, 1, -1, -1, -1, 2, 1, -1, -2, -2, -1, 1, 2};
    static int movimentsY[] = {1, -1, 0, 1, -1, 0, 1, -1, 1, 2, 2, 1, -1, -2, -2, -1};

    public Centauro() {
        this.imatge = "centauro.png";
        this.nom = "Centauro";
    }

    public int[] getMovimentsX() {
        return movimentsX;
    }

    public int[] getMovimentsY() {
        return movimentsY;
    }
}
