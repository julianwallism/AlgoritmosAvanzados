package capitulo7;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

public class Error {
    static public void informaError(Exception ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        String slogs = writer.toString();
        System.err.println("Se ha producido un error. El error es:\n" + slogs);
        try {
            FileWriter fr = new FileWriter("logs.txt", true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write("********************** Error date: " + (new Date()).toString());
            br.newLine();
            br.write(slogs);
            br.write("********************** End error report");
            br.newLine();
            br.newLine();
            br.close();
            fr.close();
        } catch (Exception e) {
            System.err.println("Error en la manipulación del archivo de logs");
            e.printStackTrace();
        }
    }
}
