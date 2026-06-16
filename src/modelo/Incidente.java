/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDateTime;
/**
 * Clase: Incidente
 * ----------------
 * Representa un incidente de ciberseguridad registrado en el sistema.
 * Cada incidente tiene un identificador unico, un tipo de amenaza,
 * una descripcion, una prioridad, un estado y la fecha en que fue reportado.
 */
public class Incidente {
    // Identificador unico del incidente
    private int id;

    // Tipo de amenaza: "Phishing", "Malware", "Acceso no autorizado", etc.
    private String tipo;

    // Descripcion detallada de lo que ocurrio
    private String descripcion;

    // Nivel de urgencia: "Alta", "Media" o "Baja"
    private String prioridad;

    // Estado actual: "Pendiente", "En investigacion" o "Resuelto"
    private String estado;

    // Fecha y hora en que se registro el incidente
    private LocalDateTime fechaRegistro;

     // Constructor
    public Incidente(int id, String tipo, String descripcion, String prioridad) {
        this.id           = id;
        this.tipo         = tipo;
        this.descripcion  = descripcion;
        this.prioridad    = prioridad;
        this.estado       = "Pendiente";
        this.fechaRegistro = LocalDateTime.now();
    }// FIN DEL CONSTRUCTOR 
    // Getters
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public String getPrioridad() { return prioridad; }
    public String getEstado() { return estado; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }

    // Setters (solo estado puede cambiar despues de crearse)
    public void setEstado(String estado) { this.estado = estado; }

    // Muestra el incidente como texto en consola
    public String toString() {
        return "[#" + id + "] " + tipo + " | " + prioridad + " | " + estado;
    }// FIN DE GETTERS Y SETTERS
    
}//FIN DE CLASS
