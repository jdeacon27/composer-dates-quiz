package net.johndeacon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Composer {
	protected Composer(String forenameFirstFullName, String birthYear, String deathYear) {
		_forenameFirstFullName = forenameFirstFullName;
		_birthYear = birthYear;
		_deathYear = deathYear;
		this.extractAndSetNameComponents(forenameFirstFullName);
//		List<String> familiarNamewWords = new ArrayList<>(Arrays.asList(forenameFirstFullName.split(" ")));
//		_lastName = familiarNamewWords.get(familiarNamewWords.size() - 1);
//		familiarNamewWords.remove(familiarNamewWords.size() - 1);
//		_foreNames = String.join(" ", familiarNamewWords);
	}
	protected Composer(String familyNameFirstFullName, String forenameFirstFullName, String birthYear, String deathYear) {
		_familyNameFirstFullName = familyNameFirstFullName;
		_forenameFirstFullName = forenameFirstFullName;
		_familyNameFirstFullName = 
		_birthYear = birthYear;
		_deathYear = deathYear;
		this.extractAndSetNameComponents(forenameFirstFullName);
	}
	private void extractAndSetNameComponents(String forenameFirstFullName) {
		List<String> familiarNamewWords = new ArrayList<>(Arrays.asList(forenameFirstFullName.split(" ")));
		_lastName = familiarNamewWords.get(familiarNamewWords.size() - 1);
		familiarNamewWords.remove(familiarNamewWords.size() - 1);
		_foreNames = String.join(" ", familiarNamewWords);

	}
	
	protected String familiarName() { return _forenameFirstFullName; }
	protected String foreNames() { return _foreNames; }
	protected String lastName() { return _lastName; }
	protected String birthyear() { return _birthYear; }
	protected String deathyear() { return _deathYear; }
	
	private String _familyNameFirstFullName ;
	private String _forenameFirstFullName ;
	private String _foreNames;
	private String _lastName;
	private String _birthYear;
	private String _deathYear;
}
