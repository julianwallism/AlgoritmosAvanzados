package capitol4.model;

import capitol4.PerEsdeveniments;
import capitol4.main;
import java.io.File;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Model implements PerEsdeveniments {
    private main prog;
    private double time;
    private int retornSelector;
    private File fitxerTriat;// = new File("elquijote.txt"); 
    private File fitxerCompressat;
    private int bufferSize = 1024;

    public Model(main p) {
        prog = p;
        time = 0.0;
    }

    public File getFitxerTriat() {
        return fitxerTriat;
    }

    public void setFitxerTriat(File fitxerTriat) {
        this.fitxerTriat = fitxerTriat;
    }

    public File getFitxerCompressat() {
        return fitxerCompressat;
    }

    public void setFitxerCompressat(File fitxerCompressat) {
        this.fitxerCompressat = fitxerCompressat;
    }

    public int getRetornSelector() {
        return retornSelector;
    }

    public void setRetornSelector(int retornSelector) {
        this.retornSelector = retornSelector;
    }

    public double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public void notificar(String s) {
        // if (s.startsWith("Fitxer")) {
        // System.out.println(s);
        // this.fitxerTriat = new File(s.replaceAll("Fitxer: ", ""));
        // }
    }
    // Return HashMap with frequency of each character in the file
}