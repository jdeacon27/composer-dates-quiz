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
		int randomComposerIndex = rng.nextInt(_totalKnownEntries);
		return _database.knownComposer(randomComposerIndex);
	}
	protected Composer nextShuffledKnownComposer() {
		if ( shuffledIndices.size() == 0 ) {
			for ( int index = 0; index < _totalKnownEntries; index++ ) {
				shuffledIndices.add(index); 
			}
		}
		
		System.out.println("Size of pack " + shuffledIndices.size());
		for ( int index = 0; index < shuffledIndices.size(); index++ ) {
			System.out.print(shuffledIndices.get(index) + ", "); 
		}
		System.out.println();
		
		int shuffledIndicesIndex;
		int composerIndex;
		shuffledIndicesIndex = rng.nextInt(shuffledIndices.size());
		System.out.println("shuffledIndicesIndex = " + shuffledIndicesIndex);
		composerIndex = shuffledIndices.get(shuffledIndicesIndex);
		shuffledIndices.remove(shuffledIndicesIndex);
		return _database.knownComposer(composerIndex);
	}
	protected void test() {
		System.out.println("Total entries " + _totalEntries);
		System.out.println("Total known entries " + _totalKnownEntries);
	}
	
	private ComposerDatabase _database;
	private List<Integer> shuffledIndices = new LinkedList<>();
	private int _totalEntries;
	private int _totalKnownEntries;
	private Random rng = new Random();

}
