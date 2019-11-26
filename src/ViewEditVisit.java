import java.awt.Color;
import java.awt.Container;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	public ViewEditVisit() throws SQLException {
		connectToDataBase();
		createTablePanel();
		
	    final Container contentPane = getContentPane();
	    setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	    
		add(tablePanel);
		
		setTitle("Visit");
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
        
        String[] columnNames = {"THC", "FIRST NAME", "LAST NAME"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        while (rs.next()) {
            String fn = rs.getString("first_name");
            String ln = rs.getString("last_name");
            Integer thc = rs.getInt("THC");

            String[] data = {fn, ln, Integer.toString(thc)} ;

            // and add this row of data into the table model
            tableModel.addRow(data);
        }
        jtable = new JTable(tableModel);
        jtable.setGridColor(Color.BLACK);
 //       jtable.setShowGrid(true);
        JScrollPane scroll = new JScrollPane(jtable);
        tablePanel.add(scroll);
    }
}
