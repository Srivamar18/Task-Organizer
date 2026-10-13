package gestortareas; // En el paquete raíz

// Importa las clases que necesita
import gestor.vista.fmrlogin; 
import javax.swing.JFrame;

public class Main {

    /**
     * Este es el ÚNICO punto de entrada de TODA tu aplicación.
     */
    public static void main(String[] args) {

        // 1. CREA UNA VENTANA NUEVA
        JFrame ventanaPrincipal = new JFrame("Inicio de Sesión");

        // 2. CREA UNA INSTANCIA DE TU PANEL DE LOGIN (fmrlogin es un JPanel)
        fmrlogin panelLogin = new fmrlogin();

        // 3. AÑADE TU PANEL "DENTRO" DE LA VENTANA
        ventanaPrincipal.add(panelLogin);

        // 4. CONFIGURA LA VENTANA
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        ventanaPrincipal.pack(); // Ajusta el tamaño de la ventana al panel
        ventanaPrincipal.setLocationRelativeTo(null); // Centra la ventana

        // 5. ¡LA HACE VISIBLE!
        ventanaPrincipal.setVisible(true);
    }
}