/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema;
import java.util.ArrayList;
import modelo.Incidente;
import modelo.Usuario;
/**
 * Clase: GestorIncidentes
 * -----------------------
 * Es el cerebro del sistema. Se encarga de registrar
 * nuevos incidentes, listarlos, buscar uno por su ID
 * y actualizar su estado de seguimiento.
 */
public class GestorIncidentes {
    // Lista que guarda todos los incidentes registrados
    private ArrayList<Incidente> listaIncidentes;

    // Contador para asignar IDs unicos y consecutivos
    private int contadorId;

    // Log del sistema para registrar cada accion
    private SistemaLog log;
    
     // Constructor
    public GestorIncidentes() {
        this.listaIncidentes = new ArrayList<>();
        this.contadorId      = 1;
        this.log             = new SistemaLog();
    }//FIN DEL CONSTRUCTOR 

    // Registra un nuevo incidente en el sistema
    public void registrar(String tipo, String descripcion,
                          String prioridad, Usuario reportadoPor) {
        Incidente nuevo = new Incidente(contadorId, tipo, descripcion, prioridad);
        listaIncidentes.add(nuevo);
        log.agregar("Incidente #" + contadorId + " registrado por "
                    + reportadoPor.getNombreUsuario());
        contadorId++;
    }//FIN DE REGISTRO

    // Muestra todos los incidentes registrados
    public void listar() {
        if (listaIncidentes.isEmpty()) {
            System.out.println("No hay incidentes registrados.");
            return;
        }
        System.out.println("\n===== LISTA DE INCIDENTES =====");
        for (Incidente i : listaIncidentes) {
            System.out.println(i.toString());
        }
    }//FIN DE MUESTRAS
    
    // Busca un incidente por su ID y lo retorna (null si no existe)
    public Incidente buscarPorId(int id) {
        for (Incidente i : listaIncidentes) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }//FIN DE BUSQUEDA Y RETORNO DE LA ID
    
    // Actualiza el estado de un incidente buscandolo por ID
    public void actualizarEstado(int id, String nuevoEstado, Usuario usuario) {
        Incidente encontrado = buscarPorId(id);
        if (encontrado != null) {
            encontrado.setEstado(nuevoEstado);
            log.agregar("Incidente #" + id + " actualizado a '"
                        + nuevoEstado + "' por " + usuario.getNombreUsuario());
            System.out.println("Estado actualizado correctamente.");
        } else {
            System.out.println("No se encontro el incidente #" + id);
        }
    }//FIN DE LA ACTUALIZACION

}//FIN DE CLASS
