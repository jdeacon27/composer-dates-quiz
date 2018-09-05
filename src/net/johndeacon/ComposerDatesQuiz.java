package net.johndeacon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class ComposerDatesQuiz extends JFrame {
	public ComposerDatesQuiz() {
		super("Composer Dates Quiz");
        
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel lifetimePanel = new JPanel();
		lifetimePanel.setLayout(null);
		JPanel inThisYearPanel = new JPanel();
		inThisYearPanel.setLayout(null);
		
		this.add(tabbedPane);
		tabbedPane.add("Lifetimes", lifetimePanel);
		tabbedPane.add("In this year", inThisYearPanel);

// Panel 1, the lifetime panel
		/*JLabel*/ composerNamePrompt.setBounds(50, 40, 290, 30);		// x axis, y axis, width, height
		composerNamePrompt.setOpaque(true);
		composerNamePrompt.setBackground(Color.WHITE);
		lifetimePanel.add(composerNamePrompt);
	    
	    /*JTextField*/ birthAnswerField.setBounds(50, 100, 80, 30);
	    lifetimePanel.add(birthAnswerField);

	    JLabel datesHyphen = new JLabel("-");
	    datesHyphen.setBounds(150, 100, 20, 30);
	    lifetimePanel.add(datesHyphen);

	    /*JTextField*/ deathAnswerField.setBounds(180, 100, 80, 30);
	    lifetimePanel.add(deathAnswerField);

	    JLabel resultField = new JLabel();
	    resultField.setBounds(50, 150, 290, 30);
		resultField.setOpaque(true);
		resultField.setBackground(Color.WHITE);
		lifetimePanel.add(resultField);

	    /*JButton*/ panel1SubmitButton.setBounds(50, 210, 100, 40);
	    panel1SubmitButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String birthAnswer = birthAnswerField.getText();
	    		String deathAnswer = deathAnswerField.getText();
	    		if ( birthAnswer.equals(birthyear) && deathAnswer.equals(deathyear) ) {
	    			resultField.setText("Correct");
	    		} else {
	    			resultField.setText(birthyear + " - " + deathyear);
	    		}
	    		composerNamePrompt.setText(currentComposer.familiarName());
	    		panel1NextButton.requestFocusInWindow();
	    		ComposerDatesQuiz.this.getRootPane().setDefaultButton(panel1NextButton);
	    	}
	    });  		          
	    lifetimePanel.add(panel1SubmitButton);
		
		/*JButton*/ panel1NextButton.setBounds(50, 300, 100, 40);
		panel1NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				birthAnswerField.setText("");
				deathAnswerField.setText("");
				resultField.setText("");
				poseComposerQuestion();
				birthAnswerField.requestFocusInWindow();
				ComposerDatesQuiz.this.getRootPane().setDefaultButton(panel1SubmitButton);
			}
		});
		lifetimePanel.add(panel1NextButton);
		
		/*JRadioButton*/ knownComposers.setBounds(170, 290, 130, 30);
		/*JRadioButton*/ allComposers.setBounds(170, 320, 130, 30);
		ButtonGroup akGroup = new ButtonGroup();
		akGroup.add(allComposers);
		akGroup.add(knownComposers);
		knownComposers.setSelected(true); quizzingKnownComposers = true;
		RadioButtonActionListener rbActionListener = new RadioButtonActionListener();
		knownComposers.addActionListener(rbActionListener);
		allComposers.addActionListener(rbActionListener);
		lifetimePanel.add(knownComposers);
		lifetimePanel.add(allComposers);
		
		JCheckBox forenames = new JCheckBox("Quiz forenames?");
		forenames.setBounds(50, 370, 130, 30);
		forenames.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ( e.getStateChange() == ItemEvent.SELECTED ) {
					quizzingForenames = true;
				}
			}
		});
		lifetimePanel.add(forenames);
		
		Vector<Component> order = new Vector<Component>(4);
        order.add(birthAnswerField);
        order.add(deathAnswerField);
        order.add(panel1SubmitButton);
        order.add(panel1NextButton);
        lifetimePanel.setFocusTraversalPolicyProvider(true);
        System.out.println("lifetime panel isFocusTraversalPolicyProvider() is " + lifetimePanel.isFocusTraversalPolicyProvider());
        lifetimePanel.setFocusTraversalPolicy(new QuizFocusTraversalPolicy(order));
		
