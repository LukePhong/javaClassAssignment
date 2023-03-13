package mapper;

import java.nio.Buffer;

import board.*;
import util.Event;
import util.ExchangeCommands;


public class BoardMap {
	private BoardBuffer buff;
	//public BoardMap mapbuffer=new BoardMap();
	//private Event lastPosEvent;
	
	public BoardMap() {
		buff=new BoardBuffer();
		buff.preChesses.push(new Step(null,new Player(Conditions.player_black)));//white first
	}
	
	public static Object handleEvent(ExchangeCommands x,Event e) {
		
		return EventProcess.processExchangeCommand(x,e);
	}
	
	public boolean insert(Position p) {
		
		if(buff.board.get(p)!=null) {
			return false;
		}
		Conditions tmPlayer=this.buff.preChesses.peek().getPlayer().p==Conditions.player_black?Conditions.player_wight:Conditions.player_black;
		Player tmPlayer2=new Player(tmPlayer);
		buff.board.put(p,tmPlayer2);
		buff.preChesses.push(new Step(p,tmPlayer2));
		return true;
	}


	public BoardBuffer get() {
		
		return buff;
	}


	public Player find(Position tmpPos) {
		
		return this.buff.board.get(tmpPos);
	};
	
	public Position lastPosition() {
		return buff.preChesses.peek().getPosition();
	}
	public Conditions LastPlayer() {
		return buff.preChesses.peek().getPlayer().p;
	}

	public boolean regret() {
		
		if(buff.board.remove(buff.preChesses.peek().getPosition())==null||
				buff.preChesses.pop()==null)
			return false;
		
		return true;
	}
	
	
}
