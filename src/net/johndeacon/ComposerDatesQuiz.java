package net.johndeacon;

public class ComposerDatesQuiz {
	public static void main(String[] args) {
		 ComposerDatabase composerDatabase = new ComposerDatabase();
		 composerDatabase.test();
		 Composer randomComposer = composerDatabase.randomComposer();
		 System.out.print(randomComposer.forename());
		 System.out.print(" ");
		 System.out.println(randomComposer.familyname());
	}
}
