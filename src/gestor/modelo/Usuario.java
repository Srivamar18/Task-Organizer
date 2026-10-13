package gestor.modelo;

public class Usuario {
    
    private int idUsuario;
    private String nombreUsuario;
    private String nombreCompleto;
    private String email; // ¡Nuevo campo añadido!
    private String password;
    private String rol; // "ADMINISTRADOR" o "COLABORADOR"

    // Constructor vacío (siempre recomendado)
    public Usuario() {
    }

    // Constructor con campos (útil para crear o cargar datos)
    public Usuario(int idUsuario, String nombreUsuario, String nombreCompleto, String email, String password, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }
    
    /**
     * Constructor para insertar un nuevo usuario.
     * El idUsuario no se incluye porque es autoincremental en la BD.
     */
    public Usuario(String nombreUsuario, String nombreCompleto, String email, String password, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }
    
    // --- MÉTODOS GETTER Y SETTER ---
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    
    public String getEmail() { return email; } // Getter y Setter para el nuevo campo
    public void setEmail(String email) { this.email = email; } 
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}