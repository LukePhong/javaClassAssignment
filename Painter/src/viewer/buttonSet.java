package viewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


class triangularButton extends JButton {
    triangularButton() {
        super("Triangle");
        setPreferredSize(new java.awt.Dimension(100, 80));
        setContentAreaFilled(true);
        setVisible(true);
        //addActionListener(new Actiona);
    }
}

class rectangularButton extends JButton {
    rectangularButton(){
        super("Rectangle");
        setPreferredSize(new java.awt.Dimension(100, 80));
        setContentAreaFilled(true);
        setVisible(true);
    }
}

class ovalButton extends JButton {
    ovalButton(){
        super("Oval");
        setPreferredSize(new java.awt.Dimension(100, 80));
        setContentAreaFilled(true);
        setVisible(true);
    }
}

class roundButton extends JButton {
    roundButton(){
        super("Round");
        setPreferredSize(new java.awt.Dimension(100, 80));
        setContentAreaFilled(true);
        setVisible(true);
    }
}

class lineButton extends JButton {
    lineButton(){
        super("Line");
        setPreferredSize(new java.awt.Dimension(100, 80));
        setContentAreaFilled(true);
        setVisible(true);
    }
}



public class buttonSet extends JButton {
    public static Vector<JButton> buttons;
    public static JButton selected=new lineButton();

    class buttonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selected = (JButton) e.getSource();
            //System.out.println("Selected: " + selected.getText());
        }
    }

    public buttonSet() {
        buttons = new Vector<JButton>();
        buttons.add(new triangularButton());
        buttons.add(new rectangularButton());
        buttons.add(new ovalButton());
        buttons.add(new roundButton());
        buttons.add(new lineButton());

        for (JButton b : buttons) {
            b.addActionListener(new buttonListener());
        }
    }
}




