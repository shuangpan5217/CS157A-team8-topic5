  
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.sql.*;
import java.time.LocalDate;
import java.util.Random;


/**
 * A class to create the main interface of adding new visit
 * @author shuangpan
 *
 */
public class CreateInterface extends JFrame {

    /**
     * static final serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private CreateInterface ownReference;
    private Connection conn;
    private Statement stmt;
    private JPanel visitPanel;
    private JTextArea commentArea;
    private JPanel commentPanel;
    private JPanel others;
    private JButton instrument;
    private JButton REM;
    private JButton counsel;
    private JPanel middlePanel;
    private JComboBox<Integer> categoryField;
    private JComboBox<String> ins;
    private JCheckBox rem;
    private JTextField THCField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPanel lowerPanel;
    private JComboBox<Integer> instruCateField;
    private JTextField visitNumberField;
    private JComboBox<String> fu;
    private Random random;
//	private VisitMain vm;
    
    private JTextField visitIDField;
    
    private String comment;
    private int instrumentModel;
	private String categoryName, categoryDescription;
	private int typeID; private String typeDescription;
	private String modelName, modelDescription;

    private String newTHC;
    private String visitID;

    private int freqREI, tRSI, mRSI, tRSPLI, tRSLI, mRSPLI, mRSLI;
    private int freqLEI, tLSI, mLSI, tLSPLI, tLSLI, mLSPLI, mLSLI;

    private String counselingComment;

    /**
     * Constructor collect data from the frame
     * @throws SQLException connect to database
     */
    public CreateInterface() throws SQLException {
        connectToDataBase();

        final Container contentPane = getContentPane();
        setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        createVisitPanel();
        createCommentsArea();
        createMiddlePanel();
        createOtherButtons();
        createLowerPanel();
        add(visitPanel);
        add(middlePanel);
        add(commentPanel);
        add(others);
        add(lowerPanel);

        setTitle("Visit");
//		setPreferredSize(new Dimension(450, 350));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * Connect to mysql database
     * @throws SQLException sql exception
     */
    private void connectToDataBase() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        // Connect to the database
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cs157A?useTimezone=true&serverTimezone=UTC","shuangpan", "FEIfei5217?");
        //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "1486630878Su");

        // Create a Statement
        stmt = conn.createStatement();
    }

