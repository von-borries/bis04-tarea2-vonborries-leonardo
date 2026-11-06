package coopetica.presentacion;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new VentanaPrincipal().setVisible(true);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "No se pudo iniciar: el archivo de datos no es accesible.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
