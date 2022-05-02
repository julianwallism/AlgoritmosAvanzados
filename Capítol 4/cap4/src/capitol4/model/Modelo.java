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
    private double time, entropia, entropiaReal;
    private int bufferSize = 32;
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
     * Getter de entropia
     */
    public double getEntropia() {
        return entropia;
    }

    /**
     * Setter de entropia
     */
    public void setEntropia(Double entropia) {
        this.entropia = entropia;
    }

    /**
     * Getter de entropiaReal
     */
    public double getEntropiaReal() {
        return entropiaReal;
    }

    /**
     * Setter de entropiaReal
     */
    public void setEntropiaReal(Double entropiaReal) {
        this.entropiaReal = entropiaReal;
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
     * Método que calcula la entropia Real en base al tamaño del fichero input,
     * el del output y la entropia teorica.
     * 
     * Nos basamos en esta fórmula:
     *  EntropiaReal = (8 / EntropiaTeorica) / (TamañoFicheroInput / TamañoFicheroOutput)
     */
    public void entropiaReal(){
        long bytesInput = ficheroInput.length();
        long bytesOutput = ficheroOutput.length();
        this.entropiaReal = (8.0 / this.entropia) / (bytesInput / bytesOutput);
    }

    /**
     * Método notificar de la intefaz por esdevenimientos
     * Puede recibir un tipo de mensaje:
     * - "Fichero Eliminado": Pone ficherInput y ficheroOutput a null
     * 
     * @param s
     */
    @Override
    public void notificar(String s) {
        if (s.startsWith("Fichero eliminado")) {
            this.ficheroInput = this.ficheroOutput = null;
        }
    }
}