// Panel 2 - on this day
		inThisYearPanel.setOpaque(true);
		
        JTextArea explanation = new JTextArea(explanationText, 5,50);
		explanation.setBounds(50, 10, 290, 30);
		explanation.setEditable(false);
		explanation.setOpaque(false);
		inThisYearPanel.add(explanation);
		
		/*JLabel*/ datePrompt.setBounds(50, 40, 290, 30);		// x axis, y axis, width, height
		//datePrompt.setOpaque(true);
		inThisYearPanel.add(datePrompt);

	    /*JButton*/ panel2SubmitButton.setBounds(50, 80, 100, 40);
	    panel2SubmitButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String inThisYearAnswerText = String.join("\n", eventsThisYear);
	    		onThisDayAnswer.setText(inThisYearAnswerText);
	    		panel2NextButton.requestFocusInWindow();
	    		ComposerDatesQuiz.this.getRootPane().setDefaultButton(panel2NextButton);
	    	}
	    });  		          
	    inThisYearPanel.add(panel2SubmitButton);

	    /*JButton*/ panel2NextButton.setBounds(200, 80, 100, 40);
		panel2NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onThisDayAnswer.setText("");
				poseYearQuestion();
//				Year inQuestion = sessionChooser.nextShuffledYear();
//				currentYear = inQuestion.yearAsStringCE();
//				eventsThisYear = inQuestion.events();
//				datePrompt.setText(currentYear);
//				panel2SubmitButton.requestFocusInWindow();
//				ComposerDatesQuiz.this.getRootPane().setDefaultButton(panel2SubmitButton);
			}
		});
		inThisYearPanel.add(panel2NextButton);

		/*JTextArea*/ onThisDayAnswer = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(onThisDayAnswer);
		scrollPane.setBounds(50, 130, 290, 300);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		inThisYearPanel.add(scrollPane);
		onThisDayAnswer.setText("");

// Finish off the JFrame
		this.setSize(400,500);		// width, height
		lifetimePanel.getRootPane().setDefaultButton(panel1SubmitButton);
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

    public static class QuizFocusTraversalPolicy extends FocusTraversalPolicy {
		public QuizFocusTraversalPolicy(Vector<Component> requiredOrder) {
			order = new Vector<Component>(requiredOrder.size());
			order.addAll(requiredOrder);
		}
		
		public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
			int index = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(index);
		}
		public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
			int index = order.indexOf(aComponent) - 1;
			if (index < 0) {
				index = order.size() - 1;
			}
			return order.get(index);
		}
		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}
		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}
		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		private Vector<Component> order;
	}

    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				ComposerDatesQuiz quiz = new ComposerDatesQuiz();
				quiz.setVisible(true);
				quiz.panel1NextButton.requestFocusInWindow();
				quiz.getRootPane().setDefaultButton(quiz.panel1NextButton);
				quiz.poseYearQuestion();
			}
		});
		
	}

	protected void poseComposerQuestion() {
		if ( quizzingKnownComposers ) {
			currentComposer = sessionChooser.nextShuffledKnownComposer();
		} else {
			currentComposer = sessionChooser.nextShuffledComposer();
		}
		if ( quizzingForenames ) {
			composerNamePrompt.setText("? " + currentComposer.lastName());
		} else {
			composerNamePrompt.setText(currentComposer.familiarName());
		}
		birthyear = currentComposer.birthyear();
		deathyear = currentComposer.deathyear();
		return;
	}
	protected void poseYearQuestion() {
		Year inQuestion = sessionChooser.nextShuffledYear();
		currentYear = inQuestion.yearAsStringCE();
		eventsThisYear = inQuestion.events();
		datePrompt.setText(currentYear);
		panel2SubmitButton.requestFocusInWindow();
		ComposerDatesQuiz.this.getRootPane().setDefaultButton(panel2SubmitButton);
	}
	
	private ComposerDatabase composerDatabase = new ComposerDatabase();
	private Chooser sessionChooser = new Chooser(composerDatabase);
	private Composer currentComposer;
	private String birthyear;
	private String deathyear;
	private boolean quizzingKnownComposers;
	private boolean quizzingForenames;
	private String currentYear;
	private List<String> eventsThisYear;
	
    private JLabel composerNamePrompt = new JLabel();
    private JTextField birthAnswerField = new JTextField();
    private JTextField deathAnswerField = new JTextField();
    private JButton panel1SubmitButton = new JButton("Respond");
    private JButton panel1NextButton = new JButton("Next");
	private JRadioButton knownComposers = new JRadioButton("Known Composers");
	private JRadioButton allComposers = new JRadioButton("All Composers");

	private JLabel datePrompt = new JLabel("");
	private JButton panel2SubmitButton = new JButton("Respond");
    private JButton panel2NextButton = new JButton("Next");
    private JTextArea onThisDayAnswer = new JTextArea(); 
	
	private String explanationText = "You're not expected to write anything,\njust think about the answer and press Enter.";
	private static final long serialVersionUID = 6312869237473479611L;
}
