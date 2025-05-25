
import Interfaz.Interfaz;


public class Main {
    public static void main(String[] args) {
        // Iniciar la interfaz gráfica
        javax.swing.SwingUtilities.invokeLater(() -> {
            Interfaz interfaz = new Interfaz();
            interfaz.setVisible(true);
        });
    }
}