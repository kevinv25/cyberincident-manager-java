/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema;
import java.util.ArrayList;

/**
 * Clase: SistemaLog
 * -----------------
 * Guarda un historial de todos los eventos importantes
 * que ocurren en el sistema: logins, registros de
 * incidentes y cambios de estado.
 */

public class SistemaLog {
    // Lista que guarda cada evento como texto
    private ArrayList<String> registros;

    // Constructor
    public SistemaLog() {
        this.registros = new ArrayList<>();
    } //FIN DEL CONSTRUCTOR

    // Agrega un nuevo evento al historial
    public void agregar(String evento) {
        registros.add(evento);
    } //FIN DE AGREGAR

    // Muestra todo el historial de eventos
    public void mostrar() {
        if (registros.isEmpty()) {
            System.out.println("El log esta vacio.");
            return;
        }
        System.out.println("\n===== HISTORIAL DEL SISTEMA =====");
        for (String r : registros) {
            System.out.println(">> " + r);
        }
    } //FIN DE MOSTRAR
    
}//FIN DE CLASS
