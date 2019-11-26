
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;


import javax.swing.BorderFactory;
import java.time.LocalDate;
import javax.swing.JComboBox;


public class Instrument extends JFrame implements ActionListener {
	
	private Instrument ownReference;
	private JPanel InstrPanel;
	private static final long serialVersionUID =1L;
	private JPanel comments;
	private LocalDate localDate;
	private JPanel visitPanel;
	private JPanel upperPanel, mainPanel;
	private JComboBox instrModelCombo;
	private JTextField lastName, firstName, date, thc, instrCat, instrType, addComments, visitIDField;
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
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    pack();
	    setVisible(true);
	}

	private void CreateVisitPanel() {
		visitPanel = new JPanel();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		visitPanel.setBorder(blackline);
		
		JLabel visitIDLabel = new JLabel("Visit ID: ");
		visitIDField = new JTextField();
		visitIDField.setText("1");  //to be modified
		
		JLabel dateLabel = new JLabel("Date: ");
		JTextField dateField = new JTextField();
		dateField.setText(LocalDate.now().toString());

		
		JLabel THCLabel = new JLabel("THC#: ");
		thc = new JTextField(6); // to be modified
		thc.setEditable(false);
		
		JLabel visitNumberLabel = new JLabel("Visit Number: ");
		JTextField visitNumberField = new JTextField(3); 
		visitNumberField.setText("5");
		
		visitPanel.add(visitIDLabel);
		visitPanel.add(visitIDField);
		visitPanel.add(dateLabel);
		visitPanel.add(dateField);
		visitPanel.add(THCLabel);
		visitPanel.add(thc);
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
        lastName = new JTextField(10);
        firstName = new JTextField(10);
        lastName.setEditable(false);
        firstName.setEditable(false);
        lastName.addActionListener(this);
        firstName.addActionListener(this);
 
        
        JLabel dateLabel = new JLabel("Date: ");
        date = new JTextField();
        date.setText(LocalDate.now().toString());

        
        JLabel THCLabel = new JLabel("THC#: ");
        thc = new JTextField(6);
        thc.addActionListener(this);// to be modified
        
        JLabel category = new JLabel("Instrument Category: ");
        instrCat = new JTextField(2);
        instrCat.addActionListener(this);
        instrCat.setEditable(false);

        
        JLabel type = new JLabel("Instrument Type: ");
        instrType = new JTextField(2);
        instrType.addActionListener(this);
        instrType.setEditable(false);

        upperPanel.add(patientInfoLabel);
        upperPanel.add(lastNameLabel);
        upperPanel.add(lastName);
        upperPanel.add(firstNameLabel);
        upperPanel.add(firstName);
        upperPanel.add(dateLabel);
        upperPanel.add(date);
        upperPanel.add(THCLabel);
        upperPanel.add(thc);
        upperPanel.add(category);
        upperPanel.add(instrCat);
        upperPanel.add(type);
        upperPanel.add(instrType);
        
        setTitle("Patient's Information:");
        MainPanel();
        
        setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        add(upperPanel);
        add(mainPanel);
               
    }
	
    private void MainPanel() {
    	
        mainPanel = new JPanel();
       
        
        JLabel instrModelLabel = new JLabel("Instrument Model: ");
        
        String modelNumber[]= {"1","2", "3","4"};
        instrModelCombo = new JComboBox(modelNumber);

        JLabel commentsLabel = new JLabel("Additional Comments About the Instrument: ");
        addComments = new JTextField(20); 
        addComments.setEditable(true);
        addComments.addActionListener(this);
        
        mainPanel.add(instrModelLabel);
        mainPanel.add(instrModelCombo);
        mainPanel.add(commentsLabel);
        mainPanel.add(addComments);
        submitButton();
        
    }

    // take back to add/edit patient interface
	public void backButton() {
		visitPanel = new JPanel();
		
		JButton newVisit = new JButton("Cancel");
		
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
		
		JButton newVisit = new JButton("Save");
		
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
	
	public void actionPerformed(ActionEvent actionEvent) {
		String lastN = lastName.getText();
		String firstN = firstName.getText();
		String date1 = date.getText();
		String instrumentCategory = instrCat.getText();
		String instrumentType = instrType.getText();
		String additionalComments = addComments.getText();
		String visitID = visitIDField.getText();
		String modelNo = (String)instrModelCombo.getSelectedItem();
		String thcNo = thc.getText();
		
		//sql statements
		try {

		
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("error: "+e);
		}
	}
}