    /**
     * Create visit panel (upper panel)
     */
    private void createVisitPanel() {
        visitPanel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        visitPanel.setBorder(blackline);

        JLabel visitIDLabel = new JLabel("Visit ID: ");
        visitIDField = new JTextField(6);
        visitIDField.setEditable(false);

        JLabel dateLabel = new JLabel("Date: ");
        JTextField dateField = new JTextField();
        dateField.setText(LocalDate.now().toString());
        dateField.setEditable(false);

        JLabel firstName = new JLabel("First Name: ");
        firstNameField = new JTextField(6);

        JLabel lastName = new JLabel("Last Name: ");
        lastNameField = new JTextField(6);

        JLabel THCLabel = new JLabel("THC#: ");
        THCField = new JTextField(6);
        THCField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (THCField.getText().length() != 0) {
                    newTHC = THCField.getText();
                    int thc = Integer.parseInt(THCField.getText());
                    String query = "select last_name, first_name from patient where thc = " + thc;
                    try {
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            lastNameField.setText(rs.getString("last_name"));
                            firstNameField.setText(rs.getString("first_name"));
                        }

                        rs = stmt.executeQuery("select max(visit_id) as id from visit");

                        if (rs.next() && rs.getString("id") == null) {
                            visitIDField.setText("1");
                        } else {
                            visitIDField.setText(Integer.toString(rs.getInt("id") + 1));
                            visitID = visitIDField.getText();
                        }

                        rs = stmt.executeQuery("select max(visit_nr) as nr from visit where date = '" + LocalDate.now().toString() + "'");
                        if (rs.next() && rs.getString("nr") == null) {
                            visitNumberField.setText("1");
                        } else {
                            visitNumberField.setText(Integer.toString(rs.getInt("nr") + 1));
                        }

                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }

        });
        THCField.setEditable(true);

        JLabel visitNumberLabel = new JLabel("Visit Number: ");
        visitNumberField = new JTextField(3);
        visitNumberField.setText("5");
        visitNumberField.setEditable(false);

        visitPanel.add(visitIDLabel);
        visitPanel.add(visitIDField);
        visitPanel.add(dateLabel);
        visitPanel.add(dateField);
        visitPanel.add(firstName);
        visitPanel.add(firstNameField);
        visitPanel.add(lastName);
        visitPanel.add(lastNameField);
        visitPanel.add(THCLabel);
        visitPanel.add(THCField);
        visitPanel.add(visitNumberLabel);
        visitPanel.add(visitNumberField);
    }

    /**
     * Create comment panel for comment in the visit
     */
    private void createCommentsArea() {
        commentPanel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        commentPanel.setBorder(blackline);

        JLabel commentLabel = new JLabel("Comment: ");
        commentArea = new JTextArea(2, 80);
        commentArea.setEditable(true);

        commentPanel.add(commentLabel);
        commentPanel.add(commentArea);
    }

    /*
	public void setVM(VisitMain vm) {
		this.vm = vm;
	}
     */
    
    /**
     * Crate the middle panel for category, type and other information
     */
    private void createMiddlePanel() {
        middlePanel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        middlePanel.setBorder(blackline);

        JLabel categoryLabel = new JLabel("Patient Category: ");
        categoryField = new JComboBox<Integer>(new Integer[]{0, 1, 2, 3, 4});
        JLabel instruCategory = new JLabel("Instrument Category");
        instruCateField = new JComboBox<Integer>(new Integer[]{1, 2, 3, 4});
        JLabel instruLabel = new JLabel("Instrument type");
        ins = new JComboBox<String>(new String[]{"V", "GS", "GH", "HA", ""});
        JLabel remLabel = new JLabel("REM");
        rem = new JCheckBox();
        JLabel follow = new JLabel("FU: ");
        fu = new JComboBox<String>(new String[]{"yes", "no"});

        middlePanel.add(categoryLabel);
        middlePanel.add(categoryField);
        middlePanel.add(instruLabel);
        middlePanel.add(ins);
        middlePanel.add(instruCategory);
        middlePanel.add(instruCateField);
        middlePanel.add(remLabel);
        middlePanel.add(rem);
        middlePanel.add(follow);
        middlePanel.add(fu);
    }

    /**
     * Create other buttons to add other information including instrument, rem and couunseling.
     */
    private void createOtherButtons() {
        others = new JPanel();
        instrument = new JButton("Instrument Details");
        instrument.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("unused")
                Instrument inst = new Instrument();
                inst.setOwnReference(inst);
                inst.setCreateReference(ownReference);
                inst.setUpperPanel(firstNameField.getText(), lastNameField.getText(), Integer.parseInt(THCField.getText()),
                        (String) ins.getSelectedItem(), (int) instruCateField.getSelectedItem());
            }

        });
        REM = new JButton("REM Details");
        REM.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Main newREM = new Main();
                newREM.setOwnReference(newREM);
                newREM.setCreateReference(ownReference);
                newREM.remPanel(newTHC.toString(),
                        (firstNameField.getText() + " " 
                                + lastNameField.getText()),
                         LocalDate.now().toString(),
                         categoryField.getSelectedItem().toString(),
                         ins.getSelectedItem().toString());
            }

        });
        counsel = new JButton("Counseling Details");
        counsel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Counseling newCounsel = new Counseling();
                newCounsel.setOwnReference(newCounsel);
                newCounsel.setCreateReference(ownReference);
                newCounsel.clPanel(newTHC, (firstNameField.getText() + " "
                        + lastNameField.getText()),
                         visitID,
                        LocalDate.now().toString(), 
                        fu.getSelectedItem().toString());
            }

        });

        others.add(instrument);
        others.add(REM);
        others.add(counsel);
    }

    /**
     * Create save and canel button
     */
    public void createLowerPanel() {
        lowerPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	int visitID = Integer.parseInt(visitIDField.getText());
            	String now = LocalDate.now().toString();
            	int visitNumber = Integer.parseInt(visitNumberField.getText());
            	int patientCategory = (int) categoryField.getSelectedItem();
            	String insType = (String) ins.getSelectedItem();
            	int insCate = (int) instruCateField.getSelectedItem();
            	String follow = (String) fu.getSelectedItem();
            	
            	/*
                private String comment;
                private int instrumentModel;
            	private String categoryName, categoryDescription;
            	private String typeName, typeDescription;
            	private String modelName, modelDescription;

                private String newTHC;
                private String visitID;

                private int freqREI, tRSI, mRSI, tRSPLI, tRSLI, mRSPLI, mRSLI;
                private int freqLEI, tLSI, mLSI, tLSPLI, tLSLI, mLSPLI, mLSLI;

                private String counselingComment;*/
            	
            	try {
            		// insert all data to the database
            		random = new Random();
					int x = random.nextInt(1000);
					int y = random.nextInt(1000);
					int z = random.nextInt(1000);
					int a = random.nextInt(1000);
					int b = random.nextInt(1000);
					int c = random.nextInt(1000);
					int d = random.nextInt(1000);
					char q = (char)(65 + random.nextInt(60));
					stmt.executeUpdate("insert into instrument_model values(" + instrumentModel + ", '" + modelName + "', '" + modelDescription + "')");
					stmt.executeUpdate("insert into instrument_category values(" + insCate + ", '" + categoryName + "', '" + categoryDescription + "')");
					stmt.executeUpdate("insert into instrument_types values(" + typeID + ", '" + insType + "', '" + typeDescription + "'" + ", " + insCate + ")");
					stmt.executeUpdate("insert into visit values(" + visitID + ", " + visitNumber + ", '" + now + "', '" + commentArea.getText() + "', " + 
					Integer.parseInt(THCField.getText()) + ", '" + follow + "')");
					stmt.executeUpdate("insert into counseling values (" + x + ", '" + q + "', '" + counselingComment + "', " + visitID + ")");
					stmt.executeUpdate("insert into instrument values(" + a + ", '" + comment + "', " + insCate + ", " + instrumentModel + ", " + typeID + ", " + 
					visitID + ", " + Integer.parseInt(THCField.getText()) + ", " + "'ear piece')");
					stmt.executeUpdate("insert into rem values(" + random.nextInt(10) + ", 'no comment', " + freqREI + ", " + tRSI + ", " + 
					mRSI + ", " + tRSPLI + ", " + tRSLI + ", " + mRSPLI + ", " + mRSLI + ", " + freqLEI + ", " + 
							tLSI + ", " + mLSI + ", " + tLSPLI + ", " + tLSLI + ", " + mLSPLI + ", " + mLSLI + ", " + y + ", " + z + ", " + a + ", " + visitID + ")");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            	/*
    			"insert into INSTRUMENT_MODEL values (9,'model1','onemodeltest')",

    			"insert into INSTRUMENT_CATEGORY values (8,'ca','onemcategory test')",

    			"insert into INSTRUMENT_TYPES values (4,'ca','this is onemcategory test', 8)",

    			"insert into VISIT values (13, 1, '2019-05-09','this is the first visit of patient testone', 1,'no')",

    			"insert into COUNSELING values(123, '1', 'patient testone counsel', 13)",

    			"insert into INSTRUMENT values(1,'this is an instrument for patient testonee', 8, 9, 4 , 13, 1, 'ear piece')",

    			"insert into REM values(987, 'this is testone REM', 1.1 ,2.1 ,3.1,4.1 ,5.1 ,6.1 ,7.1, 8.1 ,9.1 ,10.1 ,11.1 ,12.1, 13.1 , 14.1 ,15.1 ,16.1 , 1, 13)",
            	*/
                ownReference.dispose();

                //
                System.out.println("" + freqREI + tRSI + mRSI + tRSPLI
                        + tRSLI + mRSPLI + mRSLI + freqLEI + tLSI + mLSI
                        + tLSPLI + tLSLI + mLSPLI + mLSLI);
                //
                System.out.println("Counseling comment = " + counselingComment);
            }

        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ownReference.dispose();
            }

        });

        lowerPanel.add(saveButton);
        lowerPanel.add(cancelButton);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    /**
     * get own reference for disposal
     * @param reference
     */
    public void setOwnReference(CreateInterface reference) {
        this.ownReference = reference;
    }

    /**
     * Get all data from instrument details
     * @param comment instrument comment
     * @param instrumentModel instrument model
     * @param categoryName instrument category name
     * @param categoryDescription instrument category description
     * @param typeID instrument type ID
     * @param typeDescription instrument type description
     * @param modelName instrument model name
     * @param modelDescription instrument model description
     */
    public void setInstrumentData(String comment, int instrumentModel,
    		String categoryName, String categoryDescription,
    		int typeID, String typeDescription, 
    		String modelName, String modelDescription) {
    	
        this.instrumentModel = instrumentModel;
        this.comment = comment;
        
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        
        this.typeID = typeID;
        this.typeDescription = typeDescription;
        
        this.modelName = modelName;
        this.modelDescription = modelDescription;
    }

    /**
     * Get all information from REM
     * @param a freqREI
     * @param b mRSI
     * @param c tRSI
     * @param d tRSPLI
     * @param e tRSLI
     * @param f mRSPLI
     * @param g mRSLI
     * @param h freqLEI
     * @param i tLSI
     * @param j mLSI
     * @param k tLSPLI
     * @param l tLSLI
     * @param m mLSPLI
     * @param n  mLSLI
     */
    public void setREMData(int a, int b, int c, int d,
            int e, int f, int g, int h, int i,
            int j, int k, int l, int m, int n) {
        this.freqREI = a;
        this.tRSI = b;
        this.mRSI = c;
        this.tRSPLI = d;
        this.tRSLI = e;
        this.mRSPLI = f;
        this.mRSLI = g;

        this.freqLEI = h;
        this.tLSI = i;
        this.mLSI = j;
        this.tLSPLI = k;
        this.tLSLI = l;
        this.mLSPLI = m;
        this.mLSLI = n;

    }

    /**
     * Get comment from counseling
     * @param comment counseling comment
     */
    public void setCounselingComment(String comment) {
        this.counselingComment = comment;
    }
}
