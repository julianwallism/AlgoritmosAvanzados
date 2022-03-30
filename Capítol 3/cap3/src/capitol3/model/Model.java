package capitol3.model;

import capitol3.PerEsdeveniments;
import capitol3.main;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    private main prog;
    private double time;
    private static final String[] ALGORITMES = {"Tradicional", "Karatsuba", "Mixte"};
    private String algoritmeTriat = ALGORITMES[0];
    private int num1, num2;

    public Model(main p) {
        prog = p;
        time = 0.0;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }
    
    public static String[] getAlgoritmes() {
        return ALGORITMES;
    }

    public String getAlgoritmeTriat() {
        return algoritmeTriat;
    }

    public void setAlgoritmeTriat(String algoritmeTriat) {
        this.algoritmeTriat = algoritmeTriat;
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
            this.num1 = Integer.parseInt(s);
        } else if (s.startsWith("Nombre 2")) {
            s = s.replaceAll("Nombre 2: ", "");
            this.num2 = Integer.parseInt(s);
            System.out.println("Segon nombre canviat a "+s);
        } else if (s.startsWith("Algoritme")) {
            s = s.replaceAll("Algoritme: ", "");
            this.algoritmeTriat = s;
            System.out.println("Algoritme canviat a "+s);
        }
    }
}
