package view;

import java.util.Scanner;

import util.*;

public abstract class VFactoty extends Factory{
	public abstract Event produce();
	//public abstract Event produce(int a,int b);
}

class PQuit extends VFactoty{
	
	@Override
	public Event produce() {
		return new Quit();
	}
}

class PWrongCommand extends VFactoty{
	
	@Override
	public Event produce() {
		return new WrongCommand();
	}
}

class PHelp extends VFactoty{
	@Override
	public Event produce() {
		return new Help();
	}
}

class PPutChess extends VFactoty{
	
	public Event produce(int r,int c) {
		return new PutChess(r,c);
	}

	@Override
	public Event produce() {
		int r,c;
		System.out.println("Please Input as: ROW COL");
		Scanner inScanner=new Scanner(System.in);
		r=inScanner.nextInt();
		c=inScanner.nextInt();
		
		
		return produce(r,c);
	}
}