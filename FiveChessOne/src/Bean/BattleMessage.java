package Bean;

import java.io.Serializable;

public class BattleMessage implements Serializable {
    private Chess chess=new Chess(-1,-1,ChessCondition.EMPTY);
    private Actions action=Actions.NONE;
    private String text="";

    public BattleMessage() {
    }

    public BattleMessage(Actions action) {
        this.action = action;
    }

    public BattleMessage(Actions action, String text) {
        this.action = action;
        this.text = text;
    }

    public BattleMessage(Chess chess, Actions action, String text) {
        this.chess = chess;
        this.action = action;
        this.text = text;
    }

    public Chess getChess() {
        return chess;
    }

    public void setChess(Chess chess) {
        chess = chess;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        text = text;
    }


}
