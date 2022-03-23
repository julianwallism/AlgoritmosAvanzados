package capitol2.model;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Tauler {
    public int dim = 8;
    public int[][] caselles;
    
    public Tauler(int dim){
        this.dim=dim;
        caselles = new int[this.dim][this.dim];
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }


    public void setCasella(int i, int j, int x) {
        caselles[i][j] = x;
    }

    public void clearCasella(int i, int j) {
        caselles[i][j] = 0;
    }

    public int getCasella(int i, int j) {
        return caselles[i][j];
    }
}
