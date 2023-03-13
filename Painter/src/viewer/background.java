package viewer;

import javax.swing.*;
import java.awt.*;

public class background extends JPanel {
    background(){
        setLayout(new GridLayout(1,2));
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(50, 50));

        JLabel label = new JLabel("Background");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Red");
        comboBox.addItem("Green");
        comboBox.addItem("Blue");
        add(comboBox);
    }
}
