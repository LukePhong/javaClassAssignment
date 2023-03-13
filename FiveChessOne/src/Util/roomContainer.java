package Util;

import Bean.GameInfo;
import Bean.Player;
import Bean.PlayerCondition;
import Bean.PlayerOnline;
import InstanceControl.InstanceManager;
import InstanceControl.InstanceMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class roomContainer extends JPanel {
    private JLabel roomName;
    private JButton function;
    private JPanel infoPanel;
    private JTextField player1,player2;

    public roomContainer(){
        setLayout(new BorderLayout());
        roomName=new JLabel("New Room");
        add(roomName,BorderLayout.NORTH);
        function=new JButton("ADD");
        function.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                switch(function.getText()) {
                    case "ADD":
                        String name = InstanceManager.getInstanceManager().assignRoomKey();
                        Player one = new Player(player1.getText());
                        Player two = new Player(player2.getText());
                        GameInfo gameInfo = new GameInfo(one, two);
                        gameInfo.setOwner(InstanceManager.getServerPort());
                        InstanceMapper.getInstance().setRoomBuffer(name, gameInfo);
                        roomName.setText(name);
                        function.setText("ADDING");
                        function.setEnabled(false);
                        break;
                    case "JOIN!":
                        String room = roomName.getText();
                        Player first = new Player(player1.getText());
                        Player second = new Player(player2.getText());
                        first.setCondition(PlayerCondition.ONLINE);
                        second.setCondition(PlayerCondition.ONLINE);
                        GameInfo gameInfo2=new GameInfo(first,second);
                        InstanceMapper.getInstance().setRoomBuffer(room,gameInfo2);
                        break;
                }
            }
        });
        add(function,BorderLayout.SOUTH);

        infoPanel=new JPanel(new GridLayout(2,2));
        infoPanel.add(new JLabel("Player1"));
        infoPanel.add(new JLabel("Player2"));
        player1=new JTextField();
        player2=new JTextField();
        infoPanel.add(player1);
        infoPanel.add(player2);
        add(infoPanel,BorderLayout.CENTER);

        setVisible(true);
    }

    public void setRoom(String name, GameInfo gameInfo){
        roomName.setText(name);
        player1.setText(gameInfo.getP1().getName());
        player2.setText(gameInfo.getP2().getName());
        if(gameInfo.getP1().getCondition()== PlayerCondition.VIRTUAL){
            player2.setBackground(Color.RED);
            function.setText("JOIN!");
        }else if(gameInfo.getP1().getCondition()== PlayerCondition.ONLINE){
            function.setText("JOIN!");
            function.setEnabled(false);
            if(gameInfo.getP2().getCondition()== PlayerCondition.ONLINE){
                function.setText("ON BATTLE");
                function.setEnabled(false);
            }
        }
    }
}
