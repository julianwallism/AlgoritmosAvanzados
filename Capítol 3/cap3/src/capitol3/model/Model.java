package capitol3.model;

import capitol3.PerEsdeveniments;
import capitol3.main;
import java.math.BigInteger;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    private main prog;
    private double time;
    private static final String[] ALGORISMES = {"Tradicional", "Karatsuba", "Mixte"};
    private String algorismeTriat = ALGORISMES[0];
    private BigInteger num1, num2, resultat;

    public Model(main p) {
        prog = p;
        time = 0.0;
    }

    public BigInteger getResultat() {
        return resultat;
    }

    public void setResultat(BigInteger resultat) {
        this.resultat = resultat;
    }

    public BigInteger getNum1() {
        return num1;
    }

    public void setNum1(BigInteger num1) {
        this.num1 = num1;
    }

    public BigInteger getNum2() {
        return num2;
    }

    public void setNum2(BigInteger num2) {
        this.num2 = num2;
    }
    
    public static String[] getAlgorismes() {
        return ALGORISMES;
    }

    public String getAlgorismeTriat() {
        return algorismeTriat;
    }

    public void setAlgorismeTriat(String algorismeTriat) {
        this.algorismeTriat = algorismeTriat;
    }
    
     public double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
    
    @Override
    public void notificar(String s) {
        if (s.startsWith("Nombre 1")) {
            s = s.replaceAll("Nombre 1: ", "");
            System.out.println("Primer nombre canviat a "+s);
            this.num1 = new BigInteger(s);
        } else if (s.startsWith("Nombre 2")) {
            s = s.replaceAll("Nombre 2: ", "");
            this.num2 = new BigInteger(s);
            System.out.println("Segon nombre canviat a "+s);
        } else if (s.startsWith("Algoritme")) {
            s = s.replaceAll("Algoritme: ", "");
            this.algorismeTriat = s;
            System.out.println("Algoritme canviat a "+s);
        } else if (s.startsWith("Resultat")) {
            String sOriginal = s;
            s = s.replaceAll("Resultat: ", "");
            s = s.replaceAll("Temps: ", "");
            String[] res = s.split(" ");
            this.setResultat(new BigInteger(res[0]));
            this.setTime(Double.parseDouble(res[1]));
            this.prog.notificar("Vista: "+sOriginal);
        }
    }
}
