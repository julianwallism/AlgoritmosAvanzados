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
            if (!this.executat) this.start();
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
                    int progres = (int) (100*((double)j/(double)Math.sqrt(i)));
                    prog.notificar("Progrés "+progres);
                    Thread.sleep(1);
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + tempsFinal);
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
                for (int j = 0; j < Math.log(i) && this.seguir; j++) {
                    int progres = (int) (100*((double)j/(double)Math.log(i)));
                    prog.notificar("Progrés "+progres);
                    Thread.sleep(1);
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + tempsFinal);
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
                    int progres = (int) (100*((double)j/(double)i));
                    prog.notificar("Progrés "+progres);
                    Thread.sleep(1);
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + tempsFinal);
                }
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    private void nlogn() {
        try {
            for (int i : prog.getModel().ns) {
                prog.notificar("Progrés 0");
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 1; j <= i; j++) {
                    for (int k = 1; k <= Math.log(i) && this.seguir; k++) {
                        // int progres = (int) (100*((double)(double)j*(double)k/(double)((double)i*(double)Math.log(i))));
                        // prog.notificar("Progrés "+progres);
                        Thread.sleep(1);
                    }
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + tempsFinal);
                }
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    private void n2() {
        try {
            for (int i : prog.getModel().ns) {
                prog.notificar("Progrés 0");
                // començar a contar temps
                long tempsInici = System.currentTimeMillis();
                for (int j = 1; j <= i; j++) {
                    for (int k = 1; k <= i && this.seguir; k++) {
                        // int progres = (int) (100*(((double)j*(double)k)/((double)i*(double)i)));
                        // prog.notificar("Progrés "+progres);
                        Thread.sleep(1);
                    }
                }
                // notificar que n ha acabat
                if (this.seguir) {
                    prog.notificar("Progrés 100");
                    long tempsFinal = System.currentTimeMillis() - tempsInici;
                    prog.notificar("Event iter " + i + " " + tempsFinal);
                }
            }
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }
}
