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
    private static net n;

    private void init() {
        viewer= new Viewer();
        viewer.start(this);
        //n=new net(this);
    }

    public static void main(String[] args) {
        m=new Manager();
        m.init();
        exe = Executors.newCachedThreadPool();

    }

    public void sendMessage(Beans bean) {
//        exe.execute(new Thread(){
//           public void run() {
               //net n=new net("localhost",port);
               n.sendData(((sendMessage)bean).getMessage());
//            }
//        });
    }

    public void startService(Beans bean) {
        port=((startService)bean).getPort();
        exe.execute(new Thread(){
            public void run() {
                n=new net("localhost",port,m);
                n.startServe(((startService)bean).getWho());
            }
        });
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

