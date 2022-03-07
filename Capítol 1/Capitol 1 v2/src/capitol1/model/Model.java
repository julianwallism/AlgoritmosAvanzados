package capitol1.model;

import capitol1.main;
import capitol1.PerEsdeveniments;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    public final String[] opcions = {"sqrt(n)", "log(n)", "n", "n*log(n)", "n^2"};
    public final int[] ns = {100, 200, 300, 400};
    private main prog;
    private String opcioTriada;
    
    public Model(main p) {
        prog = p;
        opcioTriada = opcions[0];
    }

    public String getOpcioTriada() {
        return opcioTriada;
    }

    public void setOpcioTriada(String opcioTriada) {
        this.opcioTriada = opcioTriada;
    }

    @Override
    public void notificar(String s) {
        
    }
}
