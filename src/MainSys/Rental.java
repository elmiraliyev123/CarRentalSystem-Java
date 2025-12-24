package MainSys;

import javax.swing.SwingUtilities;
import GUI.MainFrame;

public class Rental {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
