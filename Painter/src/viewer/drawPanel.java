package viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.*;

import static java.lang.Math.abs;


public class drawPanel extends JPanel {
    public int x,x1,x2;
    public int y,y1,y2;
    private int width = 0;
    private int height = 0;
    private Graphics g;

    // Constructor
    public drawPanel() {
        super();
        setPreferredSize(new java.awt.Dimension(500, 500));
        setBackground(java.awt.Color.white);
        //setVisible(true);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                x = e.getX();
                y = e.getY();
                //repaint();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                repaint();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                repaint();
                //x1 = 0;
                //x2 = 0;
                //y1 = 0;
                //y2 = 0;
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
//                x1 = e.getX();
//                y1 = e.getY();
//                x2 = e.getX();
//                y2 = e.getY();
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                x = e.getX();
                y = e.getY();
                //repaint();
            }
        }
        );
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawLine(200, 200, 400, 400);
    }

    @Override
    public void repaint() {
        super.repaint();
        //drawLine(x1, y1, x2, y2);
    }

    //draw a line on the panel with the given coordinates
    public void drawLine(int x1, int y1, int x2, int y2) {
        Graphics g = getGraphics();
        g.drawLine(x1, y1, x2, y2);
    }


    public void drawRectangle(int x1, int y1, int x2, int y2,boolean fill, Color color) {
        Graphics g = getGraphics();
        if(fill) {
            g.setColor(color);
            g.fillRect(x1, y1, abs(x1-x2), abs(y1-y2));
        }else {
            g.drawRect(x1, y1, abs(x1 - x2), abs(y1 - y2));
        }

    }

    public void drawCircle(int x1, int y1, int x2, int y2,boolean fill, Color color) {
        Graphics g = getGraphics();
        if(fill){
            g.setColor(color);
            g.fillOval(x1, y1, abs(x1-x2), abs(y1-y2));
        }else {
            g.drawOval(x1, y1, abs(x1-x2), abs(y1-y2));
        }
    }

    public void drawTriangle(int x1, int y1, int x2, int y2,boolean fill, Color color) {
        Graphics g = getGraphics();
        int[] xPoints = {x1, x2, (x1+x2)/2};
        int[] yPoints = {y1, y2, (y1+y2)/2};
        if(fill){
            g.setColor(color);
            g.fillPolygon(xPoints, yPoints, 3);
        }else
        g.drawPolygon(xPoints, yPoints, 3);
    }

    public void drawRound(int x1, int y1, int x2, int y2,boolean fill, Color color) {
        Graphics g = getGraphics();
        if(fill) {
            g.setColor(color);
            g.fillRoundRect(x1, y1, abs(x1-x2), abs(y1-y2), abs(x1-x2), abs(y1-y2));
        }else
        g.drawRoundRect(x1, y1, abs(x1-x2), abs(y1-y2), 10, 10);
    }

    public void drawText(int x, int y,String text, Color color) {
        Graphics g = getGraphics();
        g.setColor(color);
        g.drawString(text, x, y);
    }
}
