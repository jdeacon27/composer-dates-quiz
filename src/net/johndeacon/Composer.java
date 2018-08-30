package net.johndeacon;

public class Composer {
	Composer() {
		// default constructor makes no sense; this was just for testing
	}
	protected Composer(String familiarName, String birthYear, String deathYear) {
		_familiarName = familiarName;
		_birthYear = birthYear;
		_deathYear = deathYear;

	}
	
	protected String familiarName() { return _familiarName; }
	protected String birthyear() { return _birthYear; }
	protected String deathyear() { return _deathYear; }
	
	private String _familiarName = "Edward Elgar";
	private String _birthYear = "1857";
	private String _deathYear = "1934";
}
