package capitulo7;

import capitulo7.control.Control;
import capitulo7.modelo.Modelo;
import capitulo7.vista.Vista;

/**
 * @authors Víctor Blanes, Dawid Roch y Juli� Wallis
 */
public class main implements PorEventos {

    private Modelo mod; // Puntero al Modelo
    private Vista vis; // Puntero a la Vista
    private Control con; // Puntero al Control

    // Construcción del esquema MVC
    private void inicio() {
        mod = new Modelo(this);
        con = new Control(this);
        vis = new Vista("Práctica Capitulo 7 - Víctor Blanes, Dawid Roch y Julià Wallis", this);
        testDeRendimiento();
    }

    public static void main(String[] args) {
        (new main()).inicio();
    }

    public Modelo getModelo() {
        return this.mod;
    }

    public Control getControl() {
        return this.con;
    }

    public Vista getVista() {
        return this.vis;
    }

    public void testDeRendimiento() {
        System.out.println("Test de rendimiento...");
        int N = 5;
        int[] porcentajes = { 1,2,3,4 };
        float[] tiempo = new float[N];
        float correctas = 0;
        String csv = "porcentajeMuestreo,tiempo,correctas\n";

        for (int i = 0; i < porcentajes.length; i++) {
            mod.setPorcentajeMuestreo(porcentajes[i]);
            for (int j = 0; j < N; j++) {
                notificar("Ejecuta unit test");
                float[] aux = mod.getTiempoUnitTest();
                for (int k = 0; k < aux.length; k++) {
                    tiempo[j] += aux[k];
                }
                correctas = (float) 100 * mod.getCorrectas() / 196;
                csv += porcentajes[i] + "," + tiempo[j] + "," + correctas + "\n";
            }
        }
        try {
            java.io.FileWriter fw = new java.io.FileWriter("testDeRendimiento.csv");
            fw.write(csv);
            fw.close();
        } catch (Exception e) {
            System.out.println("Error al escribir el fichero");
        }
        System.out.println("Test de rendimiento finalizado");
    }

    // Función para gestionar el patrón de eventos centralizado
    @Override
    public void notificar(String s) {
        if (s.startsWith("Ejecuta muestreo") || s.startsWith("Ejecuta unit test")) {
            mod.notificar(s);
            con.notificar(s);
        } else if (s.startsWith("Ejecución terminada") || s.startsWith("Unit test terminados")) {
            vis.notificar(s);
        }
    }
}
