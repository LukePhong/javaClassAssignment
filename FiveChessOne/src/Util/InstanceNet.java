package Util;

import Bean.GameInfo;
import Bean.PlayerCondition;
import Bean.PlayerOnline;
import InstanceControl.InstanceManager;
import InstanceControl.InstanceMapper;
import InstanceControl.InstanceViewer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

import static java.lang.Thread.sleep;

public class InstanceNet {
    private static InstanceNet net;

    public static InstanceNet getNet() {
        if(net == null){
            net = new InstanceNet();
        }
        return net;
    }

    public boolean checkRunningServer(MutablePair<String, Integer> serverAddress, ImmutablePair<String, Integer> localAddress){
        boolean result = false;

        DatagramSocket socket = null;
        try{
            InetAddress address = InetAddress.getByName(serverAddress.getLeft());
            String message = "checkRunningServer";
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, serverAddress.getRight());
            socket=new DatagramSocket(localAddress.getRight());
            socket.send(packet);

            byte[] data2 = new byte[1024];
            DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
            System.out.println("checkRunningServer");
            socket.setSoTimeout(1000);
            socket.receive(packet2);
            String reply = new String(data2, 0,packet2.getLength());
            if(reply.equals("running")){
                result = true;
            }
            socket.close();
        }catch (Exception e) {
            result = false;
            e.printStackTrace();
        }finally{
            socket.close();
        }

