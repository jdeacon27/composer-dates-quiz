package net.johndeacon;

import java.io.File;

import javax.swing.JOptionPane;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

abstract class DataFile {		// Factory for its subclasses
	abstract CSVReader getCSVReader();
	abstract CSVWriter getCSVWriter();
	abstract void close();
	abstract void backup();
	static DataFile dataFileBuilder() {
		if ( dropboxTokenFile.exists() ) {
			csvFileName = "composers.dropbox.csv";
			csvFile = new File(csvFileName);
			return new RemotelySourcedDataFile();
		} else if ( localCsvFile.exists() ) {
			csvFileName = localCsvFileName;
			csvFile = new File(csvFileName);
			return new LocallySourcedDataFile();
		} else {
			JOptionPane.showMessageDialog(null, "Neither local data file nor remote access token file exist.", "Fatal Error", JOptionPane.INFORMATION_MESSAGE);
			System.exit(1);
		}

		return new LocallySourcedDataFile();
	}
	static String csvFileName() { return csvFileName; }
	static File csvFile() { return csvFile; }
	static File dropboxTokenFile() { return dropboxTokenFile; }
	
	static private String localCsvFileName = "composers.csv";
	static private File localCsvFile = new File(localCsvFileName);
	static private String csvFileName;
	static private File csvFile;
	static private String dropboxTokenfileName = "dropboxToken.txt";
	static private File dropboxTokenFile = new File(dropboxTokenfileName);

}
