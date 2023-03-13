package event;

public class startService extends Beans {
    private int port;
    //private int who;

    public startService(int port) {

        this.port=port;
        this.event = Event.START_SERVER;
        //this.who=w;
    }

    public int getPort() {
        return port;
    }

//    public int getWho() {
//        return who;
//    }
}