package capitol2.model;

import capitol2.PerEsdeveniments;
import capitol2.main;
import java.awt.Color;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    public String[] tamanysTauler = {"4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private String[] peces = {"Cavall", "Reina", "Alfil"};
    private int tamanyTriat = 8;
    private String peçaTriada = peces[0];
    private Casella[][] tauler;
    private int progres = 0;
    private main prog;
    
    public Model(main p) {
        prog = p;
        inicialitzarCaselles();
    }
    
    private void inicialitzarCaselles() {
        tauler = new Casella[tamanyTriat][tamanyTriat];
        for (int i = 0; i < tamanyTriat; i++) {
            for (int j = 0; j < tamanyTriat; j++) {
                if ((i + j) % 2 == 0) {
                    tauler[i][j] = new Casella(Color.WHITE);
                } else {
                    tauler[i][j] = new Casella(Color.BLACK);
                }
            }
        }
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
        if (s.startsWith("Tamany canviat")) {
            this.inicialitzarCaselles();
        }
    }

    public String[] getPeces() {
        return peces;
    }

    public String getPeçaTriada() {
        return peçaTriada;
    }

    public void setPeçaTriada(String peçaTriada) {
        this.peçaTriada = peçaTriada;
    }

    public Casella[][] getTauler() {
        return tauler;
    }
}
