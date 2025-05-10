import javax.swing.JFrame;
import views.LoginView;

/**
 *
 * @author musa
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new LoginView();
        frame.setTitle("Login");
        frame.setVisible(true);
    }
}
