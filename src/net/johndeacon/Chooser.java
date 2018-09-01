package net.johndeacon;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Chooser {
	protected Chooser(ComposerDatabase database) {
		_database = database;
		_totalEntries = database.totalEntries();
		_totalKnownEntries = database.totalKnownEntries();
	}
	
	protected  Composer randomKnownComposer() {
		// Not much point in keeping this. I can't see ever wanting a random choice as opposed to a shuffled
		int randomComposerIndex = rng.nextInt(_totalKnownEntries);
		return _database.knownComposer(randomComposerIndex);
	}
	protected Composer nextShuffledKnownComposer() {
		if ( shuffledKnownIndices.size() == 0 ) {
			for ( int index = 0; index < _totalKnownEntries; index++ ) {
				shuffledKnownIndices.add(index); 
			}
		}
		int shuffledIndicesIndex;
		int composerIndex;
		shuffledIndicesIndex = rng.nextInt(shuffledKnownIndices.size());
		composerIndex = shuffledKnownIndices.get(shuffledIndicesIndex);
		shuffledKnownIndices.remove(shuffledIndicesIndex);
		return _database.knownComposer(composerIndex);
	}
	public Composer nextShuffledComposer() {
		if ( shuffledIndices.size() == 0 ) {
			for ( int index = 0; index < _totalEntries; index++ ) {
				shuffledIndices.add(index); 
			}
			System.out.println("Pack size " + shuffledIndices.size());
		}
		int shuffledIndicesIndex;
		int composerIndex;
		shuffledIndicesIndex = rng.nextInt(shuffledIndices.size());
		composerIndex = shuffledIndices.get(shuffledIndicesIndex);
		shuffledIndices.remove(shuffledIndicesIndex);
		return _database.anyComposer(composerIndex);
	}
	protected void test() {
		System.out.println("Total entries " + _totalEntries);
		System.out.println("Total known entries " + _totalKnownEntries);
	}
	
	private ComposerDatabase _database;
	private List<Integer> shuffledIndices = new LinkedList<>();
	private List<Integer> shuffledKnownIndices = new LinkedList<>();
	private int _totalEntries;
	private int _totalKnownEntries;
	private Random rng = new Random();

}
