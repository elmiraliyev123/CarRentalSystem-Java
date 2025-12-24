package GUI;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {
    public AboutFrame() {
        setTitle("About");
        setSize(520, 240);
        setLocationRelativeTo(null);

        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setText(
                "CTIS 221 Term Project\n" +
                "Vehicle Rental System\n\n" +
                "Created by: \n " +
                "Elmir Aliyev\n" +
                "Berfin Çeri\n" +
                "Dilber Zelal Aras\n" 
               
        );

        add(new JScrollPane(ta), BorderLayout.CENTER);
    }
}
