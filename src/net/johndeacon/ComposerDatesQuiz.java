package net.johndeacon;

import java.util.Scanner;

public class ComposerDatesQuiz {
	public static void main(String[] args) {
		ComposerDatabase composerDatabase = new ComposerDatabase();
//		composerDatabase.test();
		Scanner sc = new Scanner(System.in);
		String response;
		Composer randomKnownComposer;
		do {
			randomKnownComposer = composerDatabase.randomKnownComposer();
			System.out.println(randomKnownComposer.familiarName());
			response = sc.next();
			System.out.println(randomKnownComposer.birthyear() + "-" + randomKnownComposer.deathyear() + "\n");
		} while ( !response.equalsIgnoreCase("stop") );
	}
}
