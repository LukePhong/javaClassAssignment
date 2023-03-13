package viewer;

import javax.swing.*;
import java.awt.*;

public class textSetter extends JPanel {


    textSetter(){
        setLayout(new GridLayout(1,3));

        JTextField textField = new JTextField();
        textField.setText("Enter text here");
        add(textField);

        JLabel label = new JLabel("size:");
        add(label);

        JTextField fontSize = new JTextField("14");
        fontSize.add(new JScrollBar(JScrollBar.HORIZONTAL));

        add(fontSize);
    }
}
