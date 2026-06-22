/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.Duration;
import modelo.Usuario;

/**
 * Clase: GestorUsuarios
 * ----------------------
 * Administra la lista de usuarios del sistema y el proceso
 * de autenticacion (login).
 *
 * CARACTERISTICA NUEVA (no vista en clase):
 * Bloqueo TEMPORAL de cuentas. A diferencia de un bloqueo permanente,
 * cuando un usuario falla 3 veces su cuenta se bloquea por un tiempo
 * determinado (ver SEGUNDOS_BLOQUEO) y despues de ese tiempo se
 * desbloquea sola automaticamente, sin que un administrador tenga
 * que intervenir. Para lograrlo se guarda la hora exacta en la que
 * se bloqueo cada cuenta (LocalDateTime) y, cada vez que alguien
 * intenta entrar, se calcula cuanto tiempo ha pasado (Duration)
 * para decidir si ya se puede desbloquear.
 */
public class GestorUsuarios {

    // Maximo de intentos fallidos antes de bloquear la cuenta
    private static final int MAX_INTENTOS_FALLIDOS = 3;

    // Tiempo (en segundos) que dura el bloqueo temporal de una cuenta
    private static final int SEGUNDOS_BLOQUEO = 30;

    // Indices del arreglo de estadisticas (ver mas abajo)
    private static final int INDICE_EXITOSOS = 0;
    private static final int INDICE_FALLIDOS = 1;
    private static final int INDICE_BLOQUEOS = 2;

    // Lista de usuarios registrados en el sistema
    private ArrayList<Usuario> usuarios;

    // Log del sistema para registrar accesos
    private SistemaLog log;

    // Guarda la hora exacta en la que se bloqueo cada cuenta.
    // La llave es el nombre de usuario, el valor es la hora del bloqueo.
    private HashMap<String, LocalDateTime> horaBloqueo;

    // ARREGLO (requisito de arreglos): estadisticas de accesos del sistema.
    // estadisticas[0] = logins exitosos
    // estadisticas[1] = logins fallidos
    // estadisticas[2] = cuentas bloqueadas
    private int[] estadisticas;

    // Constructor
    public GestorUsuarios(SistemaLog log) {
        this.usuarios    = new ArrayList<>();
        this.log         = log;
        this.horaBloqueo = new HashMap<>();
        this.estadisticas = new int[3]; // se inicializa todo en 0
    }//FIN DEL CONSTRUCTOR

    // Agrega un usuario al sistema
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }//FIN DE AGREGAR USUARIO

    // Busca un usuario por su nombre (null si no existe)
    public Usuario buscarPorNombre(String nombreUsuario) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }//FIN DE BUSQUEDA

    /**
     * Revisa si una cuenta bloqueada ya cumplio su tiempo de espera.
     * Si ya paso el tiempo, la desbloquea automaticamente y reinicia
     * sus intentos fallidos.
     */
    private void revisarDesbloqueoAutomatico(Usuario usuario) {
        LocalDateTime momentoBloqueo = horaBloqueo.get(usuario.getNombreUsuario());

        if (momentoBloqueo == null) {
            return; // nunca se ha bloqueado, no hay nada que revisar
        }

        long segundosTranscurridos = Duration.between(momentoBloqueo, LocalDateTime.now()).getSeconds();

        if (segundosTranscurridos >= SEGUNDOS_BLOQUEO) {
            usuario.setActivo(true);
            usuario.setIntentosFallidos(0);
            horaBloqueo.remove(usuario.getNombreUsuario());
            log.agregar("Cuenta desbloqueada automaticamente por tiempo: " + usuario.getNombreUsuario());
        }
    }//FIN DE REVISAR DESBLOQUEO

    /**
     * Intenta autenticar a un usuario con nombre y contrasena.
     * Controla intentos fallidos y bloqueo TEMPORAL de la cuenta.
     * Retorna el Usuario si el login fue exitoso, o null si fallo.
     */
    public Usuario login(String nombreUsuario, String contrasena) {
        Usuario usuario = buscarPorNombre(nombreUsuario);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            log.agregar("Intento de login fallido: usuario '" + nombreUsuario + "' no existe.");
            return null;
        }

        // Si la cuenta esta bloqueada, primero revisamos si ya se puede
        // desbloquear sola por haber pasado el tiempo de espera.
        if (!usuario.isActivo()) {
            revisarDesbloqueoAutomatico(usuario);
        }

        if (!usuario.isActivo()) {
            long segundosTranscurridos = Duration.between(
                    horaBloqueo.get(usuario.getNombreUsuario()), LocalDateTime.now()).getSeconds();
            long segundosRestantes = SEGUNDOS_BLOQUEO - segundosTranscurridos;
            System.out.println("Cuenta bloqueada temporalmente. Intente de nuevo en "
                    + segundosRestantes + " segundos.");
            log.agregar("Intento de login en cuenta bloqueada: " + nombreUsuario);
            return null;
        }

        if (usuario.getContrasena().equals(contrasena)) {
            usuario.setIntentosFallidos(0);
            estadisticas[INDICE_EXITOSOS]++;
            log.agregar("Login exitoso: " + nombreUsuario);
            return usuario;

        } else {
            int nuevosIntentos = usuario.getIntentosFallidos() + 1;
            usuario.setIntentosFallidos(nuevosIntentos);
            estadisticas[INDICE_FALLIDOS]++;

            if (nuevosIntentos >= MAX_INTENTOS_FALLIDOS) {
                usuario.setActivo(false);
                horaBloqueo.put(nombreUsuario, LocalDateTime.now());
                estadisticas[INDICE_BLOQUEOS]++;
                System.out.println("Contrasena incorrecta. Cuenta bloqueada por "
                        + SEGUNDOS_BLOQUEO + " segundos.");
                log.agregar("Cuenta bloqueada temporalmente por intentos fallidos: " + nombreUsuario);
            } else {
                int restantes = MAX_INTENTOS_FALLIDOS - nuevosIntentos;
                System.out.println("Contrasena incorrecta. Intentos restantes: " + restantes);
                log.agregar("Intento de login fallido para " + nombreUsuario
                        + " (intento " + nuevosIntentos + ")");
            }
            return null;
        }
    }//FIN DE LOGIN

    /**
     * Muestra un mini-reporte de estadisticas de accesos usando
     * el arreglo "estadisticas".
     */
    public void mostrarEstadisticas() {
        System.out.println("\n===== ESTADISTICAS DE ACCESO =====");
        System.out.println("Logins exitosos:   " + estadisticas[INDICE_EXITOSOS]);
        System.out.println("Logins fallidos:    " + estadisticas[INDICE_FALLIDOS]);
        System.out.println("Cuentas bloqueadas: " + estadisticas[INDICE_BLOQUEOS]);
    }//FIN DE MOSTRAR ESTADISTICAS

}//FIN DE CLASS
