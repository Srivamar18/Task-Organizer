package gestor.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre_usuario, nombre_completo, email, password, rol) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexiondb.getConnection(); // Llama a tu clase de conexión
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getNombreCompleto());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getPassword()); 
            pstmt.setString(5, usuario.getRol());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }
    
 
    public Usuario validarLoginInseguro(String email, String password) {
        
       
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        
        try (Connection conn = Conexiondb.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email); 
            pstmt.setString(2, password);     
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                 
                    Usuario usuarioLogueado = new Usuario();
                    usuarioLogueado.setIdUsuario(rs.getInt("id_usuario"));
                    usuarioLogueado.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuarioLogueado.setNombreCompleto(rs.getString("nombre_completo"));
                    usuarioLogueado.setEmail(rs.getString("email"));
                    usuarioLogueado.setRol(rs.getString("rol"));
                    
                    return usuarioLogueado; 
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al validar login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null; 
    }
    
    public List<Usuario> obtenerColaboradores() {
        List<Usuario> colaboradores = new ArrayList<>();
       
        String sql = "SELECT id_usuario, nombre_completo, email FROM usuarios WHERE rol = 'COLABORADOR'";
        
        try (Connection conn = Conexiondb.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario u = new Usuario(); 
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                
                //Mapea el email 
                u.setEmail(rs.getString("email")); 
                
                colaboradores.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener colaboradores: " + e.getMessage());
            e.printStackTrace();
        }
        return colaboradores;
    }


    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre_usuario, nombre_completo, email, rol FROM usuarios"; // No traemos la contraseña por seguridad

        try (Connection conn = Conexiondb.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setEmail(rs.getString("email"));
                usuario.setRol(rs.getString("rol"));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return listaUsuarios;
    }

    // Aquí irían los métodos para Actualizar (UPDATE) y Eliminar (DELETE)
    // public boolean actualizarUsuario(Usuario usuario) { ... }
    // public boolean eliminarUsuario(int idUsuario) { ... }
}