package capitol2.model;

import capitol2.PerEsdeveniments;
import capitol2.main;
import capitol2.model.Peces.*;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {

    private int tamanyTriat = 8;
    private Peça peçaTriada = new Reina();
    public String[] peces = {"Reina", "Torre", "Cavall", "Cardenal", "Centauro", "Somera"};
    private int progres = 0;
    private main prog;
    private int x, y; // posició de la peça

    public Model(main p) {
        prog = p;
    }

    public int getProgres() {
        return progres;
    }

    public void setProgres(int progres) {
        this.progres = progres;
    }

    public int getTamanyTriat() {
        return tamanyTriat;
    }

    public void setTamanyTriat(int tamanyTriat) {
        this.tamanyTriat = tamanyTriat;
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Tamany tauler")) {
            s = s.replaceAll("Tamany tauler: ", "");
            this.tamanyTriat = Integer.parseInt(s);
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

    public Peça getPeçaTriada() {
        return peçaTriada;
    }

    public void setPeçaTriada(Peça peçaTriada) {
        this.peçaTriada = peçaTriada;
    }
}
