package Bean;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private PlayerCondition conditionPlayer=PlayerCondition.VIRTUAL;

    public Player(){}

    public Player(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerCondition getCondition() {
        return conditionPlayer;
    }

    public void setCondition(PlayerCondition condition) {
        this.conditionPlayer = condition;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    //parse "Player{name='name'}" to Player
    public static Player parseString(String str){
        String[] strs=str.split("'");
        Player player=new Player();
        player.setName(strs[1]);
        return player;
    }



}
