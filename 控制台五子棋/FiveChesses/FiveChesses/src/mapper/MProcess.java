package mapper;

import util.Event;
import util.Processor;

public class MProcess{
	public static Object process(Processor p,Event e) {
		return p.process(e);
	}
}


