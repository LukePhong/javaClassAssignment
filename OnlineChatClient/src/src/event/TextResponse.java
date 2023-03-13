package event;

public class TextResponse extends Beans{
    private int who=0;
    private String res;

    public TextResponse(Event event, int n, String s){
        this.event = event;
        this.who = n;
        this.res = s;
    }

    public TextResponse(Event event, String s){
        this.event = event;
        //this.who = n;
        this.res = s;
    }

    public int getWho() {
        return who;
    }

    public String getRes() {
        return res;
    }
}
