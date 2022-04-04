package capitol3.model;

import capitol3.PerEsdeveniments;
import capitol3.control.Numero;
import capitol3.main;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    private main prog;
    private double time;
    private static final String[] ALGORISMES = {"Tradicional", "Karatsuba", "Mixte"};
    private String algorismeTriat = ALGORISMES[0];
    private Numero num1, num2, resultat;

    public Model(main p) {
        prog = p;
        time = 0.0;
    }

    public Numero getResultat() {
        return resultat;
    }

    public void setResultat(Numero resultat) {
        this.resultat = resultat;
    }

    public Numero getNum1() {
        return num1;
    }

    public void setNum1(Numero num1) {
        this.num1 = num1;
    }

    public Numero getNum2() {
        return num2;
    }

    public void setNum2(Numero num2) {
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
            this.num1 = new Numero(s);
        } else if (s.startsWith("Nombre 2")) {
            s = s.replaceAll("Nombre 2: ", "");
            this.num2 = new Numero(s);
            System.out.println("Segon nombre canviat a "+s);
        } else if (s.startsWith("Algorisme")) {
            s = s.replaceAll("Algorisme: ", "");
            this.algorismeTriat = s;
            System.out.println("Algorisme canviat a "+s);
        } else if (s.startsWith("Resultat")) {
            String sOriginal = s;
            s = s.replaceAll("Resultat: ", "");
            s = s.replaceAll("Temps: ", "");
            String[] res = s.split(" ");
            this.setResultat(new Numero(res[0]));
            this.setTime(Double.parseDouble(res[1]));
            this.prog.notificar("Vista: "+sOriginal);
        }
    }
}
