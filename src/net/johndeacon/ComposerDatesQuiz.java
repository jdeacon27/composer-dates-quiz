package net.johndeacon;

import java.util.Scanner;

public class ComposerDatesQuiz {
	public static void main(String[] args) {
		ComposerDatabase composerDatabase = new ComposerDatabase();
//		composerDatabase.test();
		Scanner sc = new Scanner(System.in);
		String response;
		String[] randomKnownComposer;
		do {
			randomKnownComposer = composerDatabase.randomKnownComposer();
			System.out.println(randomKnownComposer[1]);
			response = sc.next();
			System.out.println(randomKnownComposer[2] + "-" + randomKnownComposer[3] + "\n");
		} while ( !response.equalsIgnoreCase("stop") );
	}
}
