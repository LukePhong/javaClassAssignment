package board;

public class Position {
	public Position(int i, int j) {
		pos[0]=i;
		pos[1]=j;
	}

	public int[] pos=new int[2];
	
	@Override
	public int hashCode() {
		return new Integer(pos[0]).hashCode()+new Integer(pos[1]).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        Position otherPosition=(Position) obj;
		return pos[0]==otherPosition.pos[0]&&pos[1]==otherPosition.pos[1];
	}
}
