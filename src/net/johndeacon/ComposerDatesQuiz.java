package net.johndeacon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ComposerDatesQuiz extends JFrame {
	public ComposerDatesQuiz() {
		super("Composer Dates Quiz");
        
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel lifetimePanel = new JPanel();
		lifetimePanel.setLayout(null);
		JPanel inThisYearPanel = new JPanel();
		inThisYearPanel.setLayout(null);
		JPanel editPanel = new JPanel();
		editPanel.setLayout(null);
		
		this.add(tabbedPane);
		tabbedPane.add("Lifetimes", lifetimePanel);
		tabbedPane.add("In this year", inThisYearPanel);
		tabbedPane.add("Edit", editPanel);
		tabbedPane.addChangeListener(new TabbedPaneSelection());

// Panel 1, the lifetime panel
		/*JLabel*/ composerNamePrompt.setBounds(50, 30, 290, 30);		// x axis, y axis, width, height
		composerNamePrompt.setOpaque(true);
		composerNamePrompt.setBackground(Color.WHITE);
		lifetimePanel.add(composerNamePrompt);
	    
	    /*JTextField*/ p1birthAnswerField.setBounds(50, 90, 80, 30);
	    lifetimePanel.add(p1birthAnswerField);

	    JLabel p1datesHyphen = new JLabel("-");
	    p1datesHyphen.setBounds(150, 90, 20, 30);
	    lifetimePanel.add(p1datesHyphen);

	    /*JTextField*/ p1deathAnswerField.setBounds(180, 90, 80, 30);
	    lifetimePanel.add(p1deathAnswerField);

	    JLabel resultField = new JLabel();
	    resultField.setBounds(50, 150, 280, 30);
		resultField.setOpaque(true);
//		resultField.setBackground(Color.WHITE);
		lifetimePanel.add(resultField);

	    /*JButton*/ p1SubmitButton.setText("Respond");
		p1SubmitButton.setBounds(50, 200, 100, 40);
	    p1SubmitButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String birthAnswer = p1birthAnswerField.getText();
	    		String deathAnswer = p1deathAnswerField.getText();
	    		if ( birthAnswer.equals(currentComposer.birthyear()) && deathAnswer.equals(currentComposer.deathyear()) ) {
	    			resultField.setText("Correct");
	    		} else {
	    			resultField.setText(currentComposer.birthyear() + " - " + currentComposer.deathyear());
	    		}
	    		composerNamePrompt.setText(currentComposer.forenameFirstFullName());
	    		p1NextButton.requestFocusInWindow();
	    		ComposerDatesQuiz.this.getRootPane().setDefaultButton(p1NextButton);
	    	}
	    });  		          
	    lifetimePanel.add(p1SubmitButton);
		
		/*JButton*/ p1NextButton.setText("Next");
	    p1NextButton.setBounds(50, 290, 100, 40);
		p1NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1birthAnswerField.setText("");
				p1deathAnswerField.setText("");
				resultField.setText("");
				poseComposerQuestion();
				p1birthAnswerField.requestFocusInWindow();
				ComposerDatesQuiz.this.getRootPane().setDefaultButton(p1SubmitButton);
			}
		});
		lifetimePanel.add(p1NextButton);
		
		/*JRadioButton*/ knownComposers.setText("Known Composers"); 
		knownComposers.setBounds(170, 280, 140, 30);
		/*JRadioButton*/ allComposers.setText("All Composers"); 
		allComposers.setBounds(170, 310, 140, 30);
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
		forenames.setBounds(50, 360, 130, 30);
		forenames.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ( e.getStateChange() == ItemEvent.SELECTED ) {
					quizzingForenames = true;
				}
			}
		});
		lifetimePanel.add(forenames);
		
	    /*JLabel*/ numbersInfo = new JLabel("(Quizzing from " + composerDatabase.totalKnownComposerEntries() + " composers)");
	    numbersInfo.setBounds(50, 400, 290, 30);
		lifetimePanel.add(numbersInfo);

		List<Component> order = new ArrayList<Component>();
        order.add(p1birthAnswerField);
        order.add(p1deathAnswerField);
        order.add(p1SubmitButton);
        order.add(p1NextButton);
        lifetimePanel.setFocusTraversalPolicyProvider(true);
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

	    /*JButton*/ p2SubmitButton.setText("Reveal");
	    p2SubmitButton.setBounds(50, 80, 100, 40);
	    p2SubmitButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String inThisYearAnswerText = String.join("\n", eventsThisYear);
	    		onThisDayAnswer.setText(inThisYearAnswerText);
	    		p2NextButton.requestFocusInWindow();
	    		ComposerDatesQuiz.this.getRootPane().setDefaultButton(p2NextButton);
	    	}
	    });  		          
	    inThisYearPanel.add(p2SubmitButton);

	    /*JButton*/ p2NextButton.setText("Next");
	    p2NextButton.setBounds(200, 80, 100, 40);
		p2NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onThisDayAnswer.setText("");
				poseYearQuestion();
				p2SubmitButton.requestFocusInWindow();
				ComposerDatesQuiz.this.getRootPane().setDefaultButton(p2SubmitButton);
			}
		});
		inThisYearPanel.add(p2NextButton);

		/*JTextArea*/ onThisDayAnswer = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(onThisDayAnswer);
		scrollPane.setBounds(50, 130, 290, 300);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		inThisYearPanel.add(scrollPane);
		onThisDayAnswer.setText("");
		
