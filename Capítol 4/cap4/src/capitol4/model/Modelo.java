package capitol4.model;

import capitol4.PerEsdeveniments;
import capitol4.main;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Modelo implements PerEsdeveniments {
    private main prog;
    private File ficheroInput;
    private File ficheroOutput;
    private int retornSelector;
    private double time; 
    private int bufferSize = 1024;
    private HashMap<Byte, String> codes;

    public Modelo(main p) {
        prog = p;
        time = 0.0;
    }

    /** 
     * Getter de ficheroInput
     */
    public File getFicheroInput() {
        return ficheroInput;
    }

    /**
     * Setter de ficheroInput
     */
    public void setFicheroInput(File ficheroInput) {
        this.ficheroInput = ficheroInput;
    }

    /**
     * Getter de ficheroOutput
     */
    public File getFicheroOutput() {
        return ficheroOutput;
    }

    /**
     * Setter de ficheroOutput
     */
    public void setFicheroOutput(File ficheroOutput) {
        this.ficheroOutput = ficheroOutput;
    }

    /**
     * Getter de retornSelector
     */
    public int getRetornSelector() {
        return retornSelector;
    }

    /**
     * Setter de retornSelector
     */
    public void setRetornSelector(int retornSelector) {
        this.retornSelector = retornSelector;
    }

    /**
     * Getter de time
     */
    public double getTime() {
        return time;
    }

    /**
     * Setter de time
     */
    public void setTime(Double time) {
        this.time = time;
    }

    /**
     * Getter de freq
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * Setter de freq
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * Getter de codes
     */
    public HashMap<Byte, String> getCodes() {
        return codes;
    }   

    /**
     * Setter de codes
     */
    public void setCodes(HashMap<Byte, String> codes) {
        this.codes = codes;
    }

    /**
     * Método notificar de la intefaz por esdevenimientos
     * Puede recibir un tipo de mensaje:
     * - "Fichero Eliminado": Pone ficherInput y ficheroOutput a null
     * @param s
     */
    @Override
    public void notificar(String s) {
        if (s.startsWith("Fichero eliminado")) {
            this.ficheroInput = this.ficheroOutput = null;
        }
    }
}