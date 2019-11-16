
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.sql.SQLException;
import java.util.EventListener;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import java.time.LocalDate;
import javax.swing.JComboBox;


public class Instrument extends JFrame {
	
	private Instrument ownReference;
	
	private JPanel InstrPanel;
	private static final long serialVersionUID =1L;
	private JPanel comments;
	private LocalDate localDate;
	private JPanel visitPanel;
	private JPanel upperPanel, mainPanel;
	private JComboBox instrModelCombo;
	final Container contentPane = getContentPane();

	
	public Instrument() {
		
		UpperPanel();
		MainPanel();
		submitButton();
		add(visitPanel);
		backButton();
		add(visitPanel);
		

		
		setTitle("Instrument Details");
		setPreferredSize(new Dimension(450, 550)); //
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setVisible(true);
	}

	private void CreateVisitPanel() {
		visitPanel = new JPanel();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		visitPanel.setBorder(blackline);
		
		JLabel visitIDLabel = new JLabel("Visit ID: ");
		JTextField visitIDField = new JTextField();
		visitIDField.setText("1");  //to be modified
		visitIDField.setEditable(false);
		
		JLabel dateLabel = new JLabel("Date: ");
		JTextField dateField = new JTextField();
		dateField.setText(LocalDate.now().toString());
		dateField.setEditable(false);
		
		JLabel THCLabel = new JLabel("THC#: ");
		JTextField THCField = new JTextField(6); // to be modified
		THCField.setEditable(true);
		
		JLabel visitNumberLabel = new JLabel("Visit Number: ");
		JTextField visitNumberField = new JTextField(3); 
		visitNumberField.setText("5");
		visitNumberField.setEditable(false);
		
		visitPanel.add(visitIDLabel);
		visitPanel.add(visitIDField);
		visitPanel.add(dateLabel);
		visitPanel.add(dateField);
		visitPanel.add(THCLabel);
		visitPanel.add(THCField);
		visitPanel.add(visitNumberLabel);
		visitPanel.add(visitNumberField);
	}
	private void UpperPanel() {
        upperPanel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        upperPanel.setBorder(blackline);
        JLabel patientInfoLabel = new JLabel("Patient's Information: ");
        JLabel lastNameLabel = new JLabel("Last Name: ");
        JLabel firstNameLabel = new JLabel("First Name: ");
        JTextField lastNameField = new JTextField();
        JTextField firstNameField = new JTextField();
        lastNameField.setText("T");
        firstNameField.setText("Mike");
        lastNameField.setEditable(false);
        
        JLabel dateLabel = new JLabel("Date: ");
        JTextField dateField = new JTextField();
        dateField.setText(LocalDate.now().toString());
        dateField.setEditable(false);
        
        JLabel THCLabel = new JLabel("THC#: ");
        JTextField THCField = new JTextField(6); // to be modified
        THCField.setText("102322935");
        THCField.setEditable(false);
        
        JLabel category = new JLabel("Patient Category: ");
        JTextField categoryField = new JTextField();
        categoryField.setText("XXXX");
        categoryField.setEditable(false);
        
        JLabel type = new JLabel("Instrument Type: ");
        JTextField typeField = new JTextField();
        typeField.setText("Typexxx");
        typeField.setEditable(false);
        
        upperPanel.add(patientInfoLabel);
        upperPanel.add(lastNameLabel);
        upperPanel.add(lastNameField);
        upperPanel.add(firstNameLabel);
        upperPanel.add(firstNameField);
        upperPanel.add(dateLabel);
        upperPanel.add(dateField);
        upperPanel.add(THCLabel);
        upperPanel.add(THCField);
        upperPanel.add(category);
        upperPanel.add(categoryField);
        upperPanel.add(type);
        upperPanel.add(typeField);
        
        setTitle("Patient's Information:");
        MainPanel();
        
        setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        add(upperPanel);
        add(mainPanel);
               
    }
	
	class ComboItem
	{
	    private String modelNum;


	    public ComboItem(String model)
	    {
	        this.modelNum = model;
	    }

	    @Override
	    public String toString()
	    {
	        return modelNum;
	    }

	    public String getKey()
	    {
	        return modelNum;
	    }

	}
	
    private void MainPanel() {
    	
        mainPanel = new JPanel();
       
        
        JLabel instrModelLabel = new JLabel("Instrument Model: ");
        JComboBox instrModelCombo = new JComboBox();
        instrModelCombo.addItem(new ComboItem("Model Number 1"));
        instrModelCombo.addItem(new ComboItem("Model Number 2"));
        instrModelCombo.addItem(new ComboItem("Model Number 3"));

        JLabel commentsLabel = new JLabel("Additional Comments About the Instrument: ");
        JTextArea commentsTextArea = new JTextArea(10,20); 
        commentsTextArea.setEditable(true);
        
        mainPanel.add(instrModelLabel);
        mainPanel.add(instrModelCombo);
        mainPanel.add(commentsLabel);
        mainPanel.add(commentsTextArea);
        submitButton();
        
    }

    
	public void backButton() {
		visitPanel = new JPanel();
		
		JButton newVisit = new JButton("Back");
		
		visitPanel.add(newVisit);

		
		newVisit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				ownReference.setVisible(false);
				try {
					CreateInterface cif = new CreateInterface();
//					cif.setVM(ownReference); //
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		
		
	}
	
	// needs to be modified
	public void submitButton() {
		
		visitPanel = new JPanel();
		
		JButton newVisit = new JButton("Submit");
		
		visitPanel.add(newVisit);

		
		newVisit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				ownReference.setVisible(false);
				try {
					CreateInterface cif = new CreateInterface();
//					cif.setVM(ownReference); //
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		
		
	}
	
	public void setOwnReference(Instrument ownReference) {
		this.ownReference = ownReference;
		
	}
}
