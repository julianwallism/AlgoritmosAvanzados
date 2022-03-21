/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitol2.model.Peces;

/**
 *
 * @author walli
 */
public class Cavall extends Peça {

    int movimentsX[] = {1, 1, -1, -1, 2, 2, -2, -2};
    int movimentsY[] = {2, -2, 2, -2, 1, -1, 1, -1};

    public Cavall() {
        this.imatge = "/cavall.jpg";
    }
}
