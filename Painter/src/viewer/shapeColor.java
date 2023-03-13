package viewer;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class shapeColor extends JPanel {
    public Color color=Color.RED;
    public JComboBox comboBox;

    class ComboBoxListener implements ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            //convert to Color
            String colorName = (String) comboBox.getSelectedItem();
            color = Color.decode(colorName);

        }
    }

    shapeColor(){
        setLayout(new GridLayout(1,2));
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(50, 50));

        JLabel label = new JLabel("Set Color");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        comboBox = new JComboBox();

        comboBox.addItem("Red");
        comboBox.addItem("Green");
        comboBox.addItem("Blue");
        comboBox.addActionListener(new ComboBoxListener());
        add(comboBox);
    }

    public Color getColor() {
        return color;
    }


}
