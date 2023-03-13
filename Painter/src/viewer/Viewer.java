package viewer;

import javax.swing.*;
import java.awt.*;

public class Viewer {
    private JFrame mainFrame;
    private TopPanel topPanel;
    private leftControlBar leftControlBar;
    //private buttonSet buttonSet;
    private drawPanel drawPanel;

    public Viewer(JFrame main) {
        mainFrame = main;
    }

    public void update() {

        switch(buttonSet.selected.getText()){
            case "Line":
                System.out.println("draw Line");
                drawPanel.drawLine(drawPanel.x1,drawPanel.y1, drawPanel.x2,drawPanel.y2);
                break;
                case "Rectangle":
                    System.out.println("draw Rectangle");
                drawPanel.drawRectangle(drawPanel.x1,drawPanel.y1, drawPanel.x2,drawPanel.y2,topPanel.fillRegion.isChecked,topPanel.color.getColor());
                break;
                case "Oval":
                drawPanel.drawCircle(drawPanel.x1,drawPanel.y1, drawPanel.x2,drawPanel.y2,topPanel.fillRegion.isChecked,topPanel.color.getColor());
                break;
                case "Triangle":
                drawPanel.drawTriangle(drawPanel.x1,drawPanel.y1, drawPanel.x2,drawPanel.y2,topPanel.fillRegion.isChecked,topPanel.color.getColor());
                break;
            case"Round":
                drawPanel.drawRound(drawPanel.x1,drawPanel.y1, drawPanel.x2,drawPanel.y2,topPanel.fillRegion.isChecked,topPanel.color.getColor());
                break;
            default:
        }

        //mainFrame.repaint();
    }

    public void init() {
        mainFrame.setTitle("Painter");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setBackground(new java.awt.Color(255, 255, 255));
        mainFrame.setLayout(new BorderLayout());

        topPanel = new TopPanel();
        mainFrame.add(topPanel, BorderLayout.NORTH);

        leftControlBar = new viewer.leftControlBar();
        mainFrame.add(leftControlBar, BorderLayout.WEST);

        drawPanel = new drawPanel();
        mainFrame.add(drawPanel, BorderLayout.CENTER);


    }

    public void show() {
        mainFrame.setVisible(true);
    }
}
