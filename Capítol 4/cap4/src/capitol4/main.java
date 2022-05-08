package capitol4;

import capitol4.control.Control;
import capitol4.model.Modelo;
import capitol4.vista.Vista;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class main implements PerEsdeveniments {

    private Modelo mod; // Punter al Modelo
    private Vista vis; // Punter a la Vista
    private Control con; // Punter al Control

    // Construcció de l'esquema MVC
    private void inicio() {
        mod = new Modelo(this);
        con = new Control(this);
        vis = new Vista("Práctica Capitulo 4 - Víctor Blanes, Dawid Roch y Julià Wallis", this);
    }
    
/*
    public void estudio() {
        try {
            PrintWriter writer = new PrintWriter("estudi.csv");
            StringBuilder sb = new StringBuilder();
            sb.append("Nombre\t Tamaño input\t Tamaño output\t Tamaño esperado\t"
                    + " Ratio de compresión\t Entropia\t Entropia real\n");
            for (File f : new File("archivosEstudio").listFiles()) {
                mod.setFicheroInput(f);
                con.compress();
                File archivoInput = mod.getFicheroInput();
                File archivoOutput = mod.getFicheroOutput();
                Double entropia = mod.getEntropia();
                Double entropiaReal = mod.getEntropiaReal();
                Double expectedSize = mod.getExpectedSize();
                Double compressionRate = ((double) archivoOutput.length() / (double) archivoInput.length()) * 100;
                sb.append(archivoInput.getName() + "\t " + printFileSize(archivoInput.length())
                        + "\t " + printFileSize(archivoOutput.length()) 
                        + "\t " + printFileSize(expectedSize.longValue()) 
                        + "\t " + compressionRate + "\t " + entropia 
                        + "\t " + entropiaReal+"\n");
                System.out.println(archivoInput.getName());
            }
              writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
        }
    }
*/

    public static String printFileSize(Long bytes) {
        long kilobytes = (bytes / 1024);
        long megabytes = (kilobytes / 1024);
        long gigabytes = (megabytes / 1024);
        long terabytes = (gigabytes / 1024);

        if (terabytes != 0) {
            return terabytes + " TB";
        } else if (gigabytes != 0) {
            return gigabytes + " GB";
        } else if (megabytes != 0) {
            return megabytes + " MB";
        } else if (kilobytes != 0) {
            return kilobytes + " KB";
        } else if (bytes != 0) {
            return bytes + " B";
        } else {
            return "0";
        }
    }

    public static void main(String[] args) {
        (new main()).inicio();
    }

    // Funció símple de la comunicació per Patró d'esdeveniments
    @Override
    public void notificar(String s) {
        if (s.startsWith("Fichero subido") || s.startsWith("Compresion realizada") || s.startsWith("Entropia")) {
            vis.notificar(s);
        } else if (s.startsWith("Comprime") || s.startsWith("Descomprime") || s.startsWith("Aturar")) {
            con.notificar(s);
        } else if (s.startsWith("Fichero eliminado")) {
            vis.notificar(s);
            mod.notificar(s);
        }
    }

    // Mètode public de retorn de la instància del model de dades
    public Modelo getModelo() {
        return mod;
    }
}
