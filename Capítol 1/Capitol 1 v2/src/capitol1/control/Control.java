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
        this.prog = p;
    }

    @Override
    public void run() {
        while (true) {
            while (this.seguir) {
                System.out.println("Programa executant-se amb l'opció " + this.prog.getModel().getOpcioTriada());
                switch (this.prog.getModel().getOpcioTriada()) {
                    case "n":
                        this.n();
                        break;
                    case "n^2":
                        this.n2();
                        break;
                    case "n*log(n)":
                        this.nlogn();
                        break;
                    case "log(n)":
                        this.logn();
                        break;
                    case "sqrt(n)":
                        this.sqrtn();
                        break;
                }
                this.seguir = false;
            }
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
            this.seguir = true;
            try {
                this.start();
            } catch (Exception e) {}
        } else if (s.startsWith("Aturar")) {
            this.seguir = false;
        }
    }

    private void sqrtn() {
        try {
            for (int i : prog.getModel().ns) {
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 0; j < Math.sqrt(i); j++) {
                    Thread.sleep(1);
                }
                // notificar que n ha acabat
                long tempsFinal = System.currentTimeMillis() - tempsInici;
                prog.notificar("Event iter " + i + " " + tempsFinal);
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    private void logn() {
        try {
            for (int i : prog.getModel().ns) {
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 0; j < Math.log(i); j++) {
                    Thread.sleep(1);
                }
                // notificar que n ha acabat
                long tempsFinal = System.currentTimeMillis() - tempsInici;
                prog.notificar("Event iter " + i + " " + tempsFinal);
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    private void n() {
        try {
            for (int i : prog.getModel().ns) {
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 0; j < i; j++) {
                    Thread.sleep(1);
                }
                // notificar que n ha acabat
                long tempsFinal = System.currentTimeMillis() - tempsInici;
                prog.notificar("Event iter " + i + " " + tempsFinal);
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    private void nlogn() {
        try {
            for (int i : prog.getModel().ns) {
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 0; j < i; j++) {
                    for (int k = 0; k < Math.log(i); k++) {
                        Thread.sleep(1);
                    }
                }
                // notificar que n ha acabat
                long tempsFinal = System.currentTimeMillis() - tempsInici;
                prog.notificar("Event iter " + i + " " + tempsFinal);
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    private void n2() {
        try {
            for (int i : prog.getModel().ns) {
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 0; j < i; j++) {
                    for (int k = 0; k < i; k++) {
                        Thread.sleep(1);
                    }
                }
                // notificar que n ha acabat
                long tempsFinal = System.currentTimeMillis() - tempsInici;
                prog.notificar("Event iter " + i + " " + tempsFinal);
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }
}
