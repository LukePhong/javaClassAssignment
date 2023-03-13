package mapper;

import board.Position;
import control.Controller;
import util.*;

public abstract class MMethods implements Processor{
	
}

class hQuit extends MMethods{
	
	@Override
	public Object process(Object e) {
		System.out.println("Quiting");
		return e;
	}
}

class hPutChess extends MMethods{
	
	@Override
	public Object process(Object e) {
		PutChess nEvent=(PutChess)e;
		Position position=new Position(nEvent.row,nEvent.col);
		
		Controller.mapbuffer.insert(position);
		return e;
	}
}