package net.johndeacon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Composer {
	protected Composer(String forenameFirstFullName, String birthYear, String deathYear) {		// This constructor should be redundant now we can write back the CSV file 
		_forenameFirstFullName = forenameFirstFullName;
		_birthYear = birthYear;
		_deathYear = deathYear;
		this.extractAndSetNameComponents(forenameFirstFullName);
	}
	protected Composer(String familyNameFirstFullName, String forenameFirstFullName, String birthYear, String deathYear, String age, String knownComposer) {
		_familyNameFirstFullName = familyNameFirstFullName;
		_forenameFirstFullName = forenameFirstFullName;
		_birthYear = birthYear;
		_deathYear = deathYear;
		_age = age;
		_knownComposer = knownComposer;
		this.extractAndSetNameComponents(forenameFirstFullName);
	}
	private void extractAndSetNameComponents(String forenameFirstFullName) {
		List<String> familiarNamewWords = new ArrayList<>(Arrays.asList(forenameFirstFullName.split(" ")));
		_lastName = familiarNamewWords.get(familiarNamewWords.size() - 1);
		familiarNamewWords.remove(familiarNamewWords.size() - 1);
		_foreNames = String.join(" ", familiarNamewWords);

	}
	
	protected void forenameFirstFullName(String name) {
		_forenameFirstFullName = name;
	}
	protected void knownComposer(String flag) {
		_knownComposer = flag;		// flag is any character, usually "Y"; "" signifies unknown; historical quirk
	}
	
	protected String forenameFirstFullName() { return _forenameFirstFullName; }
	protected String familyNameFirstFullName() { return _familyNameFirstFullName; }
	protected String foreNames() { return _foreNames; }
	protected String lastName() { return _lastName; }
	protected String birthyear() { return _birthYear; }
	protected String deathyear() { return _deathYear; }
	protected String age() { return _age; }
	protected String knownComposer() { return _knownComposer; }
	
	private String _familyNameFirstFullName ;
	private String _forenameFirstFullName ;
	private String _foreNames;
	private String _lastName;
	private String _birthYear;
	private String _deathYear;
	private String _age;
	private String _knownComposer;	// known composer is a historical quirk. Originally this field in the CSV had a tick, but that
									// caused problems with typeface and encoding. So it was changed to a 'Y' character. But, for
									// safety we just store the contents of the field when reading the file and construction the database
}
