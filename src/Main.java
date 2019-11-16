import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.sql.*;
import java.time.LocalDate;

public class Main extends JFrame{
	/**
	 * static final serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private Statement stmt;
	private JPanel visitPanel;
	private LocalDate localDate;
	private JTextArea commentArea;
	private JPanel comment;
        
        private JPanel remPanel, remMain, clPanel;
        
	final Container contentPane = getContentPane();
        
        public static void main(String[] args) {
            new Main();
        }
        
	public Main() {//throws SQLException {
            //connectToDataBase();
		
	    //final Container contentPane = getContentPane();
	    //setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	    
	    //CreateVisitPanel();
	    //CreateCommentsArea();
	    //add(visitPanel);
           
            remPanel();
            //remMain();
            
            //contentPane.add(remPanel);
            //add(remPanel);
            //add(remMain);
            //add(visitPanel);
	    //add(comment);
	    
            //setTitle("Visit");
            setPreferredSize(new Dimension(450, 300));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setVisible(true);
	}
        
	/*
	private void connectToDataBase() throws SQLException {
	    DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

	    // Connect to the database
	    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157A?useTimezone=true&serverTimezone=UTC","shuangpan", "FEIfei5217?"); 

	    // Create a Statement
	    stmt = conn.createStatement ();
	}
*/	

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
        
        private void remPanel() {
            remPanel = new JPanel();
            Border blackline = BorderFactory.createLineBorder(Color.black);
            remPanel.setBorder(blackline);
            
            JLabel nameLabel = new JLabel("Patien Name: ");
            JTextField nameField = new JTextField();
            nameField.setText("Mike");
            nameField.setEditable(false);
            
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
            
            remPanel.add(nameLabel);
            remPanel.add(nameField);
            remPanel.add(dateLabel);
            remPanel.add(dateField);
            remPanel.add(THCLabel);
            remPanel.add(THCField);
            remPanel.add(category);
            remPanel.add(categoryField);
            remPanel.add(type);
            remPanel.add(typeField);
            
            setTitle("REM");
            remMain();
            
            setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
            
            add(remPanel);
            add(remMain);
                   
        }
        
        private void remMain() {
            remMain = new JPanel();
            
            JLabel reLabel = new JLabel("Right Ear: ");
            JLabel leLabel = new JLabel("Left Ear: ");
            
            JTextField reText = new JTextField(10);
            JTextField leText = new JTextField(10);
            
            reText.setEditable(true);
            leText.setEditable(true);
            
            remMain.add(reLabel);
            remMain.add(reText);
            remMain.add(leLabel);
            remMain.add(leText);
            
        }
	
        
        
	private void CreateCommentsArea() {
		comment = new JPanel();
		JLabel commentLabel = new JLabel("Comment: ");
		commentArea = new JTextArea(10, 20);
		commentArea.setEditable(true);
		
		comment.add(commentLabel);
		comment.add(commentArea);
	}
}