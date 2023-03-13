package event;

public class TextResponse extends Beans{
    private int who;
    private String res;

    public TextResponse(Event event,int n,String s){
        this.event = event;
        this.who = n;
        this.res = s;
    }

    public int getWho() {
        return who;
    }

    public String getRes() {
        return res;
    }
}
