package Bean;


import java.util.concurrent.atomic.AtomicReference;

public class ChessChoose implements java.io.Serializable {
    //store time and chessCondition
    private long time;
    private ChessCondition chessCondition;

    public ChessChoose(long time, AtomicReference<ChessCondition> chessCondition) {
        this.time = time;
        this.chessCondition = chessCondition.get();
    }

    public ChessChoose(long time, ChessCondition chose) {
        this.time = time;
        this.chessCondition = chose;
    }

    public long getTime() {
        return time;
    }

    public ChessCondition getChessCondition() {
        return chessCondition;
    }
}