        return result;
    }

    public void putInstanceCondition(MutablePair<String, Integer> serverAddress, ImmutablePair<String, Integer> localAddress) {
        //tell server the port & id of local instance
        DatagramSocket socket = null;
        try {//对应 客户端向服务器发送数据
            InetAddress address = InetAddress.getByName(serverAddress.getLeft());
            String message = "putInstanceCondition:" + localAddress.getLeft() + ":" + localAddress.getRight();
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, serverAddress.getRight());
            socket = new DatagramSocket(localAddress.getRight());//10001->10000
            socket.send(packet);

            //对应从服务端接收数据
            byte[] data2 = new byte[1024];
            DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
            // 2.接收服务器响应的数据
            socket.receive(packet2);
            // 3.读取数据
            String reply = new String(data2, 0, packet2.getLength());
            System.out.println(reply);
            //parse string"port: " get the new port
            int port;
            if(reply.substring(0, 6).equals("port: ")) {
                port = Integer.parseInt(reply.substring(reply.indexOf(":") + 2));
            }else{
                port = -1;
            }
            InstanceManager.getInstanceManager().setServerPort(port);//更改了和服务器的连接端口
            // 4.关闭资源
            socket.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            socket.close();
        }
    }

    public boolean listenToRoomList(MutablePair<String, Integer> serverAddress, ImmutablePair<String, Integer> localAddress) {
        DatagramSocket socket = null;

        try{
            //
            InetAddress address = InetAddress.getByName(serverAddress.getLeft());
            socket = new DatagramSocket(serverAddress.getRight()+1);
            socket.setSoTimeout(1000);
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            //System.out.println("Waiting for Room Update");
            try {
                socket.receive(packet);
            }catch (Exception e){
                System.out.println("No Room Update");
                packet=null;
            }
            //确实有收到消息
            InstanceMapper mapper= InstanceMapper.getInstance();
            ByteArrayOutputStream byteArrayOutStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutStream = new ObjectOutputStream(byteArrayOutStream);
            if(packet!=null) {
                System.out.println("Received Room Update");

                ByteArrayInputStream byteArrayInStream = new ByteArrayInputStream(data);
                ObjectInputStream objectInStream = new ObjectInputStream(byteArrayInStream);
                Object obj = objectInStream.readObject();
                Pair<String, GameInfo> recieved= (Pair<String, GameInfo>)obj;
                String rooms = recieved.getLeft();
                GameInfo gameInfo=recieved.getRight();

                mapper.addRoom(rooms,gameInfo);//记录或更新房间信息
                InstanceViewer.getInstance().updataRoomList();
                //发来纯粹新房间信息
                if (gameInfo.getP2().getCondition()== PlayerCondition.ONLINE) {//发来对战信息
                    System.out.println("ONLINE");
                    InstanceManager.getInstanceManager().startBattle(gameInfo);

                }

                objectInStream.close();
                byteArrayInStream.close();
            }

            //buffer不为空，发送buffer中的消息
            if(mapper.getRoomBuffer().getLeft()!=null){
                System.out.println("Send from Buffer");

                String rooms = mapper.getRoomBuffer().getLeft();
                GameInfo gameInfo = mapper.getRoomBuffer().getRight();
                objectOutStream.writeObject(new MutablePair<String,GameInfo>(rooms,gameInfo));
                byte[] data2= byteArrayOutStream.toByteArray();
                DatagramPacket packet2 = new DatagramPacket(data2,data2.length,InetAddress.getByName(serverAddress.getLeft()),serverAddress.getRight());
                socket.send(packet2);
                objectOutStream.reset();
                mapper.clearRoomBuffer();//清除buffer

            }

            objectOutStream.close();
            byteArrayOutStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if(socket!=null) socket.close();
        }
        return true;
    }

    public boolean serverRunning(MutablePair<String, Integer> serverAddress) {

        DatagramSocket socket = null;
        try{
            //recieve message from clients
            socket = new DatagramSocket(serverAddress.getRight());
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            //System.out.println("waiting for client");
            socket.setSoTimeout(1000);
            socket.receive(packet);
            String reply = new String(data, 0, packet.getLength());
            System.out.println(reply);
            InetAddress address= packet.getAddress();
            int portSend = packet.getPort();
            if(reply.equals("checkRunningServer")){
                System.out.println("reply server running");

                String message = "running";
                byte[] data2 = message.getBytes();
                DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, portSend);
                socket.send(packet2);
                socket.close();
            }else if(reply.substring(0,reply.indexOf(":")).equals("putInstanceCondition")){
                String ip= reply.substring(reply.indexOf(":") + 1);//, reply.indexOf(":") + 1 + 15);
                ip=ip.substring(0,ip.indexOf(":"));
                System.out.println(ip);
                //assign port
                int port;
                for(port=10002;port<=65535;port+=2){
                    boolean isPortAvailable = true;
                    for(Object o:InstanceMapper.getInstance().getRemoteList()){
                        if(((ImmutablePair<String,Integer>)o).getValue()==port){
                            isPortAvailable = false;
                            break;
                        }
                    }
                    if(isPortAvailable){
                        break;
                    }
                }
                System.out.println(port);
                ImmutablePair<String,Integer> clienAddress = new ImmutablePair<>(ip, port);
                InstanceMapper.getInstance().addClient(clienAddress);
                String message = "port: "+port;
                System.out.println(message);
                byte[] data2 = message.getBytes();
                DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, portSend);
                socket.send(packet2);
                socket.close();
            }

        }catch (Exception e){
            //e.printStackTrace();
        }finally{
            socket.close();
        }

        return true;
    }

    public boolean listenToRoomUpdata(MutablePair<String, Integer> serverAddress) {
        DatagramSocket socket = null;
        InstanceMapper mapper= InstanceMapper.getInstance();
        try{
            //
            InetAddress address = InetAddress.getByName(serverAddress.getLeft());
//            socket = new DatagramSocket();
//            socket.setSoTimeout(50);
            byte[] data = new byte[1024];
            DatagramPacket packet = null;
            //System.out.println("Waiting for Room Update");

            //listen to every client
            for(Object o:mapper.getRemoteList()) {
                packet=new DatagramPacket(data,data.length);
                InetAddress address2 = InetAddress.getByName(((ImmutablePair<String, Integer>) o).getLeft());
                int tmpPort = ((ImmutablePair<String, Integer>) o).getRight();
                System.out.println(address2.getHostAddress() + ":" + tmpPort);
                try {
                    socket = new DatagramSocket(tmpPort);
                    socket.setSoTimeout(500);
                    socket.receive(packet);
                } catch (Exception e) {
                    //e.printStackTrace();
                    packet = null;
                }finally{
                    socket.close();
                }
                if(packet!=null) {
                    break;
                }
            }
            //sleep(100);

            socket = new DatagramSocket(serverAddress.getRight()+100);//10100
//            if(packet==null) {
//                System.out.println("No Room Update");
//            }
            //确实有收到消息
            ByteArrayOutputStream byteArrayOutStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutStream = new ObjectOutputStream(byteArrayOutStream);

            if(packet!=null) {
                //System.out.println("Received Room Update");

                ByteArrayInputStream byteArrayInStream = new ByteArrayInputStream(data);
                ObjectInputStream objectInStream = new ObjectInputStream(byteArrayInStream);
                Object obj = objectInStream.readObject();
                MutablePair<String, GameInfo> recieved= (MutablePair<String, GameInfo>)obj;
                String rooms = recieved.getLeft();
                GameInfo gameInfo=recieved.getRight();
                if(gameInfo.getIndex()==-1)
                    gameInfo.setIndex(mapper.getRoomList().size()+1);//分配房间号
                mapper.addRoom(rooms,gameInfo);//记录或更新房间信息
                InstanceViewer.getInstance().updataRoomList();
                //发来纯粹新房间信息
                if (gameInfo.getP2().getCondition()== PlayerCondition.VIRTUAL) {

                    System.out.println("VIRTUAL");
                    if(mapper.getPortRoomMap().containsKey(packet.getPort())){//更新对应表
                        mapper.getPortRoomMap().get(packet.getPort()).add(gameInfo.getIndex());
                    }else{
                        Vector<Integer> list = new Vector<>();
                        list.add(gameInfo.getIndex());
                        mapper.getPortRoomMap().put(packet.getPort(), list);
                    }

                }else if (gameInfo.getP2().getCondition()== PlayerCondition.ONLINE){//发来对战信息

                    System.out.println("ONLINE");
                    PlayerOnline player1=new PlayerOnline(),player2=new PlayerOnline();
                    player1.setName(gameInfo.getP1().getName());
                    player2.setName(gameInfo.getP2().getName());
                    player1.setCondition(PlayerCondition.ONLINE);
                    player2.setCondition(PlayerCondition.ONLINE);
                    int port1= 10000-2*gameInfo.getIndex(),port2=10000-2*gameInfo.getIndex()+1;//分配对战用的端口
                    player1.setPort(port1);
                    player2.setPort(port2);
                    Object obasePort=mapper.getPortFromRoomNumber(gameInfo.getIndex());
                    int basePort=obasePort==null?-1: (Integer) obasePort;
                    Object obaseIp=mapper.getClientFromPort(basePort);
                    String baseIp=obaseIp==null?null:(String)obaseIp;
                    player1.setIp(baseIp);
                    String ip2=packet.getAddress().toString().substring(1);
                    player2.setIp(ip2);
                    GameInfo newGameInfo = new GameInfo(player1,player2);
                    newGameInfo.setOwner(InstanceManager.getServerPort());

                    //反送给对战加入者
                    objectOutStream.writeObject(new MutablePair<>(rooms,newGameInfo));
                    byte[] data2 = byteArrayOutStream.toByteArray();
                    DatagramPacket packet2 = new DatagramPacket(data2, data2.length, packet.getAddress(), packet.getPort());
                    socket.send(packet2);
                    if(baseIp!=null){//房间不属于本地
                        //send newGameInfo to baseIp and sender of package
                        DatagramPacket packet3 = new DatagramPacket(data2, data2.length, InetAddress.getByName(baseIp), basePort);
                        socket.send(packet2);
                    }else {
                        InstanceManager.getInstanceManager().startBattle(newGameInfo);
                    }
                    objectOutStream.reset();
                }
                //向每一个slave端发送刚刚收到的房间信息
                for(Object o:mapper.getRemoteList()){
                    ImmutablePair<String,Integer> clientAddress = (ImmutablePair<String,Integer>)o;
                    objectOutStream.writeObject(o);
                    byte[] data2 = byteArrayOutStream.toByteArray();
                    objectOutStream.reset();
                    DatagramPacket packet2 = new DatagramPacket(data2, data2.length, InetAddress.getByName(clientAddress.getLeft()), clientAddress.getRight()+1);
                    socket.send(packet2);
                }

                objectInStream.close();
                byteArrayInStream.close();
            }

            //buffer不为空，发送buffer中的消息
            if(mapper.getRoomBuffer().getLeft()!=null){
                System.out.println("Send from Buffer");

                String rooms = mapper.getRoomBuffer().getLeft();
                GameInfo gameInfo = mapper.getRoomBuffer().getRight();
                gameInfo.setIndex(mapper.getRoomList().size()+1);//分配房间号
                mapper.addRoom(rooms,gameInfo);//记录房间信息
                InstanceViewer.getInstance().updataRoomList();
                if(mapper.getPortRoomMap().containsKey(-1)){//更新对应表
                    mapper.getPortRoomMap().get(-1).add(gameInfo.getIndex());
                }else{
                    Vector<Integer> list = new Vector<>();
                    list.add(gameInfo.getIndex());
                    mapper.getPortRoomMap().put(-1,list);
                }
                //向每一个slave端发送buffer的房间信息,直接发送对象
                for(Object o:mapper.getRemoteList()){
                    ImmutablePair<String,Integer> clientAddress = (ImmutablePair<String,Integer>)o;
                    objectOutStream.writeObject(mapper.getRoomBuffer());
                    byte[] data2 = byteArrayOutStream.toByteArray();
                    objectOutStream.reset();
                    System.out.println("Send to "+clientAddress.getLeft()+":"+clientAddress.getRight());
                    DatagramPacket packet2 = new DatagramPacket(data2, data2.length, InetAddress.getByName(clientAddress.getLeft()), clientAddress.getRight()+1);
                    socket.send(packet2);
                }
                mapper.clearRoomBuffer();//清除buffer
            }

            objectOutStream.close();
            byteArrayOutStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally{
            socket.close();

        }
        return true;
    }
}
