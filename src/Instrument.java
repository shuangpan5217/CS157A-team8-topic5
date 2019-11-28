  
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

/**
 * Set the insgtrument interface
 * @author shuangpan, Ren
 *
 */
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
		private JPanel categoryPanel;
		private JPanel modelPanel;
		private JPanel typePanel;
		private String categoryName;
		private String categoryDescription;
		private int typeID;
		private String typeDescription;
		private String modelName;
		private String modelDescription;

		JTextField cn;
		JTextField cnDescription;
		
		JComboBox<Integer> tn;
		JTextField tnDescription;
		
		JTextField mn = new JTextField(10);
		JTextField mnDescription = new JTextField(15);


		/**
		 * Collect all data and build the instrument interface.
		 */
		public Instrument() {
			visitPanel = new JPanel();

			UpperPanel();
			MainPanel();

			add(upperPanel);
			add(mainPanel);
			add(categoryPanel);
			add(modelPanel);
			add(typePanel);
			add(visitPanel);

			setTitle("Instrument Details");
			setPreferredSize(new Dimension(450, 550)); //
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			pack();
			setVisible(true);
		}

		/**
		 * Build the inactive upper panel.
		 */
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

		/**
		 * Build the main panel to hold other information.
		 */
		private void MainPanel() {

			mainPanel = new JPanel();
			categoryPanel = new JPanel();
			typePanel = new JPanel();
			modelPanel = new JPanel();


			JLabel instrModelLabel = new JLabel("Instrument Model: ");

			Integer modelNumber[]= {1,2, 3,4};
			instrModelCombo = new JComboBox<Integer>(modelNumber);

			JLabel commentsLabel = new JLabel("Instrument comments: ");
			addComments = new JTextField(20); 
			addComments.setEditable(true);
			
			JLabel categoryName = new JLabel("Category Name: ");
			cn = new JTextField(10);
			JLabel cnDesLabel = new JLabel("Category description: ");
			cnDescription = new JTextField(15);
			
			JLabel typeName = new JLabel("Type ID: ");
			tn = new JComboBox<Integer>(new Integer[]{1, 2, 3, 4, 5, 6, 7 ,8 ,9, 10}); 
			JLabel tnDesLabel = new JLabel("Type description: ");
			tnDescription = new JTextField(15);
			
			JLabel modelName = new JLabel("Model Name: ");
			mn = new JTextField(10);
			JLabel mnDesLabel = new JLabel("Model description: ");
			mnDescription = new JTextField(15);

			mainPanel.add(commentsLabel);
			mainPanel.add(addComments);
			
			categoryPanel.add(categoryName);
			categoryPanel.add(cn);
			categoryPanel.add(cnDesLabel);
			categoryPanel.add(cnDescription);
			typePanel.add(typeName);
			typePanel.add(tn);
			typePanel.add(tnDesLabel);
			typePanel.add(tnDescription);
			modelPanel.add(instrModelLabel);
			modelPanel.add(instrModelCombo);
			modelPanel.add(modelName);
			modelPanel.add(mn);
			modelPanel.add(mnDesLabel);
			modelPanel.add(mnDescription);
			saveButton();
			cancelButton();

		}

		/*
		private void connectToDataBase() throws SQLException {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

			// Connect to the database
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=UTC","ren-2", "7sM}c+FG"); 

			// Create a Statement
			stmt = conn.createStatement ();
		}

	*/
		// take back to add/edit patient interface
		/**
		 * Create cancel button and don't save the data.
		 */
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
		/**
		 * Get all input data and send back to the main interface
		 */
		public void saveButton() {

			JButton saveButton = new JButton("Save");
			visitPanel.add(saveButton);

			saveButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					model = (int) instrModelCombo.getSelectedItem();
					comment = addComments.getText();
					categoryName = cn.getText();
					categoryDescription = cnDescription.getText();
					typeID = (int) tn.getSelectedItem();
					typeDescription = tnDescription.getText();
					modelName = mn.getText();
					modelDescription = mnDescription.getText();
					
					createReference.setInstrumentData(comment, model, categoryName, categoryDescription,typeID,
							typeDescription, modelName, modelDescription);
					ownReference.dispose();
				}
				});
		}

	/**
	 * Get the own reference to disposal
	 * @param ownReference own reference
	 */
	public void setOwnReference(Instrument ownReference) {
		this.ownReference = ownReference;

	}
	
	/**
	 * Use it for sending back data
	 * @param refer CreateInterface type reference
	 */
	public void setCreateReference(CreateInterface refer) {
		this.createReference = refer;
	}

	/**
	 * set the information at the upper panel
	 * @param firstName patient first name
	 * @param lastName patient last name
	 * @param thc patient's thc number
	 * @param instrumentType instrument type
	 * @param instrumentCate instrument category id
	 */
	public void setUpperPanel(String firstName, String lastName, int thc, String instrumentType, int instrumentCate) {
		this.lastName.setText(lastName);
		this.firstName.setText(firstName);
		this.thc.setText(Integer.toString(thc));
		instrCat.setText(Integer.toString(instrumentCate));
		instrType.setText(instrumentType);
	}
}
