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
		int randomComposerIndex = rng.nextInt(_database.totalKnownComposerEntries());
		return _database.knownComposer(randomComposerIndex);
	}
	protected Composer nextShuffledKnownComposer() {
		// If the "deck" is empty then make an ordered list of integers representing the known composers.
		// I'll be removing items, so it's a collection rather than an array.
		if ( shuffledKnownComposerIndices.size() == 0 ||	// "deck" is empty or ...
						_totalKnownComposerEntries != _database.totalKnownComposerEntries() ) {			// number of known composers has changed since I set up the deck, so I'll have to start again as it might have left us looking for a composer that doesn't exist
			_totalKnownComposerEntries = _database.totalKnownComposerEntries();		// Note the number of known composers that existed when I made the new "deck"
			for ( int index = 0; index < _database.totalKnownComposerEntries(); index++ ) {
				shuffledKnownComposerIndices.add(index); 
			}
		}
		/* Another way to handle a change in the population of known composers (now that we have an edit panel) would be to store the known composers' indices
		 * from the all composers list instead of their indices in the known composers (index) list. That would mean that the change in population wouldn't
		 * take effect until the deck emptied. Either way, I don't think a proper shuffle is possible if the population is changing. 
		 */
		
		int shuffledIndicesIndex;
		int composerIndex;
		shuffledIndicesIndex = rng.nextInt(shuffledKnownComposerIndices.size());	// pick a random integer from my deck
		composerIndex = shuffledKnownComposerIndices.get(shuffledIndicesIndex);		// read the integer at that position in the deck (first time around, these are the same number?)
		shuffledKnownComposerIndices.remove(shuffledIndicesIndex);					// remove the integer at that position in the deck; it's now been used
		return _database.knownComposer(composerIndex);								// ask the database for that, shuffled, known composer
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
	protected Yearful randomYear() {
		int year = rng.nextInt(_database.totalYearsKnown());
		return _database.yearByIndex(year);
	}
	protected Yearful nextShuffledYear() {
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

	private ComposerDatabase _database;
	private List<Integer> shuffledComposerIndices = new LinkedList<>();
	private List<Integer> shuffledKnownComposerIndices = new LinkedList<>();
	private int _totalComposerEntries;
	private int _totalKnownComposerEntries;
	
	private List<Integer> shuffledKnownYearIndices = new LinkedList<>();
	private int _totalKnownYearEntries;
	
	private Random rng = new Random();

}
