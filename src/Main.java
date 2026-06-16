/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import modelo.Incidente;
import modelo.Usuario;
import sistema.GestorIncidentes;
import sistema.SistemaLog;
import java.util.Scanner;
/**
 * Clase: Main o Principal
 * -----------
 * Punto de entrada del sistema. Aqui se crean los objetos
 * principales y se prueba que todo funciona correctamente.
 * Por ahora crea usuarios e incidentes de prueba.
 */
public class Main {
     
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GestorIncidentes gestor = new GestorIncidentes();

        // Usuario de prueba por ahora
        Usuario kevin = new Usuario("kevin", "1234", "admin");

        int opcion;

        do {
            System.out.println("\n===== SISTEMA DE INCIDENTES =====");
            System.out.println("1. Registrar incidente");
            System.out.println("2. Ver todos los incidentes");
            System.out.println("3. Actualizar estado de incidente");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            if (opcion == 1) {
                System.out.print("Tipo de incidente: ");
                String tipo = sc.nextLine();
                System.out.print("Descripcion: ");
                String desc = sc.nextLine();
                System.out.print("Prioridad (Alta/Media/Baja): ");
                String prio = sc.nextLine();
                gestor.registrar(tipo, desc, prio, kevin);
                System.out.println("Incidente registrado.");

            } else if (opcion == 2) {
                gestor.listar();

            } else if (opcion == 3) {
                System.out.print("ID del incidente a actualizar: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.print("Nuevo estado (En investigacion/Resuelto): ");
                String estado = sc.nextLine();
                gestor.actualizarEstado(id, estado, kevin);

            } else if (opcion != 0) {
                System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);

        System.out.println("Cerrando sistema...");
        sc.close();

    } //FIN DE MAIN
}// FIN DE LA CLASS MAIN
