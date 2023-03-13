package InstanceControl;

import Bean.Chess;
import Bean.GameInfo;
import Bean.PlayerOnline;
import Util.MusicPlayer;
import Util.RestoreGameFromFile;
import Util.roomContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.Vector;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class InstanceViewer extends JFrame {
    private InstanceViewer(){}

    private static InstanceViewer instanceViewer;

    public static InstanceViewer getInstance(){
        if(instanceViewer == null){
            instanceViewer = new InstanceViewer();
        }
        return instanceViewer;
    }

    public void updataRoomList() {
        int index=0;
        roomList.removeAll();
        roomList.setLayout(new GridLayout(3,3));
        for (Map.Entry<String, GameInfo> room : InstanceMapper.getInstance().getRoomList().entrySet()) {
            //roomList.getLayout().removeLayoutComponent(roomList.getComponent(index));
            roomContainer r=new  roomContainer();
            r.setRoom(room.getKey(),room.getValue());
            roomList.add(r,index);
            index++;
        }
        for(int i=index;i<9;i++){
            roomList.add(new roomContainer());
        }
        roomList.revalidate();
        repaint();
    }

    public void setTile(String condition) {
        setTitle(condition);
    }


    class roomList extends JPanel{
        public roomList(){
            setLayout(new GridLayout(3,3));
            for(int i = 0; i < 9; i++){
//                JButton button = new JButton("Room " + (i+1));
//                button.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        getTopLevelAncestor();
//
//                    }
//                });
//                add(button);
                add(new roomContainer());
            }
        }
    }


    private JPanel roomList = new roomList();
    private JButton reviewLocal = new JButton("本地复盘");
    private JButton playLocal = new JButton("本地游戏");
    private JButton music= new JButton("来点音乐");

    public void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(roomList, BorderLayout.CENTER);
        reviewLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open a file chooser
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("选择您要复盘的文件");
                fileChooser.setCurrentDirectory(new File("C:\\Users\\'Confidence'F\\Documents\\dev_Java\\FiveChessOne\\src\\Util"));
                int result = fileChooser.showOpenDialog(reviewLocal.getTopLevelAncestor());
                if (result == JFileChooser.APPROVE_OPTION) {
                    //open the file
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    System.out.println(filePath);
                    //read the file
                    Object[] objects = RestoreGameFromFile.readGameFromFile(filePath);
                    //parse the file
                    PlayerOnline playerOnline1 = (PlayerOnline) objects[0];
                    PlayerOnline playerOnline2 = (PlayerOnline) objects[1];
                    GameInfo gameInfo = new GameInfo(playerOnline1, playerOnline2);
                    Vector<Chess> chesses = (Vector<Chess>) objects[2];
                    //create a new game
                    InstanceManager.getInstanceManager().startBattleReview(gameInfo, chesses);
                    //start the game
                }
            }
        });
        add(reviewLocal, BorderLayout.SOUTH);
        playLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InstanceManager.getInstanceManager().startBattleLocal();
            }
        });
        add(playLocal, BorderLayout.NORTH);
        music.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //select mp3 file and play background
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("选择您要播放的音乐");
                fileChooser.setCurrentDirectory(new File("C:\\Users\\'Confidence'F\\Documents"));
                int result = fileChooser.showOpenDialog(music.getTopLevelAncestor());
                if (result == JFileChooser.APPROVE_OPTION) {
                    //open the file
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    System.out.println(filePath);

                    new MusicPlayer(filePath);
                }
            }
        });
        add(music, BorderLayout.WEST);
        setVisible(true);

    }
}
