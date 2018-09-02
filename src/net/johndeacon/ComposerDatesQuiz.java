package net.johndeacon;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
// import javax.swing.SwingUtilities;	// In case I wanted to set the TAB order?	

public class ComposerDatesQuiz extends JFrame {
	public ComposerDatesQuiz() {
		super("Composer Dates Quiz");
        
		/*JLabel*/ composerNamePrompt.setBounds(50, 50, 300, 30);		// x axis, y axis, width, height
	    this.add(composerNamePrompt);
	    Font font = composerNamePrompt.getFont();
	    Font infoFont = font.deriveFont(Font.PLAIN);
	    
	    JLabel userGuidance = new JLabel("Use the Tab and Space keys to move and \"click\"");
	    userGuidance.setBounds(50, 10, 300, 30);
	    userGuidance.setFont(infoFont);
	    this.add(userGuidance);

	    /*JTextField*/ birthAnswerField.setBounds(50, 100, 80, 30);
	    this.add(birthAnswerField);

	    JLabel datesHyphen = new JLabel("-");
	    datesHyphen.setBounds(150, 100, 20, 30);
	    this.add(datesHyphen);

	    /*JTextField*/ deathAnswerField.setBounds(180, 100, 80, 30);
	    this.add(deathAnswerField);

	    JLabel resultField = new JLabel();
	    resultField.setBounds(50, 150, 300, 30);
	    this.add(resultField);

	    /*JButton*/ submitButton.setBounds(50, 200, 100, 40);
	    submitButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String birthAnswer = birthAnswerField.getText();
	    		String deathAnswer = deathAnswerField.getText();
	    		if ( birthAnswer.equals(birthyear) && deathAnswer.equals(deathyear) ) {
	    			resultField.setText("Correct");
	    		} else {
	    			resultField.setText(birthyear + " - " + deathyear);
	    		}
	    		nextButton.requestFocusInWindow();
	    	}
	    });  		          
		this.add(submitButton);
		
		/*JButton*/ nextButton.setBounds(50, 300, 100, 40);
	    nextButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		birthAnswerField.setText("");
	    		deathAnswerField.setText("");
	    		resultField.setText("");
	    		poseQuestion();
	    		birthAnswerField.requestFocusInWindow();
	    	}
	    });
		this.add(nextButton);
		
		/*JRadioButton*/ knownComposers.setBounds(170, 290, 130, 30);
		/*JRadioButton*/ allComposers.setBounds(170, 320, 130, 30);
		ButtonGroup akGroup = new ButtonGroup();
		akGroup.add(allComposers);
		akGroup.add(knownComposers);
		knownComposers.setSelected(true); quizzingKnownComposers = true;
		RadioButtonActionListener rbActionListener = new RadioButtonActionListener();
		knownComposers.addActionListener(rbActionListener);
		allComposers.addActionListener(rbActionListener);
		this.add(knownComposers);
		this.add(allComposers);
		
		this.setSize(400,500);		// width, height
		this.setLayout(null);		//using no layout managers
        this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class RadioButtonActionListener implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent event) {
	        JRadioButton button = (JRadioButton) event.getSource();
	 	    if (button == allComposers) {
	 	        quizzingKnownComposers = false;
	        } else if (button == knownComposers) {
 	        	quizzingKnownComposers = true;
	        }
	 	    return;
	    }
	}

	public static void main(String[] args) {
		ComposerDatesQuiz quiz = new ComposerDatesQuiz();
		quiz.setVisible(true);
		quiz.poseQuestion();
	}

	protected void poseQuestion() {
		if ( quizzingKnownComposers ) {
			currentComposer = sessionChooser.nextShuffledKnownComposer();
		} else {
			currentComposer = sessionChooser.nextShuffledComposer();
		}
		composerNamePrompt.setText(currentComposer.familiarName());
		birthyear = currentComposer.birthyear();
		deathyear = currentComposer.deathyear();
		return;
	}
	
	private ComposerDatabase composerDatabase = new ComposerDatabase();
	private Chooser sessionChooser = new Chooser(composerDatabase);
	private Composer currentComposer;
	private String birthyear;
	private String deathyear;
	private boolean quizzingKnownComposers;
	
    private JLabel composerNamePrompt = new JLabel();
    private JTextField birthAnswerField = new JTextField();
    private JTextField deathAnswerField = new JTextField();
    private JButton submitButton = new JButton("Respond");
    private JButton nextButton = new JButton("Next");
	private JRadioButton knownComposers = new JRadioButton("Known Composers");
	private JRadioButton allComposers = new JRadioButton("All Composers");
	private static final long serialVersionUID = 6312869237473479611L;
}
