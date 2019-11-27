import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ViewEditVisit extends JFrame{
	/**
	 * Default serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private Statement stmt;
	private JTable jtable;
	private JPanel tablePanel;
	private JButton deleteRow;
	private JPanel functionPanel;
	private DefaultTableModel tableModel;
	private JPanel filterByDatePanel;
	private JPanel filterByPatientPanel;
	private JComboBox<String> sortTableBy;
	private JPanel sort;
	public ViewEditVisit() throws SQLException { 
		connectToDataBase();
		createTablePanel();
		editFunction();
		
	    final Container contentPane = getContentPane();
	    setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	    
		add(tablePanel);
		add(sort);
		add(filterByDatePanel);
		add(filterByPatientPanel);
		add(functionPanel);
		
		setTitle("View/Edit tables");
//		setPreferredSize(new Dimension(450, 350));
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	
	private void createTablePanel() throws SQLException {
		tablePanel = new JPanel();
		
        stmt = (Statement) conn.createStatement();
        String query = "select thc, first_name, last_name\n" + 
        		"from patient";
        ResultSet rs = stmt.executeQuery(query);

        rs.beforeFirst();
        
        String[] columnNames = {"THC", "LAST NAME", "FIRST NAME"};
        tableModel = new DefaultTableModel(columnNames, 0);

        while (rs.next()) {
            Integer thc = rs.getInt("THC");
            String ln = rs.getString("last_name");
            String fn = rs.getString("first_name");

            String[] data = {Integer.toString(thc), ln, fn} ;

            // and add this row of data into the table model
            tableModel.addRow(data);
        }
        jtable = new JTable(tableModel);
        jtable.setGridColor(Color.BLACK);
 //       jtable.setShowGrid(true);
        JScrollPane scroll = new JScrollPane(jtable);
        tablePanel.add(scroll);
    }
	
	private void editFunction() {
		functionPanel = new JPanel();
		sort = new JPanel();
		
		JLabel sortTable = new JLabel("Sort Table: ");
		sortTableBy = new JComboBox<String>(new String[]{"Visit ID", "Date", "Instrument"});
		sort.add(sortTable);
		sort.add(sortTableBy);
		
		sortTableBy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String item = (String) sortTableBy.getSelectedItem();
				try {
					if(item.equals("Visit ID")) {
			        	String query = "select * from patient order by Visit_ID";
						ResultSet rs = stmt.executeQuery(query);
					}
					else if(item.equals("Date")) {
			        	String query = "select * from patient order by date";
						ResultSet rs = stmt.executeQuery(query);
					}
					else {
			        	String query = "select * from patient order by instrument_type.name";
						ResultSet rs = stmt.executeQuery(query);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		JLabel filterByDate = new JLabel("Filter by Date: ");
		JTextField filterDate = new JTextField(12);
		filterDate.setText("yyyy-mm-dd");
		filterDate.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if(filterDate.getText().equals("yyyy-mm-dd"))
					filterDate.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(filterDate.getText().length() == 0)
					filterDate.setText("yyyy-mm-dd");
			}
			
		});
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		        try {
		//        	System.out.println(filterDate.getText());
		        	String query = "select * from patient " 
			        		+ "where Date(date) = " + "'" +filterDate.getText() + "'";
					ResultSet rs = stmt.executeQuery(query);
					//to be done
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		JLabel filterByPatient = new JLabel("Filter by Patient: ");
		JLabel last = new JLabel("Last Name: ");
		JTextField lastName = new JTextField(6);
		JLabel first = new JLabel("First Name: ");
		JTextField firstName = new JTextField(6);
		JButton submit2 = new JButton("Submit");
		submit2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		        try {
		        	String query = "select * from patient " 
			        		+ " where last_name = " + "'" +
		        			lastName.getText() + "' and" + " first_name = " + "'" + firstName.getText() + "'";
					ResultSet st = stmt.executeQuery(query);
					//to be done
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		deleteRow = new JButton("Delete the selected row");
		deleteRow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = jtable.getSelectedRows();
				int j = 0;
				for(int i = 0; i < rows.length; i++) {
			        try {
			        	String query = "delete from patient " 
				        		+ "where thc = " + jtable.getValueAt(i, j++) + " and last_name = " + "'" +
			        			jtable.getValueAt(i, j++) + "' and" + " first_name = " + "'" + jtable.getValueAt(i, j++) + "'";
						stmt.executeUpdate(query);
						j = 0;
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				for(int i = 0; i < rows.length; i++) {
					tableModel.removeRow(i);
				}
			}
			
		});
		filterByDatePanel = new JPanel();
		filterByDatePanel.add(filterByDate);
		filterByDatePanel.add(filterDate);
		filterByDatePanel.add(submit);
		
		filterByPatientPanel = new JPanel();
		filterByPatientPanel.add(filterByPatient);
		filterByPatientPanel.add(last);
		filterByPatientPanel.add(lastName);
		filterByPatientPanel.add(first);
		filterByPatientPanel.add(firstName);
		filterByPatientPanel.add(submit2);
		
		functionPanel.add(deleteRow);
	}
}
