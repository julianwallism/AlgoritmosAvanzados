package capitulo5;

import capitulo5.control.Control;
import capitulo5.modelo.Modelo;
import capitulo5.vista.Vista;

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
        vis = new Vista("Práctica Capitulo 5 - Víctor Blanes, Dawid Roch y Julià Wallis", this);
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
        if (s.startsWith("Comprobar texto") || s.startsWith("Actualizar")) {
            this.con.notificar(s);
        } else if (s.startsWith("Texto comprobado") || s.startsWith("Actualizado") || s.startsWith("No idioma detectado")) {
            this.vis.notificar(s);
        }
    }
}
