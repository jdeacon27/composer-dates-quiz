package net.johndeacon;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ComposerDatabase {
	protected ComposerDatabase() {
		try {
			String[] nextRecord;
			CSVReader reader = new CSVReaderBuilder(new FileReader("composers.csv")).withSkipLines(1).build();
			while ( (nextRecord = reader.readNext()) != null ) {
				int birthYear, deathYear;
				allComposers.add(new Composer(nextRecord[foreNameFirstField], nextRecord[birthYearField], nextRecord[deathYearField]));
				if ( nextRecord[knownComposerField].length() != 0 ) {
					knownComposerIndices.add(allComposers.size()-1);	// size is 1 greater than the index of the last element
					
					birthYear = Integer.parseInt(nextRecord[birthYearField]);
					deathYear = Integer.parseInt(nextRecord[deathYearField]);
					Year knownYearEntry;
					knownYearEntry = knownYears.getOrDefault(birthYear, new Year(birthYear, new ArrayList<String>()));
					knownYearEntry.addEvent(nextRecord[foreNameFirstField] + " born");
					knownYears.put(birthYear, knownYearEntry);
					System.out.println(birthYear + " " + knownYearEntry + "\n");

					knownYearEntry = knownYears.getOrDefault(deathYear, new Year(deathYear, new ArrayList<String>()));
					knownYearEntry.addEvent(nextRecord[foreNameFirstField] + " died");
					knownYears.put(deathYear, knownYearEntry);
					System.out.println(deathYear + " " + knownYearEntry + "\n");
					
					/* While building, a map is convenient; but from then on access is (currently)
					   only by (random) position, so now we'll build an ArrayList from the entries.
					*/
					knownYearsIndexed = new ArrayList<Year>(knownYears.values());
				}
			}
			reader.close();
		} catch(IOException e) {
			System.out.println("File not found");
		}
	}
	
	protected int totalComposerEntries() { return allComposers.size(); }
	protected int totalKnownComposerEntries() { return knownComposerIndices.size(); }
	protected int totalYearsKnown() { return knownYears.size(); }
	protected Composer knownComposer(int index) {
		return allComposers.get(knownComposerIndices.get(index));
	}
	protected Composer anyComposer(int index) {
		return allComposers.get(index);
	}
	protected List<String> eventsInYear(int thisYear) {
		return knownYears.get(thisYear).events();
	}
	protected Year yearByIndex(int index) {
		return knownYearsIndexed.get(index);
	}
	
	private List<Composer> allComposers= new ArrayList<>();
	private List<Integer> knownComposerIndices = new ArrayList<>();
	private Map<Integer,Year> knownYears = new HashMap<>();
	private List<Year> knownYearsIndexed;
	
	// Current layout of CSV file follows
	//private int familyNameFirstField = 0;
	private int foreNameFirstField = 1;
	private int birthYearField = 2;
	private int deathYearField = 3;
	//private int ageField = 4;			// Currently, in the database file this is just a subtraction so can be a year out
	private int knownComposerField = 5;
}
