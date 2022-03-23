package capitol2.model;

/**
 *
 * @author Dawid Roch & Julià Wallis
 */
public class Tauler {
    private int dim;
    private int[][] caselles;

    public Tauler(int dim) {
        this.dim = dim;
        caselles = new int[this.dim][this.dim];
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
        caselles = new int[this.dim][this.dim];
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
    
    public int[][] getCaselles() {
        return caselles;
    }

    public void clear() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                clearCasella(i, j);
            }
        }
    }

    // Métode emprat per graficar
    public int[] getOrderX() {
        int[] ordre = new int[dim * dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                ordre[caselles[i][j] - 1] = i;
            }
        }
        return ordre;
    }

    // Métode emprat per graficar
    public int[] getOrderY() {
        int[] ordre = new int[dim * dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                ordre[caselles[i][j] - 1] = j;
            }
        }
        return ordre;
    }
}
