import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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

/**
 * A class to build the view and edit interface
 * @author shuangpan
 *
 */
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
	private JScrollPane scroll;
	
	/**
	 * Constructor to collect data 
	 * @throws SQLException sql exception
	 */
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
	
	/**
	 * Connect to mysql database
	 * @throws SQLException sql exception
	 */
	private void connectToDataBase() throws SQLException {
	    DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

	    // Connect to the database
	    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157A?useTimezone=true&serverTimezone=UTC","shuangpan", "FEIfei5217?"); 

	    // Create a Statement
	    stmt = conn.createStatement ();
	}
	
	/**
	 * Get data from database and display them using JTable
	 * @throws SQLException
	 */
	private void createTablePanel() throws SQLException {
		tablePanel = new JPanel();
        stmt = (Statement) conn.createStatement();
        String query = "select visit_id as ID, date as Date, concat(first_name, " + "' '" + ", last_name) as Patient, visit_nr as "
        		+ "Visit, instrument_types.name as Instr, rem_id as REM,"
        		+ " followupvisit_type as FU, visit.comments as Comments "
        		+ "from patient, visit, REM, instrument, instrument_types, instrument_model "
        		
        		+ "where patient.THC = visit.patient_thc and "
        		
        		+ "rem.VISIT_Visit_ID = instrument.VISIT_Visit_ID and rem.visit_visit_id = visit_id and rem.instrument_instr_id = instrument.instr_id and "
        		
        		+ "instrument.VISIT_Visit_ID = visit_id and `instrument model_im_id` = im_id and `instrument types_it_id` = it_id and instrument.visit_patient_thc "
        		+ "= patient_thc";
        		
        		
        		
        //		+ "and `instrument category_ic_id` = ic_id`";
        ResultSet rs = stmt.executeQuery(query);

        rs.beforeFirst();
        
        String[] columnNames = {"ID", "Date", "Patient", "Visit", "Instr", "REM", "FU", "Comments"};
        tableModel = new DefaultTableModel(columnNames, 0);

        while (rs.next()) {
        	
            Integer id = rs.getInt(1);
            String date = rs.getString(2);
            String Patient = rs.getString(3);
            Integer visit_nr = rs.getInt(4);
            String instr = rs.getString(5);
            Integer rem_id = rs.getInt(6);
            String fu = rs.getString(7);
            String comment = rs.getString(8);

            String[] data = {Integer.toString(id), date, Patient, Integer.toString(visit_nr), instr, Integer.toString(rem_id), fu, comment} ;

            // and add this row of data into the table model
            tableModel.addRow(data);
        }
        jtable = new JTable(tableModel);
        jtable.setGridColor(Color.BLACK);
        jtable.setPreferredScrollableViewportSize(new Dimension(1500, 500));
 //       jtable.setShowGrid(true);
        scroll = new JScrollPane(jtable);
        tablePanel.add(scroll);
    }
	
	/**
	 * Filter, sort the table
	 */
	private void editFunction() {
		functionPanel = new JPanel();
		sort = new JPanel();
		
		JLabel sortTable = new JLabel("Sort Table: ");
		sortTableBy = new JComboBox<String>(new String[]{"Visit ID", "Date", "Instrument"});
		sort.add(sortTable);
		sort.add(sortTableBy);
		
		//sort the table by visit_id
		sortTableBy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String item = (String) sortTableBy.getSelectedItem();
				try {
					if(item.equals("Visit ID")) {
						tablePanel.remove(scroll);
						
				        stmt = (Statement) conn.createStatement();
				        String query = "select visit_id as ID, date as Date, concat(first_name, " + "' '" + ", last_name) as Patient, visit_nr as "
				        		+ "Visit, instrument_types.name as Instr, rem_id as REM,"
				        		+ " followupvisit_type as FU, visit.comments as Comments "
				        		+ "from patient, visit, REM, instrument, instrument_types, instrument_model "
				        		
				        		+ "where patient.THC = visit.patient_thc and "
				        		
				        		+ "rem.VISIT_Visit_ID = instrument.VISIT_Visit_ID and rem.visit_visit_id = visit_id and rem.instrument_instr_id = instrument.instr_id and "
				        		
				        		+ "instrument.VISIT_Visit_ID = visit_id and `instrument model_im_id` = im_id and `instrument types_it_id` = it_id and instrument.visit_patient_thc "
				        		+ "= patient_thc order by ID";
				        		
				        		
				        		
				        //		+ "and `instrument category_ic_id` = ic_id`";
				        ResultSet rs = stmt.executeQuery(query);

				        rs.beforeFirst();
				        
				        String[] columnNames = {"ID", "Date", "Patient", "Visit", "Instr", "REM", "FU", "Comments"};
				        tableModel = new DefaultTableModel(columnNames, 0);

				        while (rs.next()) {
				        	
				            Integer id = rs.getInt(1);
				            String date = rs.getString(2);
				            String Patient = rs.getString(3);
				            Integer visit_nr = rs.getInt(4);
				            String instr = rs.getString(5);
				            Integer rem_id = rs.getInt(6);
				            String fu = rs.getString(7);
				            String comment = rs.getString(8);

				            String[] data = {Integer.toString(id), date, Patient, Integer.toString(visit_nr), instr, Integer.toString(rem_id), fu, comment} ;

				            // and add this row of data into the table model
				            tableModel.addRow(data);
				        }
				        jtable = new JTable(tableModel);
				        jtable.setGridColor(Color.BLACK);
				        jtable.setPreferredScrollableViewportSize(new Dimension(1500, 500));
				 //       jtable.setShowGrid(true);
				        scroll = new JScrollPane(jtable);
				        tablePanel.add(scroll);
				        tablePanel.revalidate();
					}
					
					// sort data by date
					else if(item.equals("Date")) {
						tablePanel.remove(scroll);
						
				        stmt = (Statement) conn.createStatement();
				        String query = "select visit_id as ID, date as Date, concat(first_name, " + "' '" + ", last_name) as Patient, visit_nr as "
				        		+ "Visit, instrument_types.name as Instr, rem_id as REM,"
				        		+ " followupvisit_type as FU, visit.comments as Comments "
				        		+ "from patient, visit, REM, instrument, instrument_types, instrument_model "
				        		
				        		+ "where patient.THC = visit.patient_thc and "
				        		
				        		+ "rem.VISIT_Visit_ID = instrument.VISIT_Visit_ID and rem.visit_visit_id = visit_id and rem.instrument_instr_id = instrument.instr_id and "
				        		
				        		+ "instrument.VISIT_Visit_ID = visit_id and `instrument model_im_id` = im_id and `instrument types_it_id` = it_id and instrument.visit_patient_thc "
				        		+ "= patient_thc order by Date";
				        		
				        		
				        		
				        //		+ "and `instrument category_ic_id` = ic_id`";
				        ResultSet rs = stmt.executeQuery(query);

				        rs.beforeFirst();
				        
				        String[] columnNames = {"ID", "Date", "Patient", "Visit", "Instr", "REM", "FU", "Comments"};
				        tableModel = new DefaultTableModel(columnNames, 0);

				        while (rs.next()) {
				        	
				            Integer id = rs.getInt(1);
				            String date = rs.getString(2);
				            String Patient = rs.getString(3);
				            Integer visit_nr = rs.getInt(4);
				            String instr = rs.getString(5);
				            Integer rem_id = rs.getInt(6);
				            String fu = rs.getString(7);
				            String comment = rs.getString(8);

				            String[] data = {Integer.toString(id), date, Patient, Integer.toString(visit_nr), instr, Integer.toString(rem_id), fu, comment} ;

				            // and add this row of data into the table model
				            tableModel.addRow(data);
				        }
				        jtable = new JTable(tableModel);
				        jtable.setGridColor(Color.BLACK);
				        jtable.setPreferredScrollableViewportSize(new Dimension(1500, 500));
				 //       jtable.setShowGrid(true);
				        scroll = new JScrollPane(jtable);
				        tablePanel.add(scroll);
				        tablePanel.revalidate();
					}
					//sort data by instrument
					else {
						tablePanel.remove(scroll);
						
				        stmt = (Statement) conn.createStatement();
				        String query = "select visit_id as ID, date as Date, concat(first_name, " + "' '" + ", last_name) as Patient, visit_nr as "
				        		+ "Visit, instrument_types.name as Instr, rem_id as REM,"
				        		+ " followupvisit_type as FU, visit.comments as Comments "
				        		+ "from patient, visit, REM, instrument, instrument_types, instrument_model "
				        		
				        		+ "where patient.THC = visit.patient_thc and "
				        		
				        		+ "rem.VISIT_Visit_ID = instrument.VISIT_Visit_ID and rem.visit_visit_id = visit_id and rem.instrument_instr_id = instrument.instr_id and "
				        		
				        		+ "instrument.VISIT_Visit_ID = visit_id and `instrument model_im_id` = im_id and `instrument types_it_id` = it_id and instrument.visit_patient_thc "
				        		+ "= patient_thc order by Instr";
				        		
				        		
				        		
				        //		+ "and `instrument category_ic_id` = ic_id`";
				        ResultSet rs = stmt.executeQuery(query);

				        rs.beforeFirst();
				        
				        String[] columnNames = {"ID", "Date", "Patient", "Visit", "Instr", "REM", "FU", "Comments"};
				        tableModel = new DefaultTableModel(columnNames, 0);

				        while (rs.next()) {
				        	
				            Integer id = rs.getInt(1);
				            String date = rs.getString(2);
				            String Patient = rs.getString(3);
				            Integer visit_nr = rs.getInt(4);
				            String instr = rs.getString(5);
				            Integer rem_id = rs.getInt(6);
				            String fu = rs.getString(7);
				            String comment = rs.getString(8);

				            String[] data = {Integer.toString(id), date, Patient, Integer.toString(visit_nr), instr, Integer.toString(rem_id), fu, comment} ;

				            // and add this row of data into the table model
				            tableModel.addRow(data);
				        }
				        jtable = new JTable(tableModel);
				        jtable.setGridColor(Color.BLACK);
				        jtable.setPreferredScrollableViewportSize(new Dimension(1500, 500));
				 //       jtable.setShowGrid(true);
				        scroll = new JScrollPane(jtable);
				        tablePanel.add(scroll);
				        tablePanel.revalidate();
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
		        	
		        	// filter the table by input date
					tablePanel.remove(scroll);
					
			        stmt = (Statement) conn.createStatement();
			        String query = "select visit_id as ID, date as Date, concat(first_name, " + "' '" + ", last_name) as Patient, visit_nr as "
			        		+ "Visit, instrument_types.name as Instr, rem_id as REM,"
			        		+ " followupvisit_type as FU, visit.comments as Comments "
			        		+ "from patient, visit, REM, instrument, instrument_types, instrument_model "
			        		
			        		+ "where patient.THC = visit.patient_thc and "
			        		
			        		+ "rem.VISIT_Visit_ID = instrument.VISIT_Visit_ID and rem.visit_visit_id = visit_id and rem.instrument_instr_id = instrument.instr_id and "
			        		
			        		+ "instrument.VISIT_Visit_ID = visit_id and `instrument model_im_id` = im_id and `instrument types_it_id` = it_id and instrument.visit_patient_thc "
			        		+ "= patient_thc and Date = " + "'" + filterDate.getText() +"'";
			        		
			        		
			        		
			        //		+ "and `instrument category_ic_id` = ic_id`";
			        ResultSet rs = stmt.executeQuery(query);

			        rs.beforeFirst();
			        
			        String[] columnNames = {"ID", "Date", "Patient", "Visit", "Instr", "REM", "FU", "Comments"};
			        tableModel = new DefaultTableModel(columnNames, 0);

			        while (rs.next()) {
			        	
			            Integer id = rs.getInt(1);
			            String date = rs.getString(2);
			            String Patient = rs.getString(3);
			            Integer visit_nr = rs.getInt(4);
			            String instr = rs.getString(5);
			            Integer rem_id = rs.getInt(6);
			            String fu = rs.getString(7);
			            String comment = rs.getString(8);

			            String[] data = {Integer.toString(id), date, Patient, Integer.toString(visit_nr), instr, Integer.toString(rem_id), fu, comment} ;

			            // and add this row of data into the table model
			            tableModel.addRow(data);
			        }
			        jtable = new JTable(tableModel);
			        jtable.setGridColor(Color.BLACK);
			        jtable.setPreferredScrollableViewportSize(new Dimension(1500, 500));
			 //       jtable.setShowGrid(true);
			        scroll = new JScrollPane(jtable);
			        tablePanel.add(scroll);
			        tablePanel.revalidate();
					
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
		        	// filter the table by patient name
					tablePanel.remove(scroll);
					
			        stmt = (Statement) conn.createStatement();
			        String query = "select visit_id as ID, date as Date, concat(first_name, " + "' '" + ", last_name) as Patient, visit_nr as "
			        		+ "Visit, instrument_types.name as Instr, rem_id as REM,"
			        		+ " followupvisit_type as FU, visit.comments as Comments "
			        		+ "from patient, visit, REM, instrument, instrument_types, instrument_model "
			        		
			        		+ "where patient.THC = visit.patient_thc and "
			        		
			        		+ "rem.VISIT_Visit_ID = instrument.VISIT_Visit_ID and rem.visit_visit_id = visit_id and rem.instrument_instr_id = instrument.instr_id and "
			        		
			        		+ "instrument.VISIT_Visit_ID = visit_id and `instrument model_im_id` = im_id and `instrument types_it_id` = it_id and instrument.visit_patient_thc "
			        		+ "= patient_thc and first_name = " + "'"+ firstName.getText() +"' and last_name = " + "'" + lastName.getText() +"'";
			        		
			        		
			        		
			        //		+ "and `instrument category_ic_id` = ic_id`";
			        ResultSet rs = stmt.executeQuery(query);

			        rs.beforeFirst();
			        
			        String[] columnNames = {"ID", "Date", "Patient", "Visit", "Instr", "REM", "FU", "Comments"};
			        tableModel = new DefaultTableModel(columnNames, 0);

			        while (rs.next()) {
			        	
			            Integer id = rs.getInt(1);
			            String date = rs.getString(2);
			            String Patient = rs.getString(3);
			            Integer visit_nr = rs.getInt(4);
			            String instr = rs.getString(5);
			            Integer rem_id = rs.getInt(6);
			            String fu = rs.getString(7);
			            String comment = rs.getString(8);

			            String[] data = {Integer.toString(id), date, Patient, Integer.toString(visit_nr), instr, Integer.toString(rem_id), fu, comment} ;

			            // and add this row of data into the table model
			            tableModel.addRow(data);
			        }
			        jtable = new JTable(tableModel);
			        jtable.setGridColor(Color.BLACK);
			        jtable.setPreferredScrollableViewportSize(new Dimension(1500, 500));
			 //       jtable.setShowGrid(true);
			        scroll = new JScrollPane(jtable);
			        tablePanel.add(scroll);
			        tablePanel.revalidate();
					//to be done
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		deleteRow = new JButton("Delete the selected row");
		deleteRow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {   // delete the visit
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
