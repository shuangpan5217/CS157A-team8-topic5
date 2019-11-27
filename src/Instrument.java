
	import java.awt.Dimension;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.Color;
	import java.awt.Container;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.SQLException;
	import java.sql.Statement;

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


	public class Instrument extends JFrame {
		private Connection conn;
		private Statement stmt;
		private Instrument ownReference;
		private JPanel InstrPanel;
		private static final long serialVersionUID =1L;
		private JPanel comments;
		private LocalDate localDate;
		private JPanel visitPanel;
		private JPanel upperPanel, mainPanel;
		private JComboBox<Integer> instrModelCombo;
		private JTextField lastName, firstName, date, thc, instrCat, instrType, addComments, visitIDField;
		final Container contentPane = getContentPane();
		private Integer thcInt;
		private int model; 
		private String comment;
		private CreateInterface createReference;


		public Instrument() {
			visitPanel = new JPanel();

			UpperPanel();
			MainPanel();

			add(upperPanel);
			add(mainPanel);
			add(visitPanel);

			setTitle("Instrument Details");
			setPreferredSize(new Dimension(450, 550)); //
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			pack();
			setVisible(true);
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
//			lastName.addActionListener(this);
//			firstName.addActionListener(this);


			JLabel dateLabel = new JLabel("Date: ");
			date = new JTextField();
			date.setText(LocalDate.now().toString());


			JLabel THCLabel = new JLabel("THC#: ");
			thc = new JTextField(6);
	//		int thcInt = Integer.parseInt(thc.getText());
//			thc.addActionListener(this);// to be modified

			JLabel category = new JLabel("Instrument Category: ");
			instrCat = new JTextField(2);
//			instrCat.addActionListener(this);
			instrCat.setEditable(false);


			JLabel type = new JLabel("Instrument Type: ");
			instrType = new JTextField(2);
//			instrType.addActionListener(this);
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

			setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		}

		private void MainPanel() {

			mainPanel = new JPanel();


			JLabel instrModelLabel = new JLabel("Instrument Model: ");

			Integer modelNumber[]= {1,2, 3,4};
			instrModelCombo = new JComboBox<Integer>(modelNumber);

			JLabel commentsLabel = new JLabel("Additional Comments About the Instrument: ");
			addComments = new JTextField(20); 
			addComments.setEditable(true);

			mainPanel.add(instrModelLabel);
			mainPanel.add(instrModelCombo);
			mainPanel.add(commentsLabel);
			mainPanel.add(addComments);
			saveButton();
			cancelButton();

		}

		private void connectToDataBase() throws SQLException {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

			// Connect to the database
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=UTC","ren-2", "7sM}c+FG"); 

			// Create a Statement
			stmt = conn.createStatement ();
		}


		// take back to add/edit patient interface
		public void cancelButton() {

			JButton cancelButton = new JButton("Cancel");

			visitPanel.add(cancelButton);

			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					ownReference.dispose();
				}
			});
		}
		public void saveButton() {

			JButton saveButton = new JButton("Save");
			visitPanel.add(saveButton);

			saveButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					model = (int) instrModelCombo.getSelectedItem();
					comment = addComments.getText();
					System.out.println(addComments.getText());
					createReference.setInstrumentData(comment, model);
					ownReference.dispose();
				}
				});
		}

	public void setOwnReference(Instrument ownReference) {
		this.ownReference = ownReference;

	}
	
	public void setCreateReference(CreateInterface refer) {
		this.createReference = refer;
	}

	
	public void setUpperPanel(String firstName, String lastName, int thc, String instrumentType, int instrumentCate) {
		this.lastName.setText(lastName);
		this.firstName.setText(firstName);
		this.thc.setText(Integer.toString(thc));
		instrCat.setText(Integer.toString(instrumentCate));
		instrType.setText(instrumentType);
	}
}
