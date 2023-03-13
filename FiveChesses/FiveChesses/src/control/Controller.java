package control;


import board.*;
import mapper.*;
import util.*;

public class Controller {
	
	
	public static BoardMap mapbuffer;
	public static int stepCount=0;
	public static int stepWin=5;
	
	public static Object handleEvent(ExchangeCommands x,Event e) {
		try {
			return EventProcess.processExchangeCommand(x,e);
		}catch(Exception e1) {
			throw e1;
		}
	}
	
	public boolean storeBoard(Position p) {
		
		return mapbuffer.insert(p);
	}
	
	public boolean isWin() {
		
		return true;
	}

	public static void init() {
		
		mapbuffer=new BoardMap();
		stepCount=0;
		stepWin=5;
	}

	public static BoardBuffer getMap() {
		
		return mapbuffer.get();
	}

	public static Player check(Position tmpPos) {
		return mapbuffer.find(tmpPos);
	}
	
	
}
