package net.johndeacon;

public class Composer {
	Composer() {
		
	}
	public String forename() { return foreName; }
	public String familyname() { return familyName; }
	public int birthyear() { return birthYear; }
	public int deathyear() { return deathYear; }
	
	private String foreName = "Edward";
	private String familyName = "Elgar";
	private int birthYear = 1857;
	private int deathYear = 1934;
}
