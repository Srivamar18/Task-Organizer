package gestor.controlador;

import gestor.modelo.Tarea;
import gestor.modelo.TareaDAO;
import java.util.List;

public class TareaControlador {
    
    private TareaDAO tareaDAO;
    
    public TareaControlador() {
        this.tareaDAO = new TareaDAO();
    }
    
    // MÉTODO 1 Para el Admin
   
    public List<Tarea> getListaDeTareas() {
        return tareaDAO.obtenerTodasLasTareasConResponsable();
    }
    
    // MÉTODO 2 Para el Colaborador
    
    public List<Tarea> getListaDeTareasPorUsuario(int idUsuario) {
        return tareaDAO.obtenerTareasPorUsuario(idUsuario);
    }
    
    
    public boolean crearTarea(int idAsigando, int idAsignador, String titulo, String desc, String estado, String prio) {
        
        // 1. Crea el objeto Tarea
        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setIdAsignado(idAsigando);   
        nuevaTarea.setIdAsignador(idAsignador); 
        nuevaTarea.setTitulo(titulo);
        nuevaTarea.setDescripcion(desc);
        nuevaTarea.setEstado(estado);
        nuevaTarea.setPrioridad(prio);
        
        // 2. Llama al DAO para guardarlo
        return tareaDAO.insertarTarea(nuevaTarea);
    }
    
   
    public boolean eliminarTarea(int idTarea) {
        return tareaDAO.eliminarTarea(idTarea);
    }
}