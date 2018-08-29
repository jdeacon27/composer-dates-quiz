package net.johndeacon;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ComposerDatabase {
	public ComposerDatabase() {
		try {
			CSVReader reader = new CSVReaderBuilder(new FileReader("composers.csv")).withSkipLines(1).build();
			database = reader.readAll();
//			System.out.println(database);
			reader.close();
		} catch(IOException e) {
			System.out.println("File not found");
		}
	}
	public void test() {
		String[] testDatabaseEntry = database.get(9);
		System.out.println(testDatabaseEntry.length);
		System.out.println(testDatabaseEntry[1]);
		System.out.println(testDatabaseEntry[2] + "-" + testDatabaseEntry[3]);
		System.out.println(testDatabaseEntry[5]);
		System.out.println("Dummy");
	}
	public Composer randomComposer() {
		return new Composer();
	}
	private List<String[]> database;

}
