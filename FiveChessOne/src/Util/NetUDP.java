package Util;

import Bean.*;
import Controller.Controller;
import View.Viewer;
import Map.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;

public class NetUDP {
    private static NetUDP net;

    public static NetUDP getNet() {
        if(net == null) {
            net = new NetUDP();
        }
        return net;
    }


    public void putChessRemote(Chess chess,PlayerOnline playerOnline) throws IOException {
        //send chess object to remote in UDP
        InetAddress address = InetAddress.getByName(playerOnline.getIp());
        int port = playerOnline.getPort();
        DatagramSocket socket = new DatagramSocket(port);

        byte[] data = chess.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        System.out.println("send chess to remote");
        socket.send(packet);
        socket.close();
    }


    public ChessCondition selectSideA(ImmutablePair<String, Integer> addressLocal, ImmutablePair<String, Integer> addressRemote) {
        AtomicReference<Viewer> v= new AtomicReference<>(Viewer.getViewer());
        AtomicReference<ChessCondition> chose= new AtomicReference<>();
        AtomicReference<ChessChoose> chessChoose=new AtomicReference<>();
        ChessChoose chessChooseReceive=null;
        new Thread(()->{
            //重要的其实只有那个时间
            chose.set(v.get().selectSide());
            //get system time
            long time = System.currentTimeMillis();
            chessChoose.set(new ChessChoose(time, chose));
        }).start();

        try {
            ByteArrayOutputStream byteArrayOutStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutStream = new ObjectOutputStream(byteArrayOutStream);

            DatagramSocket socket = new DatagramSocket(addressLocal.getRight());
            //listen chessChoose object from remote in UDP
            byte[] buffer = new byte[1024];
            DatagramPacket packetReceive = new DatagramPacket(buffer, buffer.length);

            System.out.println("1st Receive@"+addressLocal.getRight()+"for"+addressRemote.getRight()+addressLocal.getLeft());
            socket.receive(packetReceive);

            ByteArrayInputStream byteArrayInStream = new ByteArrayInputStream(buffer);
            ObjectInputStream objectInStream = new ObjectInputStream(byteArrayInStream);
            chessChooseReceive = (ChessChoose) objectInStream.readObject();
            objectInStream.close();

            while(chessChoose.get()==null){}
            //send chessChoose object to remote in UDP
            InetAddress address = InetAddress.getByName(addressRemote.getLeft());
            int port = addressRemote.getRight();
            objectOutStream.writeObject(chessChoose.get());
            byte[] data = byteArrayOutStream.toByteArray();
            objectOutStream.reset();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);

            socket.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(chessChoose.get().getChessCondition().equals(chessChooseReceive.getChessCondition())&&
                chessChoose.get().getTime()>chessChooseReceive.getTime()) {

            Viewer.getViewer().getInfoPanel().setEnemySide(chose.get());
            if(chose.get() ==ChessCondition.BLACK) {
                Viewer.getViewer().getInfoPanel().setCurrSide(ChessCondition.WHITE);
                chose.set(ChessCondition.WHITE);
            }else {
                Viewer.getViewer().getInfoPanel().setCurrSide(ChessCondition.BLACK);
                chose.set(ChessCondition.BLACK);
            }

        }else{
            Viewer.getViewer().getInfoPanel().setEnemySide(chose.get()==ChessCondition.BLACK?ChessCondition.WHITE:ChessCondition.BLACK);
            Viewer.getViewer().getInfoPanel().setCurrSide(chose.get());
        }
        return chose.get();
    }

    public ChessCondition selectSideB(ImmutablePair<String, Integer> addressLocal, ImmutablePair<String, Integer> addressRemote) {
        ChessCondition chose=Viewer.getViewer().selectSide();
        //get system time
        long time = System.currentTimeMillis();
        ChessChoose chessChoose=new ChessChoose(time,chose);
        ChessChoose chessChooseReceive=null;
        try {
            ByteArrayOutputStream byteArrayOutStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutStream = new ObjectOutputStream(byteArrayOutStream);

            DatagramSocket socket = new DatagramSocket(addressLocal.getRight());
            //listen chessChoose object from remote in UDP
            byte[] buffer = new byte[1024];

            //send chessChoose object to remote in UDP
            InetAddress address = InetAddress.getByName(addressRemote.getLeft());
            int port = addressRemote.getRight();
            objectOutStream.writeObject(chessChoose);
            byte[] data = byteArrayOutStream.toByteArray();
            objectOutStream.reset();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);

            //listen chessChoose object from remote in UDP
            DatagramPacket packetReceive = new DatagramPacket(buffer, buffer.length);
            //socket.setSoTimeout(0);
            System.out.println("2nd Receive@"+addressLocal.getRight()+"for"+addressRemote.getRight()+addressLocal.getLeft());
            socket.receive(packetReceive);
            ByteArrayInputStream byteArrayInStream = new ByteArrayInputStream(buffer);
            ObjectInputStream objectInStream = new ObjectInputStream(byteArrayInStream);
            chessChooseReceive = (ChessChoose) objectInStream.readObject();
            objectInStream.close();

            socket.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(chessChoose.getChessCondition().equals(chessChooseReceive.getChessCondition())&&
                chessChoose.getTime()>chessChooseReceive.getTime()) {

            Viewer.getViewer().getInfoPanel().setEnemySide(chose);
            if(chose ==ChessCondition.BLACK) {
                Viewer.getViewer().getInfoPanel().setCurrSide(ChessCondition.WHITE);
                chose=ChessCondition.WHITE;
            }else {
                Viewer.getViewer().getInfoPanel().setCurrSide(ChessCondition.BLACK);
                chose=ChessCondition.BLACK;
            }

        }else{
            Viewer.getViewer().getInfoPanel().setEnemySide(chose==ChessCondition.BLACK?ChessCondition.WHITE:ChessCondition.BLACK);
            Viewer.getViewer().getInfoPanel().setCurrSide(chose);
        }
        return chose;
    }

    public boolean poster(ImmutablePair<String, Integer> addressLocal, ImmutablePair<String, Integer> addressRemote) {
        //listen to local port for a while and send buffer to remote
        try {
            DatagramSocket socket = new DatagramSocket(addressLocal.getRight());
            //listen chessChoose object from remote in UDP
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.setSoTimeout(50);
            try {
                socket.receive(packet);
                System.out.println("Received");
            }catch (Exception e){
                packet=null;
            }
            if(packet!=null) {
                ByteArrayInputStream byteArrayInStream = new ByteArrayInputStream(buffer);
                ObjectInputStream objectInStream = new ObjectInputStream(byteArrayInStream);
                BattleMessage battleMessage = (BattleMessage) objectInStream.readObject();
                objectInStream.close();
                Controller.getController().notifyRemote(battleMessage);
            }

            if(MessageBuffer.getInstance().getBattleMessage()!=null) {
                //send chessChoose object to remote in UDP
                InetAddress address = InetAddress.getByName(addressRemote.getLeft());
                int port = addressRemote.getRight();
                ByteArrayOutputStream byteArrayOutStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutStream = new ObjectOutputStream(byteArrayOutStream);
                objectOutStream.writeObject(MessageBuffer.getInstance().getBattleMessage());
                MessageBuffer.getInstance().clear();
                byte[] data = byteArrayOutStream.toByteArray();
                objectOutStream.reset();
                DatagramPacket packetSend = new DatagramPacket(data, data.length, address, port);
                socket.send(packetSend);
                Thread.sleep(10);
                socket.send(packetSend);
                Thread.sleep(10);
                socket.send(packetSend);
                System.out.println("buffer sent");
            }
            socket.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
