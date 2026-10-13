
package gestor.modelo;

public class Tarea {

    
    private int idTarea;
    private int idAsignado; 
    private int idAsignador; 
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    
    
    private String nombreResponsable;
    private String nombreAsignador;   
    
    
    public Tarea() {
    }

    
    
    public int getIdTarea() { return idTarea; }
    public void setIdTarea(int idTarea) { this.idTarea = idTarea; }

    public int getIdAsignado() { return idAsignado; }
    public void setIdAsignado(int idAsigando) { this.idAsignado = idAsigando; }

    public int getIdAsignador() { return idAsignador; }
    public void setIdAsignador(int idAsignador) { this.idAsignador = idAsignador; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    
    // --- Getters y Setters para los campos del JOIN ---
    
    public String getNombreResponsable() { return nombreResponsable; }
    public void setNombreResponsable(String nombreResponsable) { this.nombreResponsable = nombreResponsable; }
    
    public String getNombreAsignador() { return nombreAsignador; }
    public void setNombreAsignador(String nombreAsignador) { this.nombreAsignador = nombreAsignador; }
}