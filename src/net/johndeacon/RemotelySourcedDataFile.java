package net.johndeacon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
/*import com.dropbox.core.v2.files.FileMetadata;*/
import com.dropbox.core.v2.files.WriteMode;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

class RemotelySourcedDataFile extends DataFile {
	protected  RemotelySourcedDataFile () {
		if ( !DataFile.dropboxTokenFile().exists() ) {
			JOptionPane.showMessageDialog(null, "Remote data file object constructor called, but remote access token file doesn't exist.", "Fatal Error", JOptionPane.INFORMATION_MESSAGE);
			System.exit(1);
		}
		if ( DataFile.csvFile().exists() ) {
			JOptionPane.showMessageDialog(null, "Remote resource already downloaded. Perhaps another instance of the program is running. Or perhaps a previous upload failed.", "Fatal Error", JOptionPane.INFORMATION_MESSAGE);
			System.exit(1);
		}
		try ( BufferedReader textReader = new BufferedReader(new FileReader(dropboxTokenfileName)) ) { 		// This now uses try with resources; there are probably other places that would probably be helped by them
			dropboxToken = textReader.readLine();

			DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/composer-quiz").build();		// I don't understand quite what this does
			/*DbxClientV2*/ mDbxClient = new DbxClientV2(config, dropboxToken);							// This object effectively IS dropbox
	        OutputStream outputStream = new FileOutputStream(DataFile.csvFile());						// What we're going to call the dropbox file when it's been downloaded
	        mDbxClient.files().download(dropboxFileName).download(outputStream);
			databaseFileDownloaded = true;
	        // Could check that the file exists on Dropbox. At the moment it's non-existence is sufficiently unlikely that the exception stack trace is indication enough
		} catch(IOException e) {
			e.printStackTrace();
		} catch(DbxException e) {
			e.printStackTrace();
		}
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
		// Nothing to do. A backup of the remote source is done at (final) upload time
	}
	public void close() {
		boolean stillNeedsUploading = false;
		if ( fileCouldBeChanged ) {		// If program is closing and working with a downloaded and changed file, upload.
			stillNeedsUploading = true;
			try {
				mDbxClient.files().deleteV2(dropboxFileName + ".orig", null);	// Attempt to delete the existing backup before backing up
			} catch(DbxException ignored) {
				// Hopefully it just means that the backup wasn't on the remote for some reason
			}
			try {
				mDbxClient.files().moveV2(dropboxFileName, dropboxFileName + ".orig");	// Attempt to back up the existing file
			} catch (DbxException ignored) {
				// Somehow the original file is no longer on the remote. This is weirder.
			}
			try ( InputStream localSourceFile = new FileInputStream(DataFile.csvFileName()) ){		// Attempt to upload the local file
				/*FileMetadata metadata = */mDbxClient.files().uploadBuilder(dropboxFileName)
										.withMode(WriteMode.OVERWRITE)
										.uploadAndFinish(localSourceFile);
				stillNeedsUploading = false;
				/*System.out.println(metadata);*/
			} catch(DbxException ingored) {
				stillNeedsUploading = true;
				JOptionPane.showMessageDialog(null, "Upload to remote data source failed", "Failure on remote resource", JOptionPane.INFORMATION_MESSAGE);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		if ( !stillNeedsUploading) {		// Delete the local file if working with a downloaded file, and the upload succeeded
			System.gc();	// Allegedly gc finishes before returning from this call, so I've commented out the following delay; however there has been one (albeit peculiar) file in use without the delay 
			try {Thread.sleep(500); } catch (InterruptedException ignored) {}	// There must be a better way. If I knew the process (or could be arsed to start it myself) then I could use waitFor(process)
			try {
				Files.delete(Paths.get(DataFile.csvFileName()));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean fileCouldBeChanged;
	private DbxClientV2 mDbxClient;
	private String dropboxTokenfileName = "dropboxToken.txt";
	private String dropboxToken;
	private String dropboxFileName = "/composers.dropbox.csv";
	private String encoding = "UTF-8";
	boolean databaseFileDownloaded = false;
}
