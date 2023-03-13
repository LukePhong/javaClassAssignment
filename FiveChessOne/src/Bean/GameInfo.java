package Bean;

import java.io.Serializable;

public class GameInfo implements Serializable {
    private Player p1,p2;
    private int index=-1;
    private int owner;

    public GameInfo(){
        p1 = new Player();
        p2 = new Player();
    }

    public GameInfo(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public Player getP1(){
        return p1;
    }

    public Player getP2(){
        return p2;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", index=" + index +
                '}';
    }

    //parse toString above to gameinfo
    public static GameInfo parseString(String s){
        String[] strs = s.split(",");
        Player p1 = Player.parseString(strs[0].substring(strs[0].indexOf("p1=") + 4, strs[0].length() - 1));
        Player p2 = Player.parseString(strs[1].substring(strs[1].indexOf("p2=") + 4, strs[1].length() - 1));
        GameInfo gi = new GameInfo(p1,p2);
        gi.setIndex(Integer.parseInt(strs[2].substring(strs[2].indexOf("index=") + 6, strs[2].length() - 1)));
        return gi;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
