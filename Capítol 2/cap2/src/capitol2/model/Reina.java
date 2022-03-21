/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitol2.model;

/**
 *
 * @author walli
 */
public class Reina extends Peça {
    int movimentsX[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3,
        4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6,
        6, 7, 7, -1, -1, -2, -2, -3, -3, -4, -4, -5, -5, -6, -6, -7, -7};
    int movimentsY[] = {1, 2, 3, 4, 5, 6, 7, -1, -2, -3, -4, -5, -6, -7, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 2, -2, 3, -3, 4, -4, 5,
        -5, 6, -6, 7, -7, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7};

    public Reina() {
        this.imatge = "/torre.jpg";
    }
}
