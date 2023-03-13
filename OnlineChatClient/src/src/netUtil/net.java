package netUtil;

import event.Event;
import event.TextResponse;
import event.noResponse;
import mainWindow.Manager;

import javax.lang.model.type.NullType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class net {
    public static final int SMALL_BUF_SIZE = 144;

    private Manager m;

    private InetAddress ip;
    private volatile Socket socket;
    private int port;
    private String host;
    private volatile ServerSocket ss;
    private boolean isServer;
    private String gotData;
    private String sentData;
    //private int clientNumber;


//    public net(Manager m){
//        this.m = m;
//    }

    public net(String host, int port,Manager m) {
        this.m = m;
        this.host = host;
        this.port = port;

        try{
            socket = new Socket(host, port);
            this.getMessage();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startServe() {
        //clientNumber=c;
        try{
            socket = ss.accept();
            isServer=true;
            this.getMessage();
        }catch(Exception e){
            e.printStackTrace();
        }
//        finally{
//            this.closeServer();
//        }

    }

    public void closeServer(){
        try {
            socket.close();
            //ss.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        isServer=false;
    }

    public void getData(){
        try{
            gotData=socket.getInputStream().toString();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void sendData(String str){

        sentData=str;
        try{
            socket.getOutputStream().write(sentData.getBytes());
            socket.getOutputStream().flush();
        }catch(Exception e){
            e.printStackTrace();
           // m.noResponse(new noResponse(Event.NO_MESSAGE,clientNumber));
        }
    }

    public void getMessage(){
        Thread t= new Thread(()->{

            while(true) {
                try {
                    BufferedReader br= null;
                    InputStream in=socket.getInputStream();
                    br = new BufferedReader(new InputStreamReader(in,"utf8"));
                    StringBuilder reqStr = new StringBuilder();
                    char[] buf = new char[SMALL_BUF_SIZE];
                    do {
//                        String str = br.readLine();//自循环 流没有结束符 不知道哪里是一行 阻塞
//                        m.getMessage(new TextResponse(Event.GET_MESSAGE, str));
                        if (br.read(buf) != -1) {
                            reqStr.append(buf);
                        }
                    }while(br.ready());

                    String str=reqStr.toString();
                    //socket.shutdownInput();
                    //System.out.println("got: "+in);
                    //System.out.println(socket.isClosed());
                    if(str!=null) {
                        m.getMessage(new TextResponse(Event.GET_MESSAGE, str));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        //t.setDaemon(true);
        t.start();

    }

}

