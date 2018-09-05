package net.johndeacon;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Chooser {
	protected Chooser(ComposerDatabase database) {
		_database = database;
		_totalComposerEntries = database.totalComposerEntries();
		_totalKnownComposerEntries = database.totalKnownComposerEntries();
		_totalKnownYearEntries = database.totalYearsKnown();
	}
	
	protected  Composer randomKnownComposer() {
		// Not much point in keeping this. I can't see ever wanting a random choice as opposed to a shuffled
		int randomComposerIndex = rng.nextInt(_totalKnownComposerEntries);
		return _database.knownComposer(randomComposerIndex);
	}
	protected Composer nextShuffledKnownComposer() {
		if ( shuffledKnownComposerIndices.size() == 0 ) {
			for ( int index = 0; index < _totalKnownComposerEntries; index++ ) {
				shuffledKnownComposerIndices.add(index); 
			}
		}
		int shuffledIndicesIndex;
		int composerIndex;
		shuffledIndicesIndex = rng.nextInt(shuffledKnownComposerIndices.size());
		composerIndex = shuffledKnownComposerIndices.get(shuffledIndicesIndex);
		shuffledKnownComposerIndices.remove(shuffledIndicesIndex);
		return _database.knownComposer(composerIndex);
	}
	public Composer nextShuffledComposer() {
		if ( shuffledComposerIndices.size() == 0 ) {
			for ( int index = 0; index < _totalComposerEntries; index++ ) {
				shuffledComposerIndices.add(index); 
			}
		}
		int shuffledIndicesIndex;
		int composerIndex;
		shuffledIndicesIndex = rng.nextInt(shuffledComposerIndices.size());
		composerIndex = shuffledComposerIndices.get(shuffledIndicesIndex);
		shuffledComposerIndices.remove(shuffledIndicesIndex);
		return _database.anyComposer(composerIndex);
	}
	protected Year randomYear() {
		int year = rng.nextInt(_database.totalYearsKnown());
		return _database.yearByIndex(year);
	}
	protected Year nextShuffledYear() {
		if ( shuffledKnownYearIndices.size() == 0 ) {
			for ( int index = 0; index < _totalKnownYearEntries; index++ ) {
				shuffledKnownYearIndices.add(index); 
			}
		}
		int shuffledIndicesIndex;
		int yearIndex;
		shuffledIndicesIndex = rng.nextInt(shuffledKnownYearIndices.size());
		yearIndex = shuffledKnownYearIndices.get(shuffledIndicesIndex);
		shuffledKnownYearIndices.remove(shuffledIndicesIndex);
		return _database.yearByIndex(yearIndex);
	}

	protected void test() {
		System.out.println("Total entries " + _totalComposerEntries);
		System.out.println("Total known entries " + _totalKnownComposerEntries);
	}
	
	private ComposerDatabase _database;
	private List<Integer> shuffledComposerIndices = new LinkedList<>();
	private List<Integer> shuffledKnownComposerIndices = new LinkedList<>();
	private int _totalComposerEntries;
	private int _totalKnownComposerEntries;
	private int _totalKnownYearEntries;
	private List<Integer> shuffledKnownYearIndices = new LinkedList<>();
	private Random rng = new Random();

}
