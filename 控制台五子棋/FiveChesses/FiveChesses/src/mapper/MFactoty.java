package mapper;

import util.*;

public abstract class MFactoty extends Factory{
	public abstract Event produce();
	//public abstract Event produce(int a,int b);
}

class PQuit extends MFactoty{
	
	@Override
	public Event produce() {
		return new Quit();
	}
}

class PPutChess extends MFactoty{
	
	public Event produce(int r,int c) {
		return new PutChess(r,c);
	}

	@Override
	public Event produce() {
		// TODO Auto-generated method stub
		return null;
	}
}