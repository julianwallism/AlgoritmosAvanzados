package capitol3.control;

import capitol3.main;
import capitol3.PerEsdeveniments;
import java.math.BigInteger;

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
        BigInteger num1 = this.prog.getModel().getNum1();
        BigInteger num2 = this.prog.getModel().getNum2();
        BigInteger resultat = new BigInteger("-1");
        
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
        System.out.println("Resultat: "+resultat.toString()+"\nTemps: "+temps);
        prog.notificar("Resultat: "+resultat.toString()+" Temps: "+temps);
    }
    
    private BigInteger tradicional(BigInteger n1, BigInteger n2) {
        return n1.multiply(n2);
    }
    
    private BigInteger karatsuba(BigInteger n1, BigInteger n2) {
        int n;
        if (n1.toString().length() > n2.toString().length()) {
            n = n1.toString().length();
        } else {
            n = n2.toString().length();
        }
        if (n > 1) {
            int s = n/2;
            BigInteger a = n1.divide(new BigInteger("10").pow(s));
            BigInteger b = n1.mod(new BigInteger("10").pow(s));
            BigInteger c = n2.divide(new BigInteger("10").pow(s));
            BigInteger d = n2.mod(new BigInteger("10").pow(s));
            
            BigInteger ac = karatsuba(a, c);
            BigInteger bd = karatsuba(b, d);
            BigInteger carro = karatsuba(a.add(b), c.add(d)).subtract(ac).subtract(bd);
            
            return ac.multiply(new BigInteger("10").pow(2*s)).add(carro.multiply(new BigInteger("10").pow(s))).add(bd);
        } else {
            return n1.multiply(n2);
        }
    }
    
    private BigInteger mixte(BigInteger n1, BigInteger n2) {
        return n1.multiply(n2);
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
