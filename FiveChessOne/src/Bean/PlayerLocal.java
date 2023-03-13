package Bean;

public class PlayerLocal extends Player {
    private ChessCondition condition;

    public PlayerLocal(){}

    public PlayerLocal(String name, ChessCondition condition) {
        super(name);
        this.condition = condition;
        super.setCondition(PlayerCondition.OFFLINE);
    }

    public ChessCondition getChessCondition() {
        return this.condition;
    }
}
