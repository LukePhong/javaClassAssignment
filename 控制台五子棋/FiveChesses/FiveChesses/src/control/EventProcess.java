package control;

import util.Event;
import util.ExchangeCommands;

public class EventProcess {
	public static Object processExchangeCommand(ExchangeCommands in,Event e) {
		switch (in) {
		case QUIT:
			//return MProcess.process(new hQuit(), new PQuit().produce());
			return null;
		case WinCheck:
			return CProcess.process(new hWinCheck(), e);
		case PUTCHESS:
			try {
				return CProcess.process(new hPutChess(), e);
				
			} catch (Exception e2) {
				throw e2;
			}
		case REGRET:
			return CProcess.process(new hRegret(), e);
		case RESET:
			return CProcess.process(new hReset(), e);
		default:
			return null;
		}
	}
}
