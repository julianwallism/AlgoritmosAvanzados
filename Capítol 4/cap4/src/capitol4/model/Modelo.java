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
    private double entropia, entropiaReal, expectedSize;
    private int bufferSize = 32;
    private HashMap<Byte, String> codes;

    public Modelo(main p) {
        prog = p;
    }

    /**
     * Getter de ficheroInput
     * @return 
     */
    public File getFicheroInput() {
        return ficheroInput;
    }

    /**
     * Setter de ficheroInput
     * @param ficheroInput
     */
    public void setFicheroInput(File ficheroInput) {
        this.ficheroInput = ficheroInput;
    }

    /**
     * Getter de ficheroOutput
     * @return 
     */
    public File getFicheroOutput() {
        return ficheroOutput;
    }

    /**
     * Setter de ficheroOutput
     * @param ficheroOutput
     */
    public void setFicheroOutput(File ficheroOutput) {
        this.ficheroOutput = ficheroOutput;
    }

    /**
     * Getter de entropia
     * @return 
     */
    public double getEntropia() {
        return entropia;
    }

    /**
     * Setter de entropia
     * @param entropia
     */
    public void setEntropia(Double entropia) {
        this.entropia = entropia;
    }

    /**
     * Getter de entropiaReal
     * @return 
     */
    public double getEntropiaReal() {
        return entropiaReal;
    }

    /**
     * Setter de entropiaReal
     * @param entropiaReal
     */
    public void setEntropiaReal(Double entropiaReal) {
        this.entropiaReal = entropiaReal;
    }

    /**
     * Getter de expectedSize
     * @return 
     */
    public double getExpectedSize() {
        return expectedSize;
    }
    
    /**
     * Setter de expectedSize
     * @param expectedSize
     */
    public void setExpectedSize(double expectedSize) {
        this.expectedSize = expectedSize;
    }   
    
    /**
     * Getter de freq
     * @return 
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * Setter de freq
     * @param bufferSize
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * Getter de codes
     * @return 
     */
    public HashMap<Byte, String> getCodes() {
        return codes;
    }

    /**
     * Setter de codes
     * @param codes
     */
    public void setCodes(HashMap<Byte, String> codes) {
        this.codes = codes;
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