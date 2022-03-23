package capitol2.model;

import capitol2.PerEsdeveniments;
import capitol2.main;
import capitol2.model.Peces.*;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {

    private int tamanyTriat;
    private Peça peçaTriada;
    public String[] peces = {"Reina", "Torre", "Cavall", "Cardenal", "Centauro", "Somera"};
    private main prog;
    public Tauler tauler;
    private int x, y; // posició de la peça

    public Model(main p) {
        prog = p;
        tamanyTriat = 8;
        tauler = new Tauler();
        tauler.setDim(tamanyTriat);
        x = -1;
        y = -1;
        peçaTriada = new Reina();
    }


    public int getTamanyTriat() {
        return tamanyTriat;
    }

    public void setTamanyTriat(int tamanyTriat) {
        this.tamanyTriat = tamanyTriat;
        tauler.setDim(this.tamanyTriat);
    }

    public Tauler getTauler() {
        return tauler;
    }

    public void setTauler(Tauler tauler) {
        this.tauler = tauler;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Peça getPeçaTriada() {
        return peçaTriada;
    }

    public void setPeçaTriada(Peça peçaTriada) {
        this.peçaTriada = peçaTriada;
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Tamany tauler")) {
            s = s.replaceAll("Tamany tauler: ", "");
            this.tamanyTriat = Integer.parseInt(s);
            this.tauler.setDim(this.tamanyTriat);
            prog.notificar("Actualitzar tauler");
        } else if (s.startsWith("Peça")) {
            String[] res = s.split(", ");
            this.x = Integer.parseInt(res[0].replaceAll("Peça ", ""));
            this.y = Integer.parseInt(res[1]);
        } else if (s.startsWith("Canvi peça")) {
            s = s.replaceAll("Canvi peça a ", "");
            switch (s) {
                case "Cavall":
                    this.peçaTriada = new Cavall();
                    break;
                case "Reina":
                    this.peçaTriada = new Reina();
                    break;
                case "Torre":
                    this.peçaTriada = new Torre();
                    break;
                case "Cardenal":
                    this.peçaTriada = new Cardenal();
                    break;
                case "Centauro":
                    this.peçaTriada = new Centauro();
                    break;
                case "Somera":
                    this.peçaTriada = new Somera();
                    break;
            }
            System.out.println(peçaTriada.imatge);
        }
    }

}
