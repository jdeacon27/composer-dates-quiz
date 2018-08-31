package net.johndeacon;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class ComposerDatesQuiz {
	public static void main(String[] args) {
		JFrame frame=new JFrame();  
        
	    // JLabel composerNamePrompt = new JLabel();		// Several of these are becoming class variables
		composerNamePrompt.setBounds(50, 50, 300, 30);		// x axis, y axis, width, height
	    frame.add(composerNamePrompt);
	    Font font = composerNamePrompt.getFont();
	    Font infoFont = font.deriveFont(Font.PLAIN);
	    
	    JLabel userGuidance = new JLabel("Use the Tab and Space keys to move and \"click\"");
	    userGuidance.setBounds(50, 10, 300, 30);
	    userGuidance.setFont(infoFont);
	    frame.add(userGuidance);

	    // JTextField birthAnswerField = new JTextField();
	    birthAnswerField.setBounds(50, 100, 80, 30);
	    frame.add(birthAnswerField);

	    JLabel datesHyphen = new JLabel("-");
	    datesHyphen.setBounds(150, 100, 20, 30);
	    frame.add(datesHyphen);

	    JTextField deathAnswerField = new JTextField();
	    deathAnswerField.setBounds(180, 100, 80, 30);
	    frame.add(deathAnswerField);

	    JLabel resultField = new JLabel();
	    resultField.setBounds(50, 150, 300, 30);
	    frame.add(resultField);

	    // JButton submitButton = new JButton("Respond");
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
	    		nextButton.requestFocusInWindow();
	    	}
	    });  		          
		frame.add(submitButton);
		
	    // JButton nextButton = new JButton("Next");
	    nextButton.setBounds(110, 300, 100, 40);
	    nextButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		birthAnswerField.setText("");
	    		deathAnswerField.setText("");
	    		poseQuestion();
	    		birthAnswerField.requestFocusInWindow();
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
        frame.setLocationByPlatform(true);
		frame.setVisible(true);
		poseQuestion();
		
	}
	protected static void poseQuestion() {
		shuffledKnownComposer = sessionChooser.nextShuffledKnownComposer();
		composerNamePrompt.setText(shuffledKnownComposer.familiarName());
		birthyear = shuffledKnownComposer.birthyear();
		deathyear = shuffledKnownComposer.deathyear();
	}
	
	private static ComposerDatabase composerDatabase = new ComposerDatabase();
	private static Chooser sessionChooser = new Chooser(composerDatabase);
	private static Composer shuffledKnownComposer;
	private static String birthyear;
	private static String deathyear;
	
    private static JLabel composerNamePrompt = new JLabel();
    private static JTextField birthAnswerField = new JTextField();
    private static JButton submitButton = new JButton("Respond");
    private static JButton nextButton = new JButton("Next");
}
