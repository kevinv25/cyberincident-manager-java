/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase: Usuario
 * --------------
 * Representa a una persona que usa el sistema.
 * Controla las credenciales de acceso, el rol
 * y el estado de la cuenta (activa o bloqueada).
 */
public class Usuario {
    // Nombre con el que inicia sesion
    private String nombreUsuario;

    // Contrasena para autenticarse
    private String contrasena;

    // Rol en el sistema: "admin" o "analista"
    private String rol;

    // Indica si la cuenta esta activa o bloqueada
    private boolean activo;

    // Cantidad de intentos de login fallidos (maximo 3)
    private int intentosFallidos;

     // Constructor
    public Usuario(String nombreUsuario, String contrasena, String rol) {
        this.nombreUsuario    = nombreUsuario;
        this.contrasena       = contrasena;
        this.rol              = rol;
        this.activo           = true;  // siempre inicia activa
        this.intentosFallidos = 0;     // sin intentos fallidos al inicio
    }//FIN DEL CONSTRUCTOR
    
    // Getters
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasena() { return contrasena; }
    public String getRol() { return rol; }
    public boolean isActivo() { return activo; }
    public int getIntentosFallidos() { return intentosFallidos; }

    // Setters
    public void setActivo(boolean activo) { this.activo = activo; }
    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    // Muestra el usuario como texto en consola
    public String toString() {
        return "[" + rol.toUpperCase() + "] " + nombreUsuario +
               " | Cuenta: " + (activo ? "Activa" : "Bloqueada");
    }//FIN DE GETTERS Y SETTERS
    
}//FIN DE CLASS
