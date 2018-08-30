package net.johndeacon;

import java.util.Random;

public class Chooser {
	Chooser(ComposerDatabase database) {
		_database = database;
		_totalEntries = database.totalEntries();
		_totalKnownEntries = database.totalKnownEntries();
	}
	public Composer randomKnownComposer() {
		int composerWanted;
		composerWanted = rng.nextInt(_totalKnownEntries);
		return _database.knownComposer(composerWanted);
	}
 
	public void test() {
		System.out.println("Total entries " + _totalEntries);
		System.out.println("Total known entries " + _totalKnownEntries);
	}
	private ComposerDatabase _database;
	private int _totalEntries;
	private int _totalKnownEntries;
	private Random rng = new Random();

}
