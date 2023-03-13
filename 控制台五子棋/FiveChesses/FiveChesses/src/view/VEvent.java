package view;

import util.Event;

public abstract class VEvent extends Event{
	
}

class Quit extends VEvent{
	
}

class WrongCommand extends VEvent{}

class Help extends VEvent{}

