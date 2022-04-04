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
        String num1 = this.prog.getModel().getNum1();
        String num2 = this.prog.getModel().getNum2();
        String resultat = "-1";
        
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
        System.out.println("Resultat: "+resultat+"\nTemps: "+temps);
        prog.notificar("Resultat: "+resultat+" Temps: "+temps);
    }
    
    private String tradicional(String n1, String n2) {
        return Numero.producte(n1, n2);
    }
    
    private String karatsuba(String n1, String n2) {
        String resultat = "";
        int n;
        if (n1.length() > n2.length()) {
            n = n1.length();
        } else {
            n = n2.length();
        }
        if (n > 2) {
            int s = n/2;
            String[] ab = Numero.xaparNumero(n1);
            String[] cd = Numero.xaparNumero(n2);
            String a = ab[0];
            String b = ab[1];
            String c = cd[0];
            String d = cd[1];
            System.out.println("n1: "+n1+": "+a+" | "+b);
            System.out.println("n2: "+n2+": "+c+" | "+d);
            
            String ac = karatsuba(a, c);
            System.out.println("ac: "+ac);
            String bd = karatsuba(b, d);
            System.out.println("bd: "+bd);
            String carro = Numero.resta(Numero.resta(karatsuba(Numero.suma(a, b), Numero.suma(c, d)), ac), bd);
            System.out.println("carro: "+carro);
            resultat = Numero.suma(Numero.suma(Numero.potencia10(ac, 2*s), Numero.potencia10(carro, s)), bd);
            
            while (resultat.charAt(0) == '0') {
                resultat = resultat.substring(1);
            }
            
            return resultat;            
        } else {
            return Numero.producte(n1, n2);
        }
    }
    
    private String mixte(String n1, String n2) {
        return "";
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
