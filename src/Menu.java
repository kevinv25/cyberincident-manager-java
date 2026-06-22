/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import modelo.Incidente;
import modelo.Usuario;
import sistema.GestorIncidentes;
import sistema.GestorUsuarios;
import sistema.SistemaLog;
import java.util.Scanner;

/**
 * Clase: Menu
 * -----------
 * Punto de entrada del sistema. Muestra los menus en consola,
 * recibe la entrada del usuario y exige login antes de dejar
 * operar el sistema.
 */
public class Menu {

    // ARREGLO (requisito de arreglos): lista de prioridades validas.
    // Se usa para validar que el usuario escriba una prioridad correcta
    // al registrar un incidente, sin tener que repetir el if 3 veces.
    private static final String[] PRIORIDADES_VALIDAS = {"Alta", "Media", "Baja"};

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SistemaLog log = new SistemaLog();
        GestorIncidentes gestor = new GestorIncidentes();
        GestorUsuarios gestorUsuarios = new GestorUsuarios(log);

        // Usuarios de prueba (en un sistema real vendrian de almacenamiento persistente)
        gestorUsuarios.agregarUsuario(new Usuario("kevin", "1234", "admin"));
        gestorUsuarios.agregarUsuario(new Usuario("joan", "5678", "analista"));

        // ===== LOGIN =====
        Usuario usuarioActual = null;

        System.out.println("===== INICIO DE SESION =====");
        // Ciclo: se repite mientras no haya un login exitoso.
        // No limitamos los intentos aqui porque GestorUsuarios ya
        // bloquea la cuenta automaticamente despues de 3 fallos.
        while (usuarioActual == null) {
            System.out.print("Usuario: ");
            String nombreUsuario = sc.nextLine();
            System.out.print("Contrasena: ");
            String contrasena = sc.nextLine();

            usuarioActual = gestorUsuarios.login(nombreUsuario, contrasena);

            if (usuarioActual == null) {
                System.out.print("Desea intentar de nuevo? (s/n): ");
                String respuesta = sc.nextLine();
                if (respuesta.equalsIgnoreCase("n")) {
                    System.out.println("Cerrando sistema...");
                    sc.close();
                    return;
                }
            }
        }

        System.out.println("Bienvenido, " + usuarioActual.getNombreUsuario()
                + " (" + usuarioActual.getRol() + ")");

        // ===== MENU PRINCIPAL =====
        int opcion;

        do {
            System.out.println("\n===== SISTEMA DE INCIDENTES =====");
            System.out.println("1. Registrar incidente");
            System.out.println("2. Ver todos los incidentes");
            System.out.println("3. Actualizar estado de incidente");
            System.out.println("4. Ver estadisticas de acceso");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            if (opcion == 1) {
                System.out.print("Tipo de incidente: ");
                String tipo = sc.nextLine();
                System.out.print("Descripcion: ");
                String desc = sc.nextLine();

                // Ciclo + arreglo: valida la prioridad contra PRIORIDADES_VALIDAS
                // hasta que el usuario escriba una opcion correcta.
                String prio;
                boolean prioridadValida;
                do {
                    System.out.print("Prioridad (Alta/Media/Baja): ");
                    prio = sc.nextLine();
                    prioridadValida = false;
                    for (String p : PRIORIDADES_VALIDAS) {
                        if (p.equalsIgnoreCase(prio)) {
                            prioridadValida = true;
                            prio = p; // normaliza a la forma correcta (Alta/Media/Baja)
                        }
                    }
                    if (!prioridadValida) {
                        System.out.println("Prioridad invalida. Escriba Alta, Media o Baja.");
                    }
                } while (!prioridadValida);

                gestor.registrar(tipo, desc, prio, usuarioActual);
                System.out.println("Incidente registrado.");

            } else if (opcion == 2) {
                gestor.listar();

            } else if (opcion == 3) {
                System.out.print("ID del incidente a actualizar: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.print("Nuevo estado (En investigacion/Resuelto): ");
                String estado = sc.nextLine();
                gestor.actualizarEstado(id, estado, usuarioActual);

            } else if (opcion == 4) {
                gestorUsuarios.mostrarEstadisticas();

            } else if (opcion != 0) {
                System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);

        System.out.println("Cerrando sistema...");
        sc.close();

    } //FIN DE MAIN
}// FIN DE LA CLASS MENU
