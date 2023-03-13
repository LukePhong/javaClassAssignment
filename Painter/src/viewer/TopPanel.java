package viewer;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    public static shapeColor color;
    public static background background;
    public static textSetter textSetter;
    public static fillRegion fillRegion;

    TopPanel() {
        setLayout(new GridLayout(1,2));
        setPreferredSize(new Dimension(800, 50));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2,1));
        leftPanel.setPreferredSize(new Dimension(400, 23));
        leftPanel.setBackground(Color.WHITE);
        color = new shapeColor();
        leftPanel.add(color);
        background = new background();
        leftPanel.add(background);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2,1));
        rightPanel.setPreferredSize(new Dimension(400, 23));
        rightPanel.setBackground(Color.PINK);
        textSetter = new textSetter();
        rightPanel.add(textSetter);
        fillRegion = new fillRegion();
        rightPanel.add(fillRegion);

        this.add(leftPanel);
        this.add(rightPanel);
    }
}
