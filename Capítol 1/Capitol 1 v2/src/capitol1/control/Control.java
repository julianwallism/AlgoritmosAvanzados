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
    private boolean seguir, executat;

    public Control(main p) {
        this.prog = p;
        this.executat = false;
    }

    @Override
    public void run() {
        this.executat = true;
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

    @Override
    public void notificar(String s) {
        if (s.startsWith("Executar")) {
            this.seguir = true;
            if (!this.executat) {
                this.start();
            }
        } else if (s.startsWith("Aturar")) {
            System.out.println("Programa aturat");
            this.seguir = false;
        }
    }

    private void sqrtn() {
        try {
            for (int i : prog.getModel().ns) {
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 0; j < Math.sqrt(i) && this.seguir; j++) {
                    int progres = (int) (100 * ((double) j / (double) Math.sqrt(i)));
                    prog.notificar("Progrés " + progres);
                    Thread.sleep(40);
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + (double) tempsFinal / 3000 + " " + 0);
                }
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
                for (int j = 0; (j <= Math.log(i)/Math.log(2)) && this.seguir; j++) {
                    int progres = (int) (100 * ((double) j / (double) Math.log(i)));
                    prog.notificar("Progrés " + progres);
                    Thread.sleep(40);
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + (double) tempsFinal / 3000 + " " + -16776961);
                }
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
                for (int j = 0; j < i && this.seguir; j++) {
                    int progres = (int) (100 * ((double) j / (double) i));
                    prog.notificar("Progrés " + progres);
                    Thread.sleep(40);
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + (double) tempsFinal / 3000 + " " + -16711936);
                }
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    private void nlogn() {
        try {
            for (int i : prog.getModel().ns) {
                double percentChange = 1.0 / i / Math.log(i);
                double percentage = 0;
                prog.notificar("Progrés 0");
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 1; j <= i; j++) {
                    for (int k = 1; (k <= Math.log(i)/Math.log(2)) && this.seguir; k++) {
                        percentage += percentChange;
                        int progres = (int) (100 * percentage);
                        prog.notificar("Progrés " + progres);
                        Thread.sleep(40);
                    }
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + (double) tempsFinal / 3000 + " " + -65536);
                }
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    private void n2() {
        try {
            for (int i : prog.getModel().ns) {
                double percentChange = 1.0 / i / i;
                double percentage = 0;
                prog.notificar("Progrés 0");
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 1; j <= i; j++) {
                    for (int k = 1; k <= i && this.seguir; k++) {
                        percentage += percentChange;
                        int progres = (int) (100 * percentage);
                        prog.notificar("Progrés " + progres);
                        Thread.sleep(40);
                    }
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + (double) tempsFinal / 12500 + " " + -65281);
                }
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }
}
