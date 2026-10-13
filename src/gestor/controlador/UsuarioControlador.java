package gestor.controlador;

import gestor.modelo.Usuario;
import gestor.modelo.UsuarioDAO;
import java.util.List; 

public class UsuarioControlador {

    private UsuarioDAO usuarioDAO;

    // Constructor: inicializa el DAO
    public UsuarioControlador() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Este método será llamado desde la VISTA (fmrregistro)
     * Recibe los datos del formulario, los procesa y llama al DAO.
     * @return true si el registro fue exitoso, false si no.
     */
    public boolean registrarUsuario(String nombreUsuario, String nombreCompleto, String email, String password, String rol) {
        
        // Validaciones de Lógica de Negocio
        if (nombreUsuario.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.err.println("Datos incompletos.");
            return false;
        }

        
        String passwordCifrada = password;
        

        // Crear el objeto Modelo
        Usuario nuevoUsuario = new Usuario(nombreUsuario, nombreCompleto, email, passwordCifrada, rol);

        // Llamar al DAO para guardar
        return usuarioDAO.insertarUsuario(nuevoUsuario);
    }
    /**
     * Puente para la lógica de validación de login.
     * @param email El email del usuario.
     * @param password La contraseña del usuario.
     */
    public Usuario validarLogin(String email, String password) {
        return usuarioDAO.validarLoginInseguro(email, password);
    }
    
    
    public List<Usuario> getListaDeColaboradores() {
        return usuarioDAO.obtenerColaboradores();
    }
    
    
}