package View;

import Bean.*;


import javax.swing.*;
import java.awt.*;

public class Viewer extends JFrame implements View {
    private static Viewer viewer;
    private static GameInfo gameInfo;

    public static Viewer getViewer() {
        if (viewer == null) {
            viewer = new Viewer();
            System.out.println("viewer");
        }
        return viewer;
    }

    public static void setGameInfo(GameInfo gameInfo) {
        Viewer.gameInfo = gameInfo;
    }

    private String key;


    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setChess(Chess chess) {
        chessBoard.setChess(chess.getRow(),chess.getCol(),chess.getCondition());
    }

    public void withdrawChess(Chess chess) {
        chessBoard.setChess(chess.getRow(),chess.getCol(),ChessCondition.EMPTY);
    }

    @Override
    public void setInfo(Player info) {

    }

    private BoardCondition boardCondition=BoardCondition.NOPE;
    private chessBoard chessBoard;
    private RightPanel rightPanel;
    private TimerPanel timerPanel;

    public void init(){

        chessBoard=new chessBoard();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(chessBoard,BorderLayout.CENTER);
        rightPanel=new RightPanel(gameInfo);
        RightPanel.setOnlineMode();
        add(rightPanel,BorderLayout.EAST);
        timerPanel=new TimerPanel();
        add(timerPanel,BorderLayout.WEST);
        this.setSize(800, 600);
        setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public chessBoard getChessBoard() {
        return chessBoard;
    }

    public void setBoardCondition(BoardCondition boardCondition) {
        this.boardCondition = boardCondition;
    }

    public InfoPanel getInfoPanel() {
        return rightPanel.getInfoPanel();
    }


    public ChessCondition selectSide() {
        //pop a message box let user choose black or white
        String[] options = {"black", "white"};
        int n = JOptionPane.showOptionDialog(null, "Choose your side", "Side",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (n == 0) {
            return ChessCondition.BLACK;
        } else {
            return ChessCondition.WHITE;
        }
    }


    public void showFinishBox(boolean isWinner) {

        String[] options = {"OK"};
        if (isWinner) {
            JOptionPane.showMessageDialog(null, "YOU WIN! Game Over");
        }   else {
            JOptionPane.showMessageDialog(null, "YOU LOSE! Game Over");
        }
    }

    public void setChatText(String text) {
        RightPanel.getChatPanel().setText(text);
    }

    public RightPanel getRightPanel() {
        return rightPanel;
    }
}
