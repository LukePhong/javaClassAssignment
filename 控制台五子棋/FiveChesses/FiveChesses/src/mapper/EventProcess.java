package mapper;

import util.Event;
import util.ExchangeCommands;

public class EventProcess {
	public static Object processExchangeCommand(ExchangeCommands in,Event e) {
		switch (in) {
		case QUIT:
			//return MProcess.process(new hQuit(), new PQuit().produce());
			return null;
		case PUTCHESS:
			return MProcess.process(new hPutChess(), e);
		default:
			return null;
		}
	}
}
