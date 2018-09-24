package net.johndeacon;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import javax.swing.JOptionPane;

/*import com.dropbox.core.v2.files.FileMetadata;*/
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

class LocallySourcedDataFile extends DataFile {
	protected LocallySourcedDataFile () {
		if ( !(DataFile.csvFile().exists() && DataFile.csvFile().isFile()) ) {
			JOptionPane.showMessageDialog(null, "Local data file object constructor called, but local data file " + DataFile.csvFileName() + " doesn't exist.", "Fatal Error", JOptionPane.INFORMATION_MESSAGE);
			System.exit(1);
		} // could have an else to create a one-line CSV locally in the face of double failure
	}
	public CSVReader getCSVReader() {
		try {
			return new CSVReaderBuilder(new InputStreamReader( new FileInputStream(DataFile.csvFile()), encoding) ).withSkipLines(1).build();
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public CSVWriter getCSVWriter() {
		try {
			return new CSVWriter(Files.newBufferedWriter(Paths.get(DataFile.csvFile().getAbsolutePath())));
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void backup() {
    	try {
			Files.move(Paths.get(DataFile.csvFileName()), Paths.get(DataFile.csvFileName() + ".orig"), REPLACE_EXISTING);
				// Doing this is historical when a local data file is in use. I guess it gives the user the option when all in-memory changes precede one use of the Write button
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	return;
	}
	public void close() {
		// nothing to do?
	}

	private String encoding = "UTF-8";
	boolean databaseFileExistsLocally = false;		// Probably redundant
}
