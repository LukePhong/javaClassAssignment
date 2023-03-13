package control;

import java.nio.Buffer;

import board.Position;
import control.Controller;
import mapper.BoardMap;
import util.*;

public abstract class CMethods implements Processor{
	
}

class hQuit extends CMethods{
	
	@Override
	public Object process(Object e) {
		System.out.println("Quiting");
		return e;
	}
}

class hReset extends CMethods{
	
	@Override
	public Object process(Object e) {
		
		Reset tmpReset=(Reset)e;
		if(!tmpReset.succ) return e;
		
		Controller.stepWin=tmpReset.num;
		return e;
	}
}

class hPutChess extends CMethods{
	
	@Override
	public Object process(Object e) {
		PutChess tmPutChess=(PutChess)e;
		
		if(tmPutChess.row<=0||tmPutChess.row>Limits.width||tmPutChess.col<=0||tmPutChess.col>Limits.width) {
			throw new IllegalArgumentException("Input Position out of Bound!");
		}
		
		Position position=new Position(tmPutChess.row, tmPutChess.col);
		
		if(Controller.mapbuffer.insert(position)) {
			Controller.stepCount++;
			WinCheck isWinCheck=(WinCheck)EventProcess.processExchangeCommand(ExchangeCommands.WinCheck, new WinCheck());
			isWinCheck.succ=true;
			return isWinCheck;
		}else {
			//tmPutChess.succ=false;
			//return tmPutChess;
			throw new IllegalArgumentException("Input Position is Occupied!");
		}
		
		
		//BoardMap.handleEvent(ExchangeCommands.PUTCHESS,(Event)e);
		
	}
}

class hWinCheck extends CMethods{
	
	@Override
	public Object process(Object e) {
		
		WinCheck winCheck=(WinCheck)e;
		winCheck.set(WinChecking.isWon(Controller.mapbuffer.lastPosition(),Controller.stepWin));
		winCheck.setWinner(Controller.mapbuffer.LastPlayer());
		
		return winCheck;
	}
}

class hRegret extends CMethods{
	
	@Override
	public Object process(Object e) {
		
		Event tmpEvent=(Event)e;
		if (Controller.stepCount==0) {
			tmpEvent.succ=false;
			return tmpEvent;
		}
		
		
		if(Controller.mapbuffer.regret()) {
			Controller.stepCount--;
			tmpEvent.succ=true;
			return tmpEvent;
		}
		
		return null;
	}
}