package View;

import Bean.ChessCondition;
import Bean.GameInfo;
import Bean.PlayerOnline;
import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private static GameInfo gameInfo;
    private static JLabel sideLabel1,sideLabel2;

    public static void setGameInfo(GameInfo gameInfo) {
        InfoPanel.gameInfo = gameInfo;
    }

    public InfoPanel(GameInfo gameInfo) {
        InfoPanel.gameInfo = gameInfo;

        setLayout(new GridLayout(1,2));
        JPanel p1,p2;
        p1 = new JPanel();
        p2 = new JPanel();
        p1.setLayout(new GridLayout(4,1));
        p2.setLayout(new GridLayout(4,1));
        p1.add(new JLabel("Player 1"));
        p1.add(new JLabel(gameInfo.getP1().getName()));
        p1.add(new JLabel(((PlayerOnline)gameInfo.getP1()).getPort()+"@"+((PlayerOnline)gameInfo.getP1()).getIp()));
        sideLabel1 = new JLabel("Waiting");
        p1.add(sideLabel1);
        p2.add(new JLabel("Player 2"));
        p2.add(new JLabel(gameInfo.getP2().getName()));
        p2.add(new JLabel(((PlayerOnline)gameInfo.getP2()).getPort()+"@"+((PlayerOnline)gameInfo.getP2()).getIp()));
        sideLabel2 = new JLabel("Waiting");
        p2.add(sideLabel2);
        add(p1);
        add(p2);
        setVisible(true);
    }

    public void setEnemySide(ChessCondition chose) {
        PlayerOnline p= Controller.getEnemyPlayer();
        if(p.getName().equals(gameInfo.getP1().getName())){
            sideLabel1.setText(chose.toString());
        }else{
            sideLabel2.setText(chose.toString());
        }
    }

    public void setCurrSide(ChessCondition chose) {
        if(gameInfo.getP1().getName().equals(Controller.getCurrentPlayer().getName())){
            sideLabel1.setText(chose.toString());
        }else{
            sideLabel2.setText(chose.toString());
        }
    }
}
