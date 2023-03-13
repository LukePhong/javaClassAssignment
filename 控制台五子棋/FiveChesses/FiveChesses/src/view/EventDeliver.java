package view;

import control.CProcess;
import control.Controller;
import util.*;

public class EventDeliver {
	public static Object processCommand(String in) {
		switch (in) {
		case "h":
		case "Help":
			return VProcess.process(new hHelp(), new PHelp().produce());
		case "q":
		case "Quit":
			return VProcess.process(new hQuit(), new PQuit().produce());
		case "p":
		case "PutChess":
			try {
				return VProcess.process(new hPutChess(), new PPutChess().produce());
			} catch (IllegalArgumentException e2) {
				return VProcess.process(new hWrongNum(), e2);
			}
		case "rg":
		case "Regret":
			//return processExchangeCommand(ExchangeCommands.REGRET, new Regret());
			return VProcess.process(new hRegret(), new Regret());
		case "r":
		case "Restart":
			return VProcess.process(new hRestart(), new Restart());
		case "rs":
		case "Reset":
			//return processExchangeCommand(ExchangeCommands.RESET, new Reset());
			return VProcess.process(new hReset(), new Reset());
		default:
			return VProcess.process(new hWrongCommand(), new PWrongCommand().produce());
		}
	}
	public static Object processExchangeCommand(ExchangeCommands in,Event e) {
		switch (in) {
		case QUIT:
			//return MProcess.process(new hQuit(), new PQuit().produce());
			return null;
		case SHOW:
			Viewer.outputBoard();
			return null;
		case AfterWin:
			Viewer.afterWin(e);
			return null;
		case REGRET:
		case RESET:
			
			return Controller.handleEvent(in, e);
			
		default:
			return null;
		}
	}
}