// Panel 3, the edit panel
		JLabel fieldLabel01 = new JLabel("Name (or fragment of name) to search for");
		fieldLabel01.setBounds(50, 00, 290, 30);
		editPanel.add(fieldLabel01);

		// Use spin buttons and print the number of matches between the buttons
		// Update the composer on field events; and only have a button for Write Database?
		/*JTextField*/ p3composerNameFragmentPrompt.setBounds(50, 30, 290, 30);
		p3composerNameFragmentPrompt.setToolTipText("To search for the composer you want to edit, enter their name or a part of their name and press the Enter key.");
		p3composerNameFragmentPrompt.setOpaque(true);
		p3composerNameFragmentPrompt.setBackground(Color.WHITE);
		p3composerNameFragmentPrompt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sought = p3composerNameFragmentPrompt.getText();
				List<String> matches = composerDatabase.composersThatMatch(sought);
				// Need to check that there was at least one match
				currentComposer = composerDatabase.composer(matches.get(0));
				ComposerDatesQuiz.this.updateEditPanel();
			}
		});
		editPanel.add(p3composerNameFragmentPrompt);
		
		JSeparator p3separator = new JSeparator(SwingConstants.HORIZONTAL);
		p3separator.setBounds(5, 75, 365, 30);
		editPanel.add(p3separator);
	    
		JLabel fieldLabel02 = new JLabel("Name");
		fieldLabel02.setBounds(50, 90, 290, 30);
		editPanel.add(fieldLabel02);

		/*JTextField*/ p3composerNameField.setBounds(50, 120, 290, 30);
		p3composerNameField.setToolTipText("The name of the composer you found. You can edit the name.");
//		p3composerNameField.setOpaque(true);
//		p3composerNameField.setBackground(Color.WHITE);
		p3composerNameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				composerDatabase.updateName(currentComposer, p3composerNameField.getText());
			}
		});
		editPanel.add(p3composerNameField);
		
		JLabel fieldLabel03 = new JLabel("Born");
		fieldLabel03.setBounds(50, 180, 290, 30);
		editPanel.add(fieldLabel03);

		/*JLabel*/ p3birthField.setBounds(50, 210, 80, 30);
		p3birthField.setToolTipText("This year can't be editied.");
		p3birthField.setOpaque(true);
		p3birthField.setBackground(Color.WHITE);
	    editPanel.add(p3birthField);

	    JLabel p3datesHyphen = new JLabel("-");
	    p3datesHyphen.setBounds(150, 210, 20, 30);
	    editPanel.add(p3datesHyphen);

		JLabel fieldLabel04 = new JLabel("Died");
		fieldLabel04.setBounds(180, 180, 290, 30);
		editPanel.add(fieldLabel04);

	    /*JLabel*/ p3deathField.setBounds(180, 210, 80, 30);
		p3deathField.setToolTipText("This year can't be editied.");
		p3deathField.setOpaque(true);
		p3deathField.setBackground(Color.WHITE);
		editPanel.add(p3deathField);

		/*JCheckBox*/ p3knownComposer.setBounds(50, 270, 130, 30);
		p3knownComposer.setToolTipText("Change whether or not this composer appears in the known composers lifetimes quiz.");
		p3knownComposer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ( e.getStateChange() == ItemEvent.SELECTED ) {
					composerDatabase.updateKnownComposer(currentComposer, "Y");
				} else {
					composerDatabase.updateKnownComposer(currentComposer, "");
				}
			}
		});
		editPanel.add(p3knownComposer);

		/*JLabel*/ p3familyNameFirstField.setBounds(50, 270, 390, 30);
		editPanel.add(p3familyNameFirstField);

	    JButton p3WriteButton = new JButton("Write");  
	    p3WriteButton.setBounds(50, 330, 100, 40);
	    p3WriteButton.setToolTipText("Write internal database out to the disk file.");
	    p3WriteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				composerDatabase.writeToCSVFile();
			}
		});
	    editPanel.add(p3WriteButton);

// Finish off the JFrame
		this.setSize(400,500);		// width, height
		lifetimePanel.getRootPane().setDefaultButton(p1SubmitButton);
        this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
	}

	
