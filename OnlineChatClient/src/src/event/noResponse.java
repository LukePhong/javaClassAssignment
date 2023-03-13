package event;

public class noResponse extends Beans{

    private int who;

    public noResponse(Event event,int n){
        this.event = event;
        this.who = n;
    }

    public int getWho() {
        return who;
    }
}
