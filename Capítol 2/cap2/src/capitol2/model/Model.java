package capitol2.model;

import capitol2.PerEsdeveniments;
import capitol2.main;
import capitol2.model.Peces.*;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {

    public final static String[] peces = {"Reina", "Torre", "Cavall", "Cardenal", "Centauro", "Somera"};

    private int tamanyTriat;
    private Peça peçaTriada;
    private main prog;
    private Tauler tauler;
    private int x, y, numParets; // posició de la peça
    private double time;

    public Model(main p) {
        prog = p;
        tamanyTriat = 8;
        tauler = new Tauler(this.tamanyTriat);
        tauler.setDim(tamanyTriat);
        x = -1;
        y = -1;
        time = 0.0;
        peçaTriada = new Reina();
        numParets =0;
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Tamany tauler")) {
            s = s.replaceAll("Tamany tauler: ", "");
            tamanyTriat = Integer.parseInt(s);
            initTauler();
            prog.notificar("Actualitzar tauler");
        } else if (s.startsWith("Peça")) {
            String[] res = s.split(", ");
            x = Integer.parseInt(res[0].replaceAll("Peça ", ""));
            y = Integer.parseInt(res[1]);
            numParets=0;
        } else if (s.startsWith("Paret")) {
            String[] res = s.split(", ");
            int paretX = Integer.parseInt(res[0].replaceAll("Paret ", ""));
            int paretY = Integer.parseInt(res[1]);
            tauler.setCasella(paretX, paretY, -1);
            numParets++;
        } else if (s.startsWith("Canvi peça")) {
            s = s.replaceAll("Canvi peça a ", "");
            switch (s) {
                case "Cavall":
                    peçaTriada = new Cavall();
                    break;
                case "Reina":
                    peçaTriada = new Reina();
                    break;
                case "Torre":
                    peçaTriada = new Torre();
                    break;
                case "Cardenal":
                    peçaTriada = new Cardenal();
                    break;
                case "Centauro":
                    peçaTriada = new Centauro();
                    break;
                case "Somera":
                    peçaTriada = new Somera();
                    break;
            }
            numParets=0;
        }
    }

    private void initTauler() {
        tauler = new Tauler(tamanyTriat);
        x = -1;
        y = -1;
        numParets=0;
    }

    public int getTamanyTriat() {
        return tamanyTriat;
    }

    public void setTamanyTriat(int tamanyTriat) {
        this.tamanyTriat = tamanyTriat;
    }

    public int getNumParets() {
        return numParets;
    }

    public Peça getPeçaTriada() {
        return peçaTriada;
    }

    public void setPeçaTriada(Peça peçaTriada) {
        this.peçaTriada = peçaTriada;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tauler getTauler() {
        return tauler;
    }

    public void setTauler(Tauler tauler) {
        this.tauler = tauler;
    }

    public double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
