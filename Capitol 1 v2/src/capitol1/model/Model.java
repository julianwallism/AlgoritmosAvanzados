package capitol1.model;

import capitol1.main;
import capitol1.PerEsdeveniments;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    public final String[] opcions = {"sqrt(n)", "log(n)", "n", "n*log(n)", "n^2"};
    private main prog;
    private int x, y, x2, y2;
    private String opcioTriada;

    public Model(main p) {
        prog = p;
        x = y = x2 = y2 = 0;
        opcioTriada = opcions[0];
    }

    public void setPosicioInicial(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setPosicioFinal(int x, int y) {
        this.x2 = x;
        this.y2 = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public String getOpcioTriada() {
        return opcioTriada;
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Opció")) {
            this.opcioTriada = s.replace("Opció ", "");
            System.out.println("Opció triada: " + this.opcioTriada);
        }
    }
}