// Inner (instance) classes follow. An instance of such a class will be contained within each instance of the containing class. They have access to the outer instance's members.

	class RadioButtonActionListener implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent event) {
	        JRadioButton button = (JRadioButton) event.getSource();
	 	    if (button == allComposers) {
	 	        quizzingKnownComposers = false;
 	        	numbersInfo.setText("(Quizzing from " + composerDatabase.totalComposerEntries() + " composers)");
	        } else if (button == knownComposers) {
 	        	quizzingKnownComposers = true;
 	        	numbersInfo.setText("(Quizzing from " + composerDatabase.totalKnownComposerEntries() + " composers)");
	        }
	 	    return;
	    }
	}

	class TabbedPaneSelection implements ChangeListener {
	    public void stateChanged(ChangeEvent event) {
	        JTabbedPane tabbedPane = (JTabbedPane) event.getSource();
	        int selectedIndex = tabbedPane.getSelectedIndex();
	        if ( selectedIndex == 0 ) {
	        	if ( quizzingKnownComposers ) {
	 	        	numbersInfo.setText("(Quizzing from " + composerDatabase.totalKnownComposerEntries() + " composers)");
	        	} else {
	 	        	numbersInfo.setText("(Quizzing from " + composerDatabase.totalComposerEntries() + " composers)");
	        	}
				p1NextButton.requestFocusInWindow();
				p1NextButton.getRootPane().setDefaultButton(p1NextButton);
	        } else if ( selectedIndex == 1 ) {
				p2SubmitButton.requestFocusInWindow();
				p2SubmitButton.getRootPane().setDefaultButton(p2SubmitButton);
	        } else {
	    		if ( currentComposer != null ) {
	    			p3composerNameFragmentPrompt.setText("");
	    			ComposerDatesQuiz.this.updateEditPanel();
	    		}
	        }
	    }

	}

    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				ComposerDatesQuiz quiz = new ComposerDatesQuiz();
				quiz.setVisible(true);
				quiz.poseYearQuestion();
				quiz.p1NextButton.requestFocusInWindow();
				quiz.p1NextButton.getRootPane().setDefaultButton(quiz.p1NextButton);
				// This is asymmetric. One panel does these defaults here and the other elsewhere.
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
			composerNamePrompt.setText(currentComposer.forenameFirstFullName());
		}
		return;
	}
	protected void poseYearQuestion() {
		Yearful inQuestion = sessionChooser.nextShuffledYear();
		currentYear = inQuestion.yearAsStringCE();
		eventsThisYear = inQuestion.events();
		datePrompt.setText(currentYear);
	}
	protected void updateEditPanel() {
		p3composerNameField.setText(currentComposer.forenameFirstFullName());
    	p3birthField.setText(currentComposer.birthyear());
	    p3deathField.setText(currentComposer.deathyear());
	    p3familyNameFirstField.setText(currentComposer.familyNameFirstFullName());
	    p3knownComposer.setSelected(currentComposer.knownComposer().length() != 0);
	}
	
	private ComposerDatabase composerDatabase = new ComposerDatabase();
	private Chooser sessionChooser = new Chooser(composerDatabase);
	private Composer currentComposer;
	private boolean quizzingKnownComposers;
	private boolean quizzingForenames;
	private String currentYear;
	private List<String> eventsThisYear;
	
    private JLabel composerNamePrompt = new JLabel();
    private JTextField p1birthAnswerField = new JTextField();
    private JTextField p1deathAnswerField = new JTextField();
    private JButton p1SubmitButton = new JButton();
    private JButton p1NextButton = new JButton();
	private JRadioButton knownComposers = new JRadioButton();
	private JRadioButton allComposers = new JRadioButton();
	private JLabel numbersInfo;

	private JLabel datePrompt = new JLabel("");
	private JButton p2SubmitButton = new JButton();
    private JButton p2NextButton = new JButton();
    private JTextArea onThisDayAnswer = new JTextArea();
    
    private JTextField p3composerNameFragmentPrompt = new JTextField();
    private JTextField p3composerNameField = new JTextField();
    private JLabel p3birthField = new JLabel();
    private JLabel p3deathField = new JLabel();
    private JLabel p3familyNameFirstField = new JLabel();
    private JCheckBox p3knownComposer = new JCheckBox("Memorized?");
	
	private String explanationText = "You're not expected to write anything,\njust think about the answer and press Enter.";
	private static final long serialVersionUID = 6312869237473479611L;

// Class classes follow: regular classes and class definitions except that they are name scoped to the containing class definition    
	public static class QuizFocusTraversalPolicy extends FocusTraversalPolicy {
		public QuizFocusTraversalPolicy(List<Component> requiredOrder) {
			order = requiredOrder;
		}
		
		public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
			int index = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(index);
		}
		public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
			int index = order.indexOf(aComponent) == 0 ? order.size() - 1 : order.indexOf(aComponent) - 1;
			return order.get(index);
		}
		public Component getDefaultComponent(Container focusCycleRoot) { return order.get(0); }
		public Component getLastComponent(Container focusCycleRoot) { return order.get(order.size()-1); }
		public Component getFirstComponent(Container focusCycleRoot) { return order.get(0); }

		private List<Component> order;
	}
	
}
