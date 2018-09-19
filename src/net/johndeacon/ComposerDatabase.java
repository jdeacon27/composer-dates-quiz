package net.johndeacon;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class ComposerDatabase {
	/* Reads a CSV file and creates a database of composer information. It holds composers both
	 * by position (for repeatable random access) and by normalized, forename first, composer name.
	 * The positional information is available over all the composers in the database or over just
	 * those that were marked as known (or memorized).
	 * The database can also be asked for lists of birth and death events by year (CE).
	 */
	protected ComposerDatabase() {
		DatabaseFile file = new DatabaseFile();
		CSVReader reader = file.getCSVReader();
		try {
			String[] nextRecord;
			while ( (nextRecord = reader.readNext()) != null ) {
				int birthYear, deathYear;
				Composer nextComposer;
				nextComposer = new Composer(
						nextRecord[familyNameFirstField],
						nextRecord[foreNameFirstField],
						nextRecord[birthYearField],
						nextRecord[deathYearField],
						nextRecord[ageField],
						nextRecord[knownComposerField]);
				allComposers.add(nextComposer);
				String accentlessComposerName;
				accentlessComposerName = this.normalize(nextRecord[foreNameFirstField]);
				composers.put(accentlessComposerName, nextComposer);
				if ( nextRecord[knownComposerField].length() != 0 ) {
					// In order to access known composers efficiently we keep a list of their indices in the main list
					knownComposerIndices.add(allComposers.size()-1);	// size is 1 greater than the index of the last element
					
					// And for the known composers we gather their birth and death events by year
					birthYear = Integer.parseInt(nextRecord[birthYearField]);
					deathYear = Integer.parseInt(nextRecord[deathYearField]);
					Yearful knownYearEntry;
					knownYearEntry = knownYears.getOrDefault(birthYear, new Yearful(birthYear, new ArrayList<String>()));
					knownYearEntry.addEvent(nextRecord[foreNameFirstField] + " born");
					knownYears.put(birthYear, knownYearEntry);

					knownYearEntry = knownYears.getOrDefault(deathYear, new Yearful(deathYear, new ArrayList<String>()));
					knownYearEntry.addEvent(nextRecord[foreNameFirstField] + " died");
					knownYears.put(deathYear, knownYearEntry);
				}
				// While building the above structures, a map is convenient; but from then on access is (currently)
				//   only by (random) position, so now we'll build an ArrayList from the entries.
				knownYearsIndexed = new ArrayList<Yearful>(knownYears.values());
			}
			reader.close();
		} catch(IOException e) {
			System.out.println("File not found");
		}
	}
	
	protected int totalComposerEntries() { return allComposers.size(); }
	protected int totalKnownComposerEntries() { return knownComposerIndices.size(); }
	protected int totalYearsKnown() { return knownYears.size(); }
	protected Composer composer(String familiarName) {
		return composers.get(familiarName);
	}
	protected Composer knownComposer(int index) {
		return allComposers.get(knownComposerIndices.get(index));
	}
	protected Composer anyComposer(int index) {
		return allComposers.get(index);
	}
	protected String normalize(String withAccentsAndMixedCase) {
		// The following converts accents to a normalized form where the accents are separated from the characters,
		// \\p{M} then finds and removes the accents,
		// and the case is then folded
		return Normalizer.normalize(withAccentsAndMixedCase, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
	}
	protected List<String> composersThatMatch(String frag) {
		final String accentlessComposerName = this.normalize(frag);
		List<String> matches = composers.keySet()
										.stream()
										.filter(s -> s.contains(accentlessComposerName))
										.collect(Collectors.toList());
		return matches;
	}
	protected List<String> eventsInYear(int thisYear) {
		return knownYears.get(thisYear).events();
	}
	protected Yearful yearByIndex(int index) {
		return knownYearsIndexed.get(index);
	}
	protected void updateName(Composer updatee, String update) {
		// composers are held two ways: by position, which won't be impacted; and by the name being updated, so ...
		composers.remove(this.normalize(updatee.forenameFirstFullName()));			// remove via current name
		updatee.forenameFirstFullName(update);						// update name
		composers.put(this.normalize(updatee.forenameFirstFullName()), updatee);	// add back via updated name
		diskFileOutOfSync = true;
		return;
	}
	protected void updateKnownComposer(Composer updatee, String flag) {
		int indexInKnownComposerIndices = knownComposerIndices.indexOf(allComposers.indexOf(updatee));	// Is the updatee already known
		if ( flag.equals("Y") && indexInKnownComposerIndices == -1 ) { // then it is a change and it's an addition
			knownComposerIndices.add(allComposers.indexOf(updatee));
			diskFileOutOfSync = true;
			updatee.knownComposer(flag);
		}
		if ( flag.equals("") && indexInKnownComposerIndices != -1 ) { // then it is a change and it's a removal
			knownComposerIndices.remove(indexInKnownComposerIndices);
			diskFileOutOfSync = true;
			updatee.knownComposer(flag);
		}
		// I'm not quite sure how this (checkbox at the moment) could get set to what it already is, but those two conditions between them ignore such an event
	}
	protected boolean safeToClose() {
		return !diskFileOutOfSync;
	}
	protected boolean writeToCSVFile() {
        try {
        	Files.move(Paths.get("./composers.csv"), Paths.get("./composers.orig.csv"), REPLACE_EXISTING);
        	CSVWriter writer = new CSVWriter(Files.newBufferedWriter(Paths.get("./composers.csv")));
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
        diskFileOutOfSync = false;
		return true;
	}
	
	private List<Composer> allComposers = new ArrayList<>();
	private List<Integer> knownComposerIndices = new ArrayList<>();
	private Map<String,Composer> composers = new HashMap<>();
	private Map<Integer,Yearful> knownYears = new LinkedHashMap<>();		// Yearfuls "indexed" by CE year, e.g. 1756
	private List<Yearful> knownYearsIndexed;								// Yearfuls indexed by index number: 0, 1, 2, ...
	private boolean diskFileOutOfSync;
	// Current layout of CSV file follows
	private int familyNameFirstField = 0;
	private int foreNameFirstField = 1;
	private int birthYearField = 2;
	private int deathYearField = 3;
	private int ageField = 4;			// Currently, in the database file this is just a subtraction so can be a year out
	private int knownComposerField = 5;
}
