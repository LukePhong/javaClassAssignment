package control;

import util.Event;
import util.Processor;

public class CProcess{
	public static Object process(Processor p,Event e) {
		return p.process(e);
	}
}


