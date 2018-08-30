package net.johndeacon;

public class Composer {
	Composer() {
		// default constructor makes no sense; this was just for testing
	}
	Composer(String familiarName, String birthYear, String deathYear) {
		_familiarName = familiarName;
		_birthYear = birthYear;
		_deathYear = deathYear;

	}
	public String familiarName() { return _familiarName; }
	public String birthyear() { return _birthYear; }
	public String deathyear() { return _deathYear; }
	
	private String _familiarName = "Edward Elgar";
	private String _birthYear = "1857";
	private String _deathYear = "1934";
}
