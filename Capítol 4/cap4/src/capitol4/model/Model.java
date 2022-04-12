package capitol4.model;

import capitol4.PerEsdeveniments;
import capitol4.main;

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
    
    public double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
    
    @Override
    public void notificar(String s) {
        
    }
}
