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
		DataFile dataFile;
		if ( _dropboxTokenFile.exists() ) {
			_dataFileMode = "downloaded file";
			_csvFileName = _downloadedCsvFileName;
			_csvFile = new File(_csvFileName);
			dataFile = new RemotelySourcedDataFile();
		} else if ( _localCsvFile.exists() ) {
			_dataFileMode = "local file";
			_csvFileName = _localCsvFileName;
			_csvFile = new File(_csvFileName);
			dataFile =new LocallySourcedDataFile();
		} else {
			JOptionPane.showMessageDialog(null, "Neither local data file nor remote access token file exist.", "Fatal Error", JOptionPane.INFORMATION_MESSAGE);
			dataFile = null;
			System.exit(1);
		}
		return dataFile;
	}
	String dataFileMode() { return _dataFileMode; }
	static String csvFileName() { return _csvFileName; }
	static File csvFile() { return _csvFile; }
	static File dropboxTokenFile() { return _dropboxTokenFile; }
	
	static private String _dataFileMode;
	static private String _localCsvFileName = "composers.csv";
	static private String _downloadedCsvFileName = "composers.dropbox.csv";
	static private File _localCsvFile = new File(_localCsvFileName);
	static private String _csvFileName;
	static private File _csvFile;
	static private String _dropboxTokenfileName = "dropboxToken.txt";
	static private File _dropboxTokenFile = new File(_dropboxTokenfileName);

}
