package capitulo7;

import capitulo7.control.Control;
import capitulo7.modelo.Modelo;
import capitulo7.vista.Vista;

/**
 * @authors Víctor Blanes, Dawid Roch y Julià Wallis
 */
public class main implements PorEventos {

    private Modelo mod;  // Puntero al Modelo
    private Vista vis;   // Puntero a la Vista
    private Control con; // Puntero al Control

    // Construcción del esquema MVC
    private void inicio() {
        mod = new Modelo(this);
        con = new Control(this);
        vis = new Vista("Práctica Capitulo 7 - Víctor Blanes, Dawid Roch y Julià Wallis", this);
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

    // Función para gestionar el patrón de eventos centralizado
    @Override
    public void notificar(String s) {
        if (s.startsWith("Ejecutar")) {
            con.notificar(s);
        }
    }
}
