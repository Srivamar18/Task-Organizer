package gestor.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TareaDAO {

    
    public List<Tarea> obtenerTodasLasTareasConResponsable() {
        List<Tarea> listaTareas = new ArrayList<>();
        
        
        // u_resp = Usuario Responsable (Colaborador)
        // u_admin = Usuario Asignador (Administrador)
        String sql = "SELECT " +
                     "    t.id_tarea, t.titulo, t.descripcion, t.estado, t.prioridad, " +
                     "    u_resp.nombre_completo AS nombre_responsable, " + // Colaborador
                     "    u_admin.nombre_completo AS nombre_asignador " +   // Administrador
                     "FROM tareas t " + // Tu tabla se llama 'tareas' (plural)
                     // 1er JOIN: Tarea -> Usuarios (Colaborador)
                     "LEFT JOIN usuarios u_resp ON t.id_asignado = u_resp.id_usuario " +
                     // 2do JOIN: Tarea -> Usuarios (Admin)
                     "LEFT JOIN usuarios u_admin ON t.id_asignador = u_admin.id_usuario";
                     
        try (Connection conn = Conexiondb.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Tarea tarea = new Tarea();
                
                // Mapea los datos de la tarea
                tarea.setIdTarea(rs.getInt("id_tarea")); 
                tarea.setTitulo(rs.getString("titulo")); 
                tarea.setDescripcion(rs.getString("descripcion")); 
                tarea.setEstado(rs.getString("estado"));
                tarea.setPrioridad(rs.getString("prioridad"));
                
                // Mapea los NOMBRES
                tarea.setNombreResponsable(rs.getString("nombre_responsable"));
                tarea.setNombreAsignador(rs.getString("nombre_asignador"));
                
                listaTareas.add(tarea);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener tareas (Admin JOIN): " + e.getMessage());
            e.printStackTrace(); // Añadido para más detalle
        }
        return listaTareas;
    }

    /**
     * R - READ (Colaborador): Obtiene SÓLO las tareas de un usuario.
     * ¡VERSIÓN SIMPLIFICADA (sin 'proyectos')!
     */
    public List<Tarea> obtenerTareasPorUsuario(int idUsuario) {
        List<Tarea> listaTareas = new ArrayList<>();
        
        // --- La misma consulta, pero con un WHERE ---
        String sql = "SELECT " +
                     "    t.id_tarea, t.titulo, t.descripcion, t.estado, t.prioridad, " +
                     "    u_resp.nombre_completo AS nombre_responsable, " + // Colaborador
                     "    u_admin.nombre_completo AS nombre_asignador " +   // Administrador
                     "FROM tareas t " + // Tu tabla se llama 'tareas' (plural)
                     "LEFT JOIN usuarios u_resp ON t.id_asignado = u_resp.id_usuario " +
                     "LEFT JOIN usuarios u_admin ON t.id_asignador = u_admin.id_usuario " +
                     "WHERE t.id_asignado = ?"; // El filtro de Colaborador

        try (Connection conn = Conexiondb.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario); // Pasamos el ID del usuario logueado
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Tarea tarea = new Tarea();
                    // Mapea los datos de la tarea
                    tarea.setIdTarea(rs.getInt("id_tarea")); 
                    tarea.setTitulo(rs.getString("titulo")); 
                    tarea.setDescripcion(rs.getString("descripcion")); 
                    tarea.setEstado(rs.getString("estado"));
                    tarea.setPrioridad(rs.getString("prioridad"));
                    
                    // Mapea los NOMBRES
                    tarea.setNombreResponsable(rs.getString("nombre_responsable"));
                    tarea.setNombreAsignador(rs.getString("nombre_asignador"));
                    
                    listaTareas.add(tarea);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener tareas por usuario (JOIN): " + e.getMessage());
            e.printStackTrace(); // Añadido para más detalle
        }
        return listaTareas;
    }
   

    /**
     * C - CREATE: Inserta una nueva tarea en la base de datos.
     * @param tarea El objeto Tarea a insertar.
     * @return true si la inserción fue exitosa.
     */
    public boolean insertarTarea(Tarea tarea) {
        
        
        String sql = "INSERT INTO tareas (id_asignado, id_asignador, titulo, descripcion, estado, prioridad) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexiondb.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tarea.getIdAsignado());   // ID del Colaborador
            pstmt.setInt(2, tarea.getIdAsignador());  // ID del Admin (quien la crea)
            pstmt.setString(3, tarea.getTitulo());
            pstmt.setString(4, tarea.getDescripcion());
            pstmt.setString(5, tarea.getEstado());
            pstmt.setString(6, tarea.getPrioridad());
            
            // executeUpdate() devuelve el número de filas afectadas
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar tarea: " + e.getMessage());
            e.printStackTrace(); // Para ver el error completo
            return false;
        }
    }
    
    /**
     * D - DELETE: Elimina una tarea de la base de datos usando su ID.
     * @param idTarea El ID de la tarea a eliminar.
     * @return true si la eliminación fue exitosa.
     */
    public boolean eliminarTarea(int idTarea) {
        // (Asegúrate que tu tabla se llame 'tareas')
        String sql = "DELETE FROM tareas WHERE id_tarea = ?";
        
        try (Connection conn = Conexiondb.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idTarea);
            
            // executeUpdate() devuelve el número de filas afectadas
            return pstmt.executeUpdate() > 0; // Devuelve true si 1 fila fue eliminada

        } catch (SQLException e) {
            System.err.println("Error al eliminar tarea: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
} // <-- Esta es la última llave de tu clase TareaDAO
