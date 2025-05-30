import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import views.Login;

/**
 *
 * @author HP
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        // 2) Now launch your UI on the EDT
        SwingUtilities.invokeLater(() -> {
            // e.g. show your login first
            JFrame frame = new Login();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
