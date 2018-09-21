package net.johndeacon;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedReader;
import java.io.File;
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

public class DatabaseFile {
	protected DatabaseFile () {
		if ( localCSVFile.exists() && localCSVFile.isFile() ) {
			databaseFileExistsLocally = true;
		}
		if ( dropboxTokenFile.exists() ) {		// If the token file is there we'll steam in and assume we're to load the database from Dropbox, even if a local file exists
			try {
				BufferedReader textReader = new BufferedReader(new FileReader(dropboxTokenfileName));
				dropboxToken = textReader.readLine();
				textReader.close();

				DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/composer-quiz").build();		// I don't understand quite what this does
		        /*DbxClientV2*/ mDbxClient = new DbxClientV2(config, dropboxToken);								// This object effectively IS dropbox
				fileInUse = remoteCSVFile;
		        OutputStream outputStream = new FileOutputStream(fileInUse);								// What we're going to call the dropbox file when it's been downloaded
		        mDbxClient.files().download(dropboxFileName).download(outputStream);
				databaseFileDownloaded = true;
		        // Could check that the file exists on Dropbox. At the moment it's non-existence is sufficiently unlikely that the exception stack trace is indication enough
			} catch(IOException e) {
				e.printStackTrace();
			} catch(DbxException e) {
				e.printStackTrace();
			}
		} else if ( databaseFileExistsLocally ) {
			fileInUse = localCSVFile;
			databaseFileDownloaded = false;
		} // could have another else to create a one-line CSV locally in the face of double failure
		fileCouldBeChanged = false;
	}
	protected CSVReader getCSVReader() {
		try {
			return new CSVReaderBuilder(new InputStreamReader( new FileInputStream(fileInUse), encoding) ).withSkipLines(1).build();
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	protected CSVWriter getCSVWriter() {
		fileCouldBeChanged = true;		// If we've handed out a writer, we have to assume that the file could change
										// If it's the local file, it doesn't matter: writes are writes; end of story. But if it's a downloaded file, an upload may be needed
		try {
			return new CSVWriter(Files.newBufferedWriter(Paths.get(fileInUse.getAbsolutePath())));
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	protected boolean safeToExit() {
			return (fileInUse == remoteCSVFile) && fileCouldBeChanged;
	}
	protected void close() {
		// This, and other places, would probably be helped by try with resources, which came in after my time try (resource list) { ...
		try {
			if ( fileInUse == remoteCSVFile && fileCouldBeChanged ) {		// If working with a downloaded file and program is closing, upload.
				try {
					mDbxClient.files().deleteV2(dropboxFileName + ".orig", null);	// Attempt to delete the existing backup before backing up
				} catch(DbxException ignored) {
					// Hopefully it just means that the backup wasn't on the remote for some reason
				}
				try {
					mDbxClient.files().moveV2(dropboxFileName, dropboxFileName + ".orig");
				} catch (DbxException ignored) {
					// Somehow the original file is no longer on the remote. This is weirder.
				}
				InputStream localSourceFile = new FileInputStream(remoteCSVfileName);
				try {
					/*FileMetadata metadata = */mDbxClient.files().uploadBuilder(dropboxFileName)
											.withMode(WriteMode.OVERWRITE)
											.uploadAndFinish(localSourceFile);
					try {Thread.sleep(5000); } catch (InterruptedException ignored) {}	// There must be a better way. If I knew the process that is carrying out the upload then I could use waitFor(process)
					localSourceFile.close();
					Files.delete(Paths.get(remoteCSVfileName));
					/*System.out.println(metadata);*/
				} catch(DbxException ingored) {
					JOptionPane.showMessageDialog(null, "Upload to remote data source failed", "Failure on remote resource", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	protected void backup() {
    	try {
    		if ( fileInUse == localCSVFile ) {
    			Files.move(Paths.get(localCSVfileName), Paths.get(localCSVfileName + ".orig"), REPLACE_EXISTING);
    		}		// Doing this is just historical and when a local data file is in use, we'll wait for the final upload to do this with the remote Dropbox version
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	return;
	}

	private boolean fileCouldBeChanged;
	private DbxClientV2 mDbxClient;
	private String dropboxTokenfileName = "dropboxToken.txt";
	private String dropboxToken;
	private String localCSVfileName = "composers.csv";
	private String dropboxFileName = "/composers.dropbox.csv";
	private String remoteCSVfileName = "composers.dropbox.csv";
	private String encoding = "UTF-8";
	private File dropboxTokenFile = new File(dropboxTokenfileName);
	private File localCSVFile = new File(localCSVfileName);
	private File remoteCSVFile = new File(remoteCSVfileName);
	private File fileInUse;
	boolean databaseFileExistsLocally = false;
	boolean databaseFileDownloaded = false;
}
