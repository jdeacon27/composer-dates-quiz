package net.johndeacon;

import java.util.List;

public class Yearful {
	Yearful(int yearCE, List<String> events) {
		_yearCE = yearCE;
		_events = events;
	}
	Yearful(String yearCE, List<String> events) {
		_yearCE = Integer.parseInt(yearCE);
		_events = events;
	}
	protected void addEvent(String event) {
		_events.add(event);
	}
	protected int yearAsIntCE () { return _yearCE; }
	protected String yearAsStringCE () { return Integer.toString(_yearCE); }
	protected List<String> events() { return _events; }
	public String toString() { return _events.toString(); }
	
	private int _yearCE;
	private List<String> _events;
}
