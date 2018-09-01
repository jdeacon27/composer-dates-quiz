package net.johndeacon;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class ComposerDatesQuiz extends JFrame {
	public ComposerDatesQuiz() {
		super();
        
	    // JLabel composerNamePrompt = new JLabel();		// Several of these are becoming class variables
		composerNamePrompt.setBounds(50, 50, 300, 30);		// x axis, y axis, width, height
	    this.add(composerNamePrompt);
	    Font font = composerNamePrompt.getFont();
	    Font infoFont = font.deriveFont(Font.PLAIN);
	    
	    JLabel userGuidance = new JLabel("Use the Tab and Space keys to move and \"click\"");
	    userGuidance.setBounds(50, 10, 300, 30);
	    userGuidance.setFont(infoFont);
	    this.add(userGuidance);

	    // JTextField birthAnswerField = new JTextField();
	    birthAnswerField.setBounds(50, 100, 80, 30);
	    this.add(birthAnswerField);

	    JLabel datesHyphen = new JLabel("-");
	    datesHyphen.setBounds(150, 100, 20, 30);
	    this.add(datesHyphen);

	    JTextField deathAnswerField = new JTextField();
	    deathAnswerField.setBounds(180, 100, 80, 30);
	    this.add(deathAnswerField);

	    JLabel resultField = new JLabel();
	    resultField.setBounds(50, 150, 300, 30);
	    this.add(resultField);

	    // JButton submitButton = new JButton("Respond");
	    submitButton.setBounds(50, 200, 100, 40);
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
		this.add(submitButton);
		
	    // JButton nextButton = new JButton("Next");
	    nextButton.setBounds(50, 300, 100, 40);
	    nextButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		birthAnswerField.setText("");
	    		deathAnswerField.setText("");
	    		poseQuestion();
	    		birthAnswerField.requestFocusInWindow();
	    	}
	    });
		this.add(nextButton);
		
	    JButton stopButton = new JButton("Stop");
	    stopButton.setBounds(50, 400, 100, 40);
	    stopButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		ComposerDatesQuiz.this.dispose();
	    	}
	    });
		this.add(stopButton);
		
		// JRadioButton knownComposers = new JRadioButton("Known Composers");
		knownComposers.setBounds(170, 290, 130, 30);
		// JRadioButton allComposers = new JRadioButton("All Composers");
		allComposers.setBounds(170, 320, 130, 30);
		ButtonGroup akGroup = new ButtonGroup();
		akGroup.add(allComposers);
		akGroup.add(knownComposers);
		knownComposers.setSelected(true);
		this.add(knownComposers);
		this.add(allComposers);
		
		this.setSize(400,500);		// width, height
		this.setLayout(null);		//using no layout managers
        this.setLocationByPlatform(true);
	}
    	public static void main(String[] args) {
    		ComposerDatesQuiz quiz = new ComposerDatesQuiz();
    		quiz.setVisible(true);
    		quiz.poseQuestion();
		
	}

	protected static void poseQuestion() {
		if ( quizzingKnownComposers ) {
			currentComposer = sessionChooser.nextShuffledKnownComposer();
		} else {
			currentComposer = sessionChooser.nextShuffledComposer();
		}
		composerNamePrompt.setText(currentComposer.familiarName());
		birthyear = currentComposer.birthyear();
		deathyear = currentComposer.deathyear();
	}
	
	private static ComposerDatabase composerDatabase = new ComposerDatabase();
	private static Chooser sessionChooser = new Chooser(composerDatabase);
	private static Composer currentComposer;
	private static String birthyear;
	private static String deathyear;
	private static boolean quizzingKnownComposers = false;
	
    private static JLabel composerNamePrompt = new JLabel();
    private static JTextField birthAnswerField = new JTextField();
    private static JButton submitButton = new JButton("Respond");
    private static JButton nextButton = new JButton("Next");
	private static JRadioButton knownComposers = new JRadioButton("Known Composers");
	private static JRadioButton allComposers = new JRadioButton("All Composers");
}
