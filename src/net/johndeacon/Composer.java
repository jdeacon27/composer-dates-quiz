package net.johndeacon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Composer {
	protected Composer(String familiarName, String birthYear, String deathYear) {
		_familiarName = familiarName;
		_birthYear = birthYear;
		_deathYear = deathYear;
		List<String> familiarNamewWords = new ArrayList<>(Arrays.asList(familiarName.split(" ")));
		_lastName = familiarNamewWords.get(familiarNamewWords.size() - 1);
		familiarNamewWords.remove(familiarNamewWords.size() - 1);
		_foreNames = String.join(" ", familiarNamewWords);
	}
	
	protected String familiarName() { return _familiarName; }
	protected String foreNames() { return _foreNames; }
	protected String lastName() { return _lastName; }
	protected String birthyear() { return _birthYear; }
	protected String deathyear() { return _deathYear; }
	
	private String _familiarName ;
	private String _foreNames;
	private String _lastName;
	private String _birthYear;
	private String _deathYear;
}
