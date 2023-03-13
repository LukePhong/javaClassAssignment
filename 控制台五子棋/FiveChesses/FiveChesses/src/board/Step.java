package board;

public class Step {
	
	private Position p;
	private Player l;
	
	public Step(Position pos,Player pl) {
		p=pos;
		l=pl;
	}
	
	public Player getPlayer() {
		return l;
	}

	public Position getPosition() {
		// TODO Auto-generated method stub
		return p;
	}
}
