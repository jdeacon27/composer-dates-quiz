package net.johndeacon;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ComposerDatabase {
	public ComposerDatabase() {
		try {
			String[] nextRecord;
			CSVReader reader = new CSVReaderBuilder(new FileReader("composers.csv")).withSkipLines(1).build();
			while ( (nextRecord = reader.readNext()) != null ) {
				allComposers.add(new Composer(nextRecord[1], nextRecord[2], nextRecord[3]));
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
	public void test() {
//		String[] testDatabaseEntry = allComposers.get(0);
//		
////		System.out.println("Known composer indices List has " + knownComposerIndices.size() + " elements");
//		for ( Integer element : knownComposerIndices ) {
//			System.out.println(allComposers.get(element)[1]);
//		}
//		System.out.println(testDatabaseEntry.length);
//		System.out.println(testDatabaseEntry[1]);
//		System.out.println(testDatabaseEntry[2] + "-" + testDatabaseEntry[3]);
//		System.out.println(testDatabaseEntry[5]);
	}
	public Composer randomComposer() {
		return new Composer();
	}
	
	public Composer randomKnownComposer() {		// should return a composer
		return allComposers.get(knownComposerIndices.get(rng.nextInt(numberOfKnownComposers)));
	}
	
	private List<Composer> allComposers= new ArrayList<>();		// should have the read of the CSV file construct Composers and store them
	private List<Integer> knownComposerIndices = new ArrayList<>();
	private int knownComposerField = 5;		// sixth element in the String[] array
	private int numberOfKnownComposers;
	private Random rng = new Random();
}
