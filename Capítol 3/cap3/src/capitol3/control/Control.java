package capitol3.control;

import capitol3.main;
import capitol3.PerEsdeveniments;

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
                resol();
                this.seguir = false;
            }
        }
    }
    
    private void resol() {
        Numero num1 = this.prog.getModel().getNum1();
        Numero num2 = this.prog.getModel().getNum2();
        Numero resultat = new Numero("-1");
        
        long inici = System.nanoTime();
        switch (this.prog.getModel().getAlgorismeTriat()) {
            case "Tradicional":
                resultat = tradicional(num1, num2);
                break;
            case "Karatsuba":
                resultat = karatsuba(num1, num2);
                break;
            case "Mixte":
                resultat = mixte(num1, num2);
                break;
        }
        double temps = (System.nanoTime() - inici) / 1000000000.0;
        System.out.println("Resultat: "+resultat.getNum()+"\nTemps: "+temps);
        prog.notificar("Resultat: "+resultat.getNum()+" Temps: "+temps);
    }
    
    private Numero tradicional(Numero n1, Numero n2) {
        return n1.producte(n2);
    }
    
    private Numero karatsuba(Numero n1, Numero n2) {
        int n;
        if (n1.getNum().length() > n2.getNum().length()) {
            n = n1.getNum().length();
        } else {
            n = n2.getNum().length();
        }
        if (n > 1) {
            int s = n/2;
            Numero[] ab = n1.xaparNumero();
            Numero[] cd = n2.xaparNumero();
            Numero a = ab[0];
            Numero b = ab[1];
            Numero c = cd[0];
            Numero d = cd[1];
            
            Numero ac = karatsuba(a, c);
            Numero bd = karatsuba(b, d);
            Numero carro = karatsuba(a.suma(b), c.suma(d)).resta(ac).resta(bd);
            
            return ac.producte(new Numero("10").potencia10(2*s)).suma(carro.producte(new Numero("10").potencia10(s))).suma(bd);
        } else {
            return n1.producte(n2);
        }
    }
    
    private Numero mixte(Numero n1, Numero n2) {
        int n;
        if (n1.getNum().length() > n2.getNum().length()) {
            n = n1.toString().length();
        } else {
            n = n2.toString().length();
        }
        if (n > 15) {
            int s = n/2;
            Numero[] ab = n1.xaparNumero();
            Numero[] cd = n2.xaparNumero();
            Numero a = ab[0];
            Numero b = ab[1];
            Numero c = cd[0];
            Numero d = cd[1];
            
            Numero ac = karatsuba(a, c);
            Numero bd = karatsuba(b, d);
            Numero carro = karatsuba(a.suma(b), c.suma(d)).resta(ac).resta(bd);
            
            return ac.producte(new Numero("10").potencia10(2*s)).suma(carro.producte(new Numero("10").potencia10(s))).suma(bd);
        } else {
            return n1.producte(n2);
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Executar")) {
            this.seguir = true;
            if (!this.executat) {
                this.start();
                System.out.println("Programa executat per primer pic");
            } else {
                System.out.println("Programa reanudat");
            }
        } else if (s.startsWith("Aturar")) {
            this.seguir = false;
            System.out.println("Programa aturat");
        }
    }
}
