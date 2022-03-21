/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitol2.model;

import capitol2.model.Peces.Peça;
import java.awt.Color;

/**
 *
 * @author walli
 */
public class Casella {
    public Peça peça = null;
    public Color color;
    public boolean visitada = false;
    
    public Casella(Color color){
        this.color = color;
    }

    public Peça getPeça() {
        return peça;
    }

    public void setPeça(Peça peça) {
        this.peça = peça;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }
    
    
}
