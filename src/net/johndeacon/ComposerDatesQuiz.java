package net.johndeacon;

import java.awt.event.*;
import javax.swing.*;

public class ComposerDatesQuiz {
	public static void main(String[] args) {
		JFrame frame=new JFrame();  
        
	    JLabel composerNamePrompt = new JLabel();
	    composerNamePrompt.setBounds(50, 50, 300, 30);		//x axis, y axis, width, height
	    frame.add(composerNamePrompt);

	    JTextField birthAnswerField = new JTextField();
	    birthAnswerField.setBounds(50, 100, 80, 30);
	    frame.add(birthAnswerField);

	    JLabel datesHyphen = new JLabel("-");
	    datesHyphen.setBounds(150, 100, 20, 30);
	    frame.add(datesHyphen);

	    JTextField deathAnswerField = new JTextField();
	    deathAnswerField.setBounds(180, 100, 80, 30);
	    frame.add(deathAnswerField);

	    JLabel resultField = new JLabel();
	    resultField.setBounds(130, 150, 300, 30);
	    frame.add(resultField);

	    JButton submitButton = new JButton("Go");
	    submitButton.setBounds(110, 200, 100, 40);
	    submitButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String birthAnswer = birthAnswerField.getText();
	    		String deathAnswer = deathAnswerField.getText();
	    		if ( birthAnswer.equals(birthyear) && deathAnswer.equals(deathyear) ) {
	    			resultField.setText(" Correct");
	    		} else {
	    			resultField.setText(birthyear + " - " + deathyear);
	    		}
	    	}
	    });  		          
		frame.add(submitButton);
		
	    JButton nextButton = new JButton("Next");
	    nextButton.setBounds(110, 300, 100, 40);
	    nextButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		birthAnswerField.setText("");
	    		deathAnswerField.setText("");
				shuffledKnownComposer = sessionChooser.nextShuffledKnownComposer();
				composerNamePrompt.setText(shuffledKnownComposer.familiarName());
				birthyear = shuffledKnownComposer.birthyear();
				deathyear = shuffledKnownComposer.deathyear();
	    	}
	    });
		frame.add(nextButton);
		
	    JButton stopButton = new JButton("Stop");
	    stopButton.setBounds(110, 400, 100, 40);
	    stopButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.dispose();
	    	}
	    });
		frame.add(stopButton);

		
		frame.setSize(400,500);		// width, height  
		frame.setLayout(null);		//using no layout managers  
		frame.setVisible(true);  
		
	}
	
	private static ComposerDatabase composerDatabase = new ComposerDatabase();
	private static Chooser sessionChooser = new Chooser(composerDatabase);
	private static Composer shuffledKnownComposer;
	private static String birthyear;
	private static String deathyear;

}
