package capitol3.control;

import capitol3.main;
import capitol3.PerEsdeveniments;
import java.util.Random;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Control extends Thread implements PerEsdeveniments {

    private final main prog;
    private boolean seguir, executat;
    Random rand = new Random();

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
        String resultat = "0";
        //Comprovam si la multiplicació donarà un resultat negatiu
        boolean negatiu = (num1.charAt(0) == '-' || num2.charAt(0) == '-') && !(num1.charAt(0) == '-' && num2.charAt(0) == '-');
        if (num1.charAt(0) == '-') {
            num1 = num1.substring(1);
        }
        if (num2.charAt(0) == '-') {
            num2 = num2.substring(1);
        }

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
        if (negatiu) {
            resultat = '-' + resultat;
        }
        double temps = (System.nanoTime() - inici) / 1000000000.0;
        System.out.println("Resultat: " + resultat + "\nTemps: " + temps);
        prog.getModel().setResultat(resultat);
        prog.getModel().setTime(temps);
        prog.notificar("Resultat");
    }

    private String tradicional(String n1, String n2) {
        return Numero.producte(n1, n2);
    }

    private String karatsuba(String n1, String n2) {
        String resultat;
        int n;
        if (n1.length() > n2.length()) {
            n = n1.length();
        } else {
            n = n2.length();
        }
        if (n > 2) { // aplicam karatsuba fins que la multiplicacio sigui directa (dos nombres d'un dígit)
            int s = n / 2;
            if (2 * s != n) {
                s++;
            }
            String[] abcd = Numero.xaparNumeros(n1, n2, s);
            String a = abcd[0];
            String b = abcd[1];
            String c = abcd[2];
            String d = abcd[3];

            String ac = karatsuba(a, c);
            String bd = karatsuba(b, d);
            String carro = Numero.resta(Numero.resta(karatsuba(Numero.suma(a, b), Numero.suma(c, d)), ac), bd);
            ac = Numero.posarZeros(ac, 2 * s, true);
            carro = Numero.posarZeros(carro, s, true);
            resultat = Numero.suma(Numero.suma(ac, carro), bd);
            // Per molt que sigui "0000000000000000" retornam un "0"
            if (Numero.esZero(resultat)) {
                return "0";
            }

            // Llevam tots els zeros de davant en cas de tenir-ne
            while (resultat.charAt(0) == '0') {
                resultat = resultat.substring(1);
            }

            return resultat;
        } else {
            return Numero.producte(n1, n2);
        }
    }

    private String mixte(String n1, String n2) {
        String resultat;
        int n;
        if (n1.length() > n2.length()) {
            n = n1.length();
        } else {
            n = n2.length();
        }
        if (n > this.prog.getModel().getUmbral()) { // aplicam karatsuba fins que la multiplicacio sigui directa (dos nombres d'un dígit)
            int s = n / 2;
            if (2 * s != n) {
                s++;
            }
            String[] abcd = Numero.xaparNumeros(n1, n2, s);
            String a = abcd[0];
            String b = abcd[1];
            String c = abcd[2];
            String d = abcd[3];

            String ac = mixte(a, c);
            String bd = mixte(b, d);
            String carro = Numero.resta(Numero.resta(mixte(Numero.suma(a, b), Numero.suma(c, d)), ac), bd);
            ac = Numero.posarZeros(ac, 2 * s, true);
            carro = Numero.posarZeros(carro, s, true);
            resultat = Numero.suma(Numero.suma(ac, carro), bd);

            // Per molt que sigui "0000000000000000" retornam un "0"
            if (Numero.esZero(resultat)) {
                return "0";
            }

            // Llevam tots els zeros de davant en cas de tenir-ne
            while (resultat.charAt(0) == '0') {
                resultat = resultat.substring(1);
            }

            return resultat;
        } else {
            return Numero.producte(n1, n2);
        }
    }

    private String[] randNum(int length) {
        String num1 = "";
        String num2 = "";
        //No pot començar per 0
        num1 += rand.nextInt(9) + 1;
        num2 += rand.nextInt(9) + 1;
        for (int i = 0; i < length - 1; i++) {
            num1 += rand.nextInt(10);
            num2 += rand.nextInt(10);
        }
        String[] ret = new String[2];
        ret[0] = num1;
        ret[1] = num2;
        return ret;
    }

    
    private void estudi() {
        double tempsTradicional;
        double tempsKaratsuba;
        double tempsMixte;
        double[][] estudi = new double[3][700];
        long tempsActual;
        String num1;
        String num2;
        String[] nums;

        //Cream les dades per a graficar i treure un umbral recomanat
        for (int umbral = 1; umbral < 700; umbral++) {

            nums = randNum(umbral);
            num1 = nums[0];
            num2 = nums[1];

            tempsActual = System.nanoTime();
            tradicional(num1, num2);
            tempsTradicional = (System.nanoTime() - tempsActual) / 1000000000.0;

            nums = randNum(umbral);
            num1 = nums[0];
            num2 = nums[1];

            tempsActual = System.nanoTime();
            karatsuba(num1, num2);
            tempsKaratsuba = (System.nanoTime() - tempsActual) / 1000000000.0;

            nums = randNum(umbral);
            num1 = nums[0];
            num2 = nums[1];

            tempsActual = System.nanoTime();
            mixte(num1, num2);
            tempsMixte = (System.nanoTime() - tempsActual) / 1000000000.0;

            estudi[0][umbral] = tempsTradicional;
            estudi[1][umbral] = tempsKaratsuba;
            estudi[2][umbral] = tempsMixte;
        }
        recomanarUmbral(estudi);

        this.prog.getModel().setEstudi(estudi);
        this.prog.notificar("Fet estudi");
    }
   

    private void recomanarUmbral(double[][] estudi) {

        double[] tradicional = estudi[0];
        double[] karatsuba = estudi[1];

        int atura = 25;
        int index = 335;

        //Find the index when the values in karatsuba have been greater than the ones in traditional ten times in a row
        for (int i = 10; i < tradicional.length; i++) {
            if (tradicional[i] > karatsuba[i]) {
                atura--;
            } else {
                atura = 25;
            }
            if (atura == 0) {
                index = i - 25;
                break;
            }
        }
        this.prog.getModel().setUmbral(index);
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Executar")) {
            this.seguir = true;
            if (!this.executat) {
                this.start();
            } else {
                System.out.println("Programa reanudat");
            }
        } else if (s.startsWith("Aturar")) {
            this.seguir = false;
            System.out.println("Programa aturat");
        } else if (s.startsWith("Estudi")) {
            estudi();
        }
    }
}
