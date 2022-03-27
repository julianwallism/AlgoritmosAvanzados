package capitol3.model;

import capitol3.PerEsdeveniments;
import capitol3.main;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    private main prog;
    private double time;

    public Model(main p) {
        prog = p;
        time = 0.0;
    }

    @Override
    public void notificar(String s) {
        
    }
    
     public double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
