package InstanceControl;

import Bean.*;
import Controller.*;
import Map.*;
import Util.InstanceNet;
import View.Viewer;
import View.ViewerLocal;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class InstanceManager {

    private InstanceManager() {}

    private static RunningMode runningMode;

    private static InstanceManager im=new InstanceManager();
    private static InstanceMapper instanceMapper=InstanceMapper.getInstance();
    private static InstanceViewer instanceViewer=InstanceViewer.getInstance();
    private static InstanceNet net=InstanceNet.getNet();

    private static final String SERVER_IP="localhost";
    private static int SERVER_PORT=10000;
    private static final String LOCAL_IP="localhost";
    private static final int LOCAL_PORT=10001;
    private static final MutablePair<String,Integer> SERVER_ADDRESS=new MutablePair<>(SERVER_IP,SERVER_PORT);
    private static final ImmutablePair<String,Integer> LOCAL_ADDRESS=new ImmutablePair<>(LOCAL_IP,LOCAL_PORT);

    public static InstanceManager getInstanceManager() {
        if(im==null){
            im=new InstanceManager();
        }
        return im;
    }

    //assign the key string to controller accoring to instancemapper.instanceNum
    public String assignKey(){
        String key="Group"+instanceMapper.getInstanceNum();
        return key;
    }

    public Controller getController(String instanceName){
        return instanceMapper.getController(instanceName);
    }

    public Viewer getViewer(String instanceName){
        return instanceMapper.getViewer(instanceName);
    }

    public Mapper getMapper(String instanceName){
        return instanceMapper.getMapper(instanceName);
    }

    public String assignRoomKey(){
        StringBuilder key=new StringBuilder();
        key.append("Room");
        key.append(instanceMapper.getInstanceNum());
        key.append("@").append(LOCAL_IP);
        instanceMapper.incInstanceNum();
        return key.toString();
    }

    //set server port
    public void setServerPort(int port){
        System.out.println("setServerPort "+port);
        SERVER_PORT=port;
        SERVER_ADDRESS.setValue(SERVER_PORT);
    }

    public static int getServerPort() {
        return SERVER_PORT;
    }

    public String getRoomList(){
        Map<String, GameInfo> roomList=instanceMapper.getRoomList();
        StringBuilder sb=new StringBuilder();
        for(Map.Entry<String, GameInfo> entry:roomList.entrySet()){
            sb.append(entry.getKey()).append(" ").append(entry.getValue().getP1().toString()).append(" ").
                    append(entry.getValue().getP2().toString()).append("\n");
        }
        return sb.toString();
    }

    //reset roomlist in instancemapper
    public void setRoomList(String roomList){
        //parse roomlist to Map<String, GameInfo> rooms
        Map<String, GameInfo> rooms=new HashMap<String, GameInfo>();
        String[] roomListArray=roomList.split("\n");
        for(String room:roomListArray){
            String[] roomInfo=room.split(" ");
            String roomName=roomInfo[0];
            String p1=roomInfo[1];
            String p2=roomInfo[2];
            GameInfo gameInfo=new GameInfo(new Player(p1),new Player(p2));
            rooms.put(roomName,gameInfo);
        }
       instanceMapper.resetRoomList(rooms);
    }

    private void init(){
        if(net.checkRunningServer(SERVER_ADDRESS,LOCAL_ADDRESS)){
           runningMode=RunningMode.SLAVE;
           InstanceViewer.getInstance().setTile("Running as client");
           System.out.println("running mode: slave");
           net.putInstanceCondition(SERVER_ADDRESS, LOCAL_ADDRESS);//会更改服务上器和本地连接用的端口
           new Thread(()->{
               while(net.listenToRoomList(SERVER_ADDRESS, LOCAL_ADDRESS)){}//根据服务端回传 更新房间列表
           }).start();
        }else{
            runningMode=RunningMode.MASTER;
            System.out.println("running mode: master");
            InstanceViewer.getInstance().setTile("Running as server");
            instanceMapper.initClientList();

            //注意 以下均使用SERVER_ADDRESS
            new Thread(()->{
                while(net.serverRunning(SERVER_ADDRESS)){}
            }).start();

            new Thread(()->{
                while(net.listenToRoomUpdata(SERVER_ADDRESS)){}
            }).start();
        }
        instanceViewer.init();
    }

    public static void main(String[] args){

        im.init();

//        PlayerOnline player1=new PlayerOnline(),player2=new PlayerOnline();
//        player1.setName("555");
//        player2.setName("246");
//        player1.setCondition(PlayerCondition.ONLINE);
//        player2.setCondition(PlayerCondition.ONLINE);
//        int port1=9996,port2=9997;//分配对战用的端口
//        player1.setPort(port1);
//        player2.setPort(port2);
//        player1.setIp("localhost");
//        player2.setIp("localhost");
//        GameInfo newGameInfo = new GameInfo(player1,player2);

        //TODO:标志哪一边没解决
        //im.startBattle(newGameInfo);
    }

    public void startBattle(GameInfo newGameInfo) {
        System.out.println("start battle");
        System.out.println(newGameInfo.toString());
        System.out.println(((PlayerOnline)newGameInfo.getP1()).getPort()+" "+((PlayerOnline)newGameInfo.getP2()).getPort());
        new Thread(()->{

            Controller controller=Controller.getController();
            Viewer viewer=Viewer.getViewer();
            Mapper mapper=Mapper.getMapper();
            String key=InstanceManager.getInstanceManager().assignKey();
            InstanceMapper.addInstance(key, controller, viewer, mapper);

            controller.setKey(key);
            viewer.setKey(key);
            mapper.setKey(key);

            instanceMapper.incInstanceNum();

            System.out.println(SERVER_PORT);
            System.out.println(newGameInfo.getOwner());
            controller.setGameInfo(newGameInfo);
            if(newGameInfo.getOwner()==SERVER_PORT) {
                controller.setCurrentPlayer((PlayerOnline) newGameInfo.getP1());
            }else{
                controller.setCurrentPlayer((PlayerOnline) newGameInfo.getP2());
            }

            controller.start();
        }).start();
    }

    public void startBattleLocal() {
        System.out.println("start battle");

        new Thread(()->{

            ControllerLocal controller=ControllerLocal.getController();
            ViewerLocal viewer=ViewerLocal.getViewer();
            MapperLocal mapper= MapperLocal.getMapper();

            instanceMapper.incInstanceNum();

            controller.start();
        }).start();
    }

    public void startBattleReview(GameInfo newGameInfo, Vector<Chess> chesses){
        System.out.println("start battle");
        System.out.println(newGameInfo.toString());
        System.out.println(((PlayerOnline)newGameInfo.getP1()).getPort()+" "+((PlayerOnline)newGameInfo.getP2()).getPort());
        new Thread(()->{

            ControllerReview controller=ControllerReview.getController();
            Viewer viewer=Viewer.getViewer();
            Mapper mapper=Mapper.getMapper();
            String key=InstanceManager.getInstanceManager().assignKey();
            InstanceMapper.addInstance(key, controller, viewer, mapper);

            controller.setKey(key);
            viewer.setKey(key);
            mapper.setKey(key);

            instanceMapper.incInstanceNum();


            controller.setGameInfo(newGameInfo);
            controller.setChessVector(chesses);

            controller.start();
        }).start();
    }
}
