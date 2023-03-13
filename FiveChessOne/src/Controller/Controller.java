package Controller;

import Bean.*;
import InstanceControl.InstanceManager;
import Map.AddressBattle;
import Map.Mapper;
import Map.MessageBuffer;
import Util.NetUDP;
import View.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class Controller {
    private static Controller controller;
    private static InstanceManager instanceManager=InstanceManager.getInstanceManager();
    private static Viewer viewer;
    private static Mapper mapper;
    private static NetUDP net;
    private static BoardCondition boardCondition=BoardCondition.NOPE;
    private static GameInfo gameInfo;
    private static PlayerOnline currentPlayer;
    private static PlayerOnline enemyPlayer;
    private static PutChess putChess=null;
    private static WaitForNext waitForNext=null;
    private static ChessCondition side = ChessCondition.EMPTY;
    private static PlayerOnline winner = null;
    private static CheckWin winChecker;
    private static Thread postThread = null;
    private static boolean threadKill = false;

    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public static void setGameInfo(GameInfo gameInfo) {
        Controller.gameInfo = gameInfo;

    }

    public static void setCurrentPlayer(PlayerOnline currentPlayer) {
        Controller.currentPlayer = currentPlayer;
        //decide which player is enemy player
        if(currentPlayer.getName().equals(gameInfo.getP1().getName())){
            enemyPlayer=(PlayerOnline) gameInfo.getP2();
        }else{
            enemyPlayer=(PlayerOnline) gameInfo.getP1();
        }
    }

    private static String key;

    public void setKey(String k) {
        key = k;
    }

    public static Viewer getViewer() {
        return viewer;
    }

    public static Mapper getMapper() {
        return mapper;
    }

    public void start(){
        viewer=instanceManager.getViewer(key);
        mapper=instanceManager.getMapper(key);
        viewer.setGameInfo(gameInfo);
        mapper.setGameInfo(gameInfo);
        net=NetUDP.getNet();
        winChecker=CheckWin.getInstance();

        viewer.init();
        viewer.setBoardCondition(boardCondition);

        AddressBattle.getInstance().setAddressLocal(currentPlayer.getIp(), currentPlayer.getPort());
        AddressBattle.getInstance().setAddressRemote(enemyPlayer.getIp(), enemyPlayer.getPort());
        if(currentPlayer.getName().equals(gameInfo.getP1().getName())){
            side = net.selectSideA(AddressBattle.getAddressLocal(), AddressBattle.getAddressRemote());
        }else{
            side = net.selectSideB(AddressBattle.getAddressLocal(), AddressBattle.getAddressRemote());
        }

        //side=ChessCondition.BLACK;

        System.out.println("side: "+side);

        postThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!threadKill&&net.poster(AddressBattle.getAddressLocal(), AddressBattle.getAddressRemote())){}
                System.out.println("poster exit");
            }
        });

        postThread.start();

        if(side == ChessCondition.WHITE) {
            boardCondition = BoardCondition.PLAY;
        }else{
            boardCondition = BoardCondition.NOPE;
        }

        //gameLoopStart();

    }

    private void gameLoopStart() {
        while(boardCondition!=BoardCondition.FINISH){
            //while(boardCondition==BoardCondition.WAIT){}//wait for next对方下棋了 立刻检查是否胜利
            //if(!winChecker.isWon()){

                //System.out.println("putting chess this side");
                PutChessNet.getPutChessNet().putChess();//我方下棋了 立刻检查
                //while(boardCondition==BoardCondition.PLAY){}//wait for local player
                //if(!winChecker.isWon()){

                    //System.out.println("waiting for next");
                    WaitForNextNet.getInstance().waitForNext();
                //}else {
                    //MessageBuffer.getInstance().setBattleMessage(new BattleMessage(Actions.NONE));
                    //System.out.println("win");
//                    winner=currentPlayer;
//                    MessageBuffer.getInstance().clear();
//                    boardCondition=BoardCondition.FINISH;
//                    this.notifyBoardPress(-1,-1);
//                    break;
               // }
            //}else{
                //MessageBuffer.getInstance().setBattleMessage(new BattleMessage(Actions.NONE));
//                System.out.println("someone won");
//                winner=enemyPlayer;
//                MessageBuffer.getInstance().clear();
//                boardCondition=BoardCondition.FINISH;
//                this.notifyBoardPress(-1,-1);
//                break;
           // }
        }
    }

    public void notifyBoardPress(int row, int col) {
        try {
            switch (boardCondition) {
                case NOPE:
                    break;
                case PLAY:
                    viewer.setChess(new Chess(row, col, side));
                    //System.out.println("black");
                    //net.putChessRemote(new Chess(row, col, ChessCondition.BLACK),  new PlayerOnline("2", ChessCondition.WHITE, "localhost", 10001));
                    MessageBuffer.getInstance().setBattleMessage(new BattleMessage(new Chess(row, col, side), Actions.NORM,""));
                    Mapper.addChess(new Chess(row, col, side));
                    boardCondition=BoardCondition.WAIT;
                    if(winChecker.isWon()){
                        winner=currentPlayer;
                        //MessageBuffer.getInstance().clear();
                        boardCondition=BoardCondition.FINISH;
                        //this.notifyBoardPress(-1,-1);
                        viewer.showFinishBox(true);

                        postThread.interrupt();
                    }
                    break;
                case FINISH:
                    //show a message box
                    viewer.showFinishBox(winner.equals(currentPlayer));
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void notifyRemote(BattleMessage battleMessage) {
        try {
            switch (battleMessage.getAction()) {
                case NORM:
                    if(boardCondition==BoardCondition.WAIT||boardCondition==BoardCondition.NOPE){
                        if (battleMessage.getChess().getCondition() != side) {
                            viewer.setChess(battleMessage.getChess());
                        }
                        boardCondition = BoardCondition.PLAY;
                        Mapper.addChess(battleMessage.getChess());
                        if (winChecker.isWon()) {
                            winner = enemyPlayer;
                            MessageBuffer.getInstance().clear();
                            boardCondition = BoardCondition.FINISH;
                            //this.notifyBoardPress(-1,-1);
                            viewer.showFinishBox(false);

                            postThread.interrupt();
                        }
                    }
                    break;
                case WITHDRAW:
                    if(battleMessage.getChess().getCondition()!=side) {
                        viewer.withdrawChess(battleMessage.getChess());
                    }
                    boardCondition=BoardCondition.PLAY;
                    Mapper.removeChess(battleMessage.getChess());
                    break;
                case QUIT:
                    boardCondition=BoardCondition.FINISH;
                    setWinner(currentPlayer);
                    viewer.showFinishBox(true);

                    threadKill=true;
                    break;
                case TEXT:
                    if(battleMessage.getChess().getCondition()!=side) {
                        viewer.setChatText(battleMessage.getText());
                    }
                    break;
                default:
                    //boardCondition=BoardCondition.PLAY;
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static PlayerOnline getCurrentPlayer() {
        return currentPlayer;
    }

    public static PlayerOnline getEnemyPlayer() {
        return enemyPlayer;
    }

    public void setBoardCondition(BoardCondition boardCondition) {
        this.boardCondition = boardCondition;
    }

    public static void setWinner(PlayerOnline winner) {
        Controller.winner = winner;
    }

    public static ChessCondition getSide() {
        return side;
    }

    public static BoardCondition getBoardCondition() {
        return boardCondition;
    }

    public static Thread getPostThread() {
        return postThread;
    }

    public static boolean isThreadKill() {
        return threadKill;
    }

    public static void setThreadKill(boolean threadKill) {
        Controller.threadKill = threadKill;
    }
}
