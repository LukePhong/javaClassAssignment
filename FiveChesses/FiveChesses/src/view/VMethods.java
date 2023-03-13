package view;

import java.util.Scanner;

import board.Conditions;
import control.Controller;
import util.*;

public abstract class VMethods implements Processor{
	
}

class hQuit extends VMethods{
	
	@Override
	public Object process(Object e) {
		System.out.println("Quiting");
		System.exit(0);
		return e;
	}
}

class hRestart extends VMethods{
	
	@Override
	public Object process(Object e) {
		System.out.println("Restarting");
		Viewer.isFreshStart=true;
		Viewer.startup();
		return e;
	}
}

class hReset extends VMethods{
	
	@Override
	public Object process(Object e) {
		
		Reset tmReset=(Reset)e;
		
		System.out.println("Please input a number:");
		
		int n;
		Scanner inputScanner=new Scanner(System.in);
		n=inputScanner.nextInt();
		
		if(n<=0||n>10) {
			System.out.println("Number Declined");
			tmReset.succ=false;
		}else {
			tmReset.num=n;
			tmReset.succ=true;
		}
		
		return EventDeliver.processExchangeCommand(ExchangeCommands.RESET, tmReset);
	}
}

class hWrongCommand extends VMethods{
	
	@Override
	public Object process(Object e) {
		System.out.println("No this Command! Check Your Input!");
		return e;
	}
}

class hWrongNum extends VMethods{
	
	@Override
	public Object process(Object e) {
		System.out.println((Exception)e);
		return e;
	}
}

class hHelp extends VMethods{
	
	@Override
	public Object process(Object e) {
		System.out.println("This is a Five-In-A-Row Game");
		System.out.println("Help/h: Show Helps");
		System.out.println("PutChess/p: Put a Chess");
		System.out.println("Regret/rg: Regret a Chess");
		System.out.println("Restart/r: Restart the Game");
		System.out.println("Quit/q: Quit the Game");
		return e;
	}
}

class hPutChess extends VMethods{
	
	@Override
	public Object process(Object e) {
		
		Object isCheck;
		isCheck=(WinCheck)Controller.handleEvent(ExchangeCommands.PUTCHESS,(Event)e);
		
		EventDeliver.processExchangeCommand(ExchangeCommands.SHOW, null);
				
		return isCheck;
	}
}

class hRegret extends VMethods{
	
	@Override
	public Object process(Object e) {
		
		Event tmpEvent=(Event)EventDeliver.processExchangeCommand(ExchangeCommands.REGRET, (Event)e);
		
		if(tmpEvent.succ) {
			System.out.println("Regret One Step:");
			EventDeliver.processExchangeCommand(ExchangeCommands.SHOW, null);
		}else {
			System.out.println("Regret Fail!");
		}
		
		return tmpEvent;
	}
}