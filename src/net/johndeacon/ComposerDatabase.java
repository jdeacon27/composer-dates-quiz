package net.johndeacon;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ComposerDatabase {
	protected ComposerDatabase() {
		try {
			String[] nextRecord;
			CSVReader reader = new CSVReaderBuilder(new FileReader("composers.csv")).withSkipLines(1).build();
			while ( (nextRecord = reader.readNext()) != null ) {
				allComposers.add(new Composer(nextRecord[foreNameFirstField], nextRecord[birthYearField], nextRecord[deathYearField]));
				if ( nextRecord[knownComposerField].length() != 0 ) {
					knownComposerIndices.add(allComposers.size()-1);	// size is 1 greater than the index of the last element
				}
				numberOfKnownComposers = knownComposerIndices.size();
			}
			reader.close();
		} catch(IOException e) {
			System.out.println("File not found");
		}
	}
	
	protected int totalEntries() { return allComposers.size(); }
	protected int totalKnownEntries() { return knownComposerIndices.size(); }
	protected Composer knownComposer(int index) {
		return allComposers.get(knownComposerIndices.get(index));
	}
	protected Composer anyComposer(int index) {
		return allComposers.get(index);
	}
	
	private List<Composer> allComposers= new ArrayList<>();
	private List<Integer> knownComposerIndices = new ArrayList<>();
	// Current layout of CSV file follows
	private int familyNameFirstField = 0;
	private int foreNameFirstField = 1;
	private int birthYearField = 2;
	private int deathYearField = 3;
	private int ageField = 4;			// Currently, in the database file this is just a subtraction so can be a year out
	private int knownComposerField = 5;
	private int numberOfKnownComposers;
}
