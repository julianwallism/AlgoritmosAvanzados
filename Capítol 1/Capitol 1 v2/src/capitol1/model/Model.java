package capitol1.model;

import capitol1.PerEsdeveniments;
import capitol1.main;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    public final String[] opcions = {"sqrt(n)", "log(n)", "n", "n*log(n)", "n^2"};
    public final int[] ns = {10000, 200, 300, 400};
    private int progres = 0;
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

    public int getProgres() {
        return progres;
    }

    public void setProgres(int progres) {
        this.progres = progres;
    }
    
    @Override
    public void notificar(String s) {
        if (s.startsWith("Progrés")) {
            s = s.replaceAll("Progrés ", "");
            int valor = Integer.parseInt(s);
            if (valor != this.progres) {
                this.progres = valor;
                prog.notificar("Barra " + s);
            }
        }
    }
}
