/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexiondb {
    private static final String URL = "jdbc:mysql://localhost:3306/gestor_proyectos_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "";
    // Driver: Ya no es estrictamente necesario cargarlo en Java moderno, 
    // pero se mantiene por compatibilidad y claridad.
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Establece y devuelve una nueva conexión a la base de datos.
     * @return Objeto Connection si la conexión es exitosa.
     * @throws SQLException Si ocurre un error de conexión o con el driver.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // 1. Cargar el Driver
            Class.forName(DRIVER); 
            
            // 2. Establecer la conexión y devolverla
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            // Se lanza si el JAR del conector no está bien incluido
            System.err.println("Error al cargar el Driver JDBC. ¿Se incluyó el .jar correctamente?");
            throw new SQLException("Falta el Driver JDBC: " + e.getMessage());
        }
    }
        public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
}
