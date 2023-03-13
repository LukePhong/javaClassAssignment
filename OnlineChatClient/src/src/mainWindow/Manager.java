package mainWindow;

import event.Beans;
import event.sendMessage;
import event.startService;
import netUtil.net;
import viewer.Viewer;
import event.Beans.*;
import java.util.*;
import java.util.concurrent.*;

public class Manager {
    private static Manager m;
    private static Viewer viewer;
    private Map<String,Integer> clientThreads;
    private static ExecutorService exe;
    private int port;
    private volatile net n;

    private void init() {
        viewer= new Viewer();
        viewer.start(this);

    }

    public static void main(String[] args) {
        m=new Manager();
        m.init();
        exe = Executors.newCachedThreadPool();
    }

    public void sendMessage(Beans bean) {
        n.sendData(((sendMessage)bean).getMessage());
//        exe.execute(new Thread(){
//           public void run() {
//               //net n=new net("localhost",port,m);
//               try {
//                   this.wait(1000);
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
//               n.sendData(((sendMessage)bean).getMessage());
//            }
//        });
    }

    public void startService(Beans bean) {
        port=((startService)bean).getPort();
        n=new net("localhost",port,m);

//        exe.execute(new Thread(){
//            public void run() {
//                n=new net("localhost",port,m);
//                //n.startServe();
//            }
//        });
    }

    public void noResponse(Beans b){
        viewer.noResponse(b);
    }

    public void getMessage(Beans b){
        viewer.getMessage(b);
    }

    public void closing() {
        n.closeServer();
    }

}

