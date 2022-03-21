package capitol2.model;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Tauler {
    public int dim = 8;
    public Casella[][] caselles;
    
    public Tauler(int dim){
        this.dim=dim;
        caselles = new Casella[this.dim][this.dim];
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public Casella[][] getCaselles() {
        return caselles;
    }

    public void setCaselles(Casella[][] caselles) {
        this.caselles = caselles;
    }
    
}
