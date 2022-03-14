package capitol2.model;

import capitol2.PerEsdeveniments;
import capitol2.main;
import capitol2.model.Peces.Peça;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    public String[] tamanysTauler = {"4", "5", "6", "7", "8", "9", "10", "11", "12"};
    public Peça[] peces = {};
    private int progres = 0;
    private int tamanyTriat = 6;
    private main prog;
    
    public Model(main p) {
        prog = p;
    }

    public int getProgres() {
        return progres;
    }

    public void setProgres(int progres) {
        this.progres = progres;
    }
    
    @Override
    public void notificar(String s) {
        
    }

    public int getTamanyTriat() {
        return tamanyTriat;
    }

    public void setTamanyTriat(int tamanyTriat) {
        this.tamanyTriat = tamanyTriat;
    }
}
