package capitol1.control;

import capitol1.main;
import capitol1.MeuError;
import capitol1.PerEsdeveniments;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Control extends Thread implements PerEsdeveniments {
    private final main prog;
    private boolean seguir;

    public Control(main p) {
        prog = p;
        seguir = false;
    }

    @Override
    public void run() {
        while (seguir) {
            
        }
    }

    private void espera(long m, int n) {
        try {
            Thread.sleep(m, n);
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Executar")) {
            if (!seguir) this.start();
            seguir = true;
            System.out.println("Programa executant-se amb l'opció " + this.prog.getModel().getOpcioTriada());
        }
    }
}
