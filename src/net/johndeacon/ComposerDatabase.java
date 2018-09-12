package net.johndeacon;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class ComposerDatabase {
	protected ComposerDatabase() {
		try {
			String[] nextRecord;
			CSVReader reader = new CSVReaderBuilder(new InputStreamReader( new FileInputStream("composers.csv"),"UTF-8") ).withSkipLines(1).build();
			while ( (nextRecord = reader.readNext()) != null ) {
				int birthYear, deathYear;
				allComposers.add(new Composer(
						nextRecord[familyNameFirstField],
						nextRecord[foreNameFirstField],
						nextRecord[birthYearField],
						nextRecord[deathYearField],
						nextRecord[ageField],
						nextRecord[knownComposerField]));
				if ( nextRecord[knownComposerField].length() != 0 ) {
					knownComposerIndices.add(allComposers.size()-1);	// size is 1 greater than the index of the last element
					
					birthYear = Integer.parseInt(nextRecord[birthYearField]);
					deathYear = Integer.parseInt(nextRecord[deathYearField]);
					Yearful knownYearEntry;
					knownYearEntry = knownYears.getOrDefault(birthYear, new Yearful(birthYear, new ArrayList<String>()));
					knownYearEntry.addEvent(nextRecord[foreNameFirstField] + " born");
					knownYears.put(birthYear, knownYearEntry);

					knownYearEntry = knownYears.getOrDefault(deathYear, new Yearful(deathYear, new ArrayList<String>()));
					knownYearEntry.addEvent(nextRecord[foreNameFirstField] + " died");
					knownYears.put(deathYear, knownYearEntry);
					
					/* While building, a map is convenient; but from then on access is (currently)
					   only by (random) position, so now we'll build an ArrayList from the entries.
					*/
					knownYearsIndexed = new ArrayList<Yearful>(knownYears.values());
				}
			}
			reader.close();
		} catch(IOException e) {
			System.out.println("File not found");
		}
		this.writeToCSVFile();
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
	protected Yearful yearByIndex(int index) {
		return knownYearsIndexed.get(index);
	}
	
	protected boolean writeToCSVFile() {
        try {
        	CSVWriter writer = new CSVWriter(Files.newBufferedWriter(Paths.get("./composers.new.csv")));
        	String[] headerLine = {"Family Name First", "Forename First", "Birth Date", "Death Date", "Age", "Memorized"};
        	writer.writeNext(headerLine);
        	for (Composer nextComposer : allComposers) {
        		writer.writeNext(new String[] { nextComposer.familyNameFirstFullName(),
        				nextComposer.forenameFirstFullName(),
        				nextComposer.birthyear(),
        				nextComposer.deathyear(),
        				nextComposer.age(),
        				nextComposer.knownComposer() });
        	}
        	writer.close();
        } catch(IOException e) {
        	e.printStackTrace();
        }
		return true;
	}
	
	private List<Composer> allComposers= new ArrayList<>();
	private List<Integer> knownComposerIndices = new ArrayList<>();
	private Map<Integer,Yearful> knownYears = new LinkedHashMap<>();		// "indexed" by CE year, e.g. 1756
	private List<Yearful> knownYearsIndexed;						// indexed by index number: 0, 1, 2, ...
	
	// Current layout of CSV file follows
	private int familyNameFirstField = 0;
	private int foreNameFirstField = 1;
	private int birthYearField = 2;
	private int deathYearField = 3;
	private int ageField = 4;			// Currently, in the database file this is just a subtraction so can be a year out
	private int knownComposerField = 5;
}
