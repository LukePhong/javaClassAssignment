package View;

import Bean.*;
import Controller.*;
import Map.Mapper;
import Map.MessageBuffer;
import Util.SaveGameToFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JButton withdrawButton;
    private JButton quitButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton saveToFile;

    public ControlPanel() {
        this.setLayout(new GridLayout(2,2));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(java.awt.event.ActionEvent e) {
               if(Mapper.getChesses().size() == 0) {
                   return;
               }
               Chess toSend=null;
               for(int i=Mapper.getChesses().size()-1;i>=0;i--){
                   if(Mapper.getChesses().get(i).getCondition().equals(Controller.getSide())){
                       toSend=Mapper.getChesses().get(i);
                       Mapper.getChesses().remove(i);
                       Viewer.getViewer().withdrawChess(toSend);
                       break;
                   }
               }
               MessageBuffer.getInstance().setBattleMessage(new BattleMessage(toSend,Actions.WITHDRAW,""));
           }
        });
        this.add(this.withdrawButton);

        this.quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(java.awt.event.ActionEvent e) {
               MessageBuffer.getInstance().setBattleMessage(new BattleMessage(Actions.QUIT));
               Controller.setWinner(Controller.getEnemyPlayer());
               Controller.getController().setBoardCondition(BoardCondition.FINISH);
               Controller.getController().notifyBoardPress(-1,-1);

               Controller.setThreadKill(true);
           }
        });
        this.add(this.quitButton);

        this.saveToFile = new JButton("Save to File");
        saveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                SaveGameToFile.getInstance().saveGame(Controller.getCurrentPlayer(),Controller.getEnemyPlayer(),Mapper.getChesses());
            }
        });
        this.add(this.saveToFile);

        this.previousButton = new JButton("Previous");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(Mapper.getChesses().size() == 0) {
                    return;
                }else{
                    Chess removed =Mapper.getChesses().remove(Mapper.getChesses().size()-1);
                    ControllerReview.getChessVector().add(removed);
                    Viewer.getViewer().withdrawChess(removed);
                }
            }
        });
        this.add(this.previousButton);

        this.nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(ControllerReview.getChessVector().size() == 0) {
                    return;
                }else {
                    Chess toSet=ControllerReview.getChessVector().remove(ControllerReview.getChessVector().size()-1);
                    Mapper.getChesses().add(toSet);
                    Viewer.getViewer().setChess(toSet);
                }
            }
        });
        this.add(this.nextButton);



    }

    public void setOnlineMode(){
        this.withdrawButton.setEnabled(true);
        this.quitButton.setEnabled(true);
        this.previousButton.setEnabled(false);
        this.nextButton.setEnabled(false);
    }

    public void setOfflineMode() {
        this.withdrawButton.setEnabled(false);
        this.quitButton.setEnabled(false);
        this.saveToFile.setEnabled(false);
        this.previousButton.setEnabled(true);
        this.nextButton.setEnabled(true);

    }
}
