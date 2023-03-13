package Bean;

import java.io.Serializable;
import java.util.Objects;

public class Chess implements Serializable {
    private int row;//1-19
    private int col;//1-19
    private ChessCondition condition;

    public Chess(int row, int col, ChessCondition condition){
        this.row = row;
        this.col = col;
        this.condition = condition;
    }

    public Chess(ChessCondition condition) {
        this.condition = condition;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public ChessCondition getCondition() {
        return condition;
    }

    public void setCondition(ChessCondition condition) {
        this.condition = condition;
    }

    public byte[] getBytes() {
        byte[] bytes = new byte[3];
        bytes[0] = (byte) row;
        bytes[1] = (byte) col;
        bytes[2] = (byte) condition.ordinal();
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chess chess = (Chess) o;
        return row == chess.row && col == chess.col && condition == chess.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, condition);
    }
}
