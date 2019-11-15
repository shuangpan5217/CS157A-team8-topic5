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

public class CreateInterface extends JFrame{
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
	
	public CreateInterface() throws SQLException {
		connectToDataBase();
		
	    final Container contentPane = getContentPane();
	    setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	    
	    CreateVisitPanel();
	    CreateCommentsArea();
	    add(visitPanel);
	    add(comment);
	    
		setTitle("Visit");
		setPreferredSize(new Dimension(450, 300));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setVisible(true);
	}
	
	private void connectToDataBase() throws SQLException {
	    DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

	    // Connect to the database
	    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157A?useTimezone=true&serverTimezone=UTC","shuangpan", "FEIfei5217?"); 

	    // Create a Statement
	    stmt = conn.createStatement ();
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
	
	private void CreateCommentsArea() {
		comment = new JPanel();
		JLabel commentLabel = new JLabel("Comment: ");
		commentArea = new JTextArea(10, 20);
		commentArea.setEditable(true);
		
		comment.add(commentLabel);
		comment.add(commentArea);
	}
}
