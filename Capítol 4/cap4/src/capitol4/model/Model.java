package capitol4.model;

import capitol4.PerEsdeveniments;
import capitol4.main;
import java.io.File;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    private main prog;
    private double time;
    private int retornSelector;
    private File fitxerTriat;

    public Model(main p) {
        prog = p;
        time = 0.0;
    }

    public int getRetornSelector() {
        return retornSelector;
    }

    public void setRetornSelector(int retornSelector) {
        this.retornSelector = retornSelector;
    }
    
    public double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
    
    @Override
    public void notificar(String s) {
        if (s.startsWith("Fitxer")) {
            System.out.println(s);
            this.fitxerTriat = new File(s.replaceAll("Fitxer: ", ""));
        }
    }
}
