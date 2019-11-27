
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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

public class Counseling extends JFrame {

    /**
     * static final serialVersionUID.
     */
    private JPanel clMain, clPanel, lowerPanel;
    private CreateInterface createReference;
    private Counseling ownReference;

    private JTextArea detailText = new JTextArea(30, 40);
    String detail;

    final Container contentPane = getContentPane();

    public static void main(String[] args) {
        new Counseling("1");
    }

    public Counseling(String thc) {

        try {
            clPanel(thc);
        } catch (SQLException ex) {
            Logger.getLogger(Counseling.class.getName()).log(Level.SEVERE, null, ex);
        }

        setPreferredSize(new Dimension(580, 680));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void clPanel(String thc) throws SQLException {
        clPanel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        clPanel.setBorder(blackline);

        //connection
        //Connection conn = DriverManager.getConnection(
        //        "jdbc:mysql://localhost:3306/mydb", "root", "1486630878Su");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cs157A?useTimezone=true&serverTimezone=UTC","shuangpan", "FEIfei5217?");
        Statement stmt = conn.createStatement();

        JLabel nameLabel = new JLabel("Patien Name: ");
        JTextField nameField = new JTextField();
        nameField.setEditable(false);

        JLabel dateLabel = new JLabel("Date: ");
        JTextField dateField = new JTextField();
        dateField.setEditable(false);

        JLabel THCLabel = new JLabel("THC#: ");
        JTextField THCField = new JTextField(6);
        THCField.setEditable(false);

        JLabel category = new JLabel("Patient Category: ");
        JTextField categoryField = new JTextField();
        categoryField.setEditable(false);

        JLabel type = new JLabel("Follow-up(PU) Type: ");
        JTextField typeField = new JTextField();
        typeField.setEditable(false);

        //
        ResultSet rs = stmt.executeQuery("select* from patient where"
                + " THC = " + thc + ";");

        rs.next();

        //
        THCField.setText(thc);
        categoryField.setText(String.valueOf(rs.getInt(4)));
        nameField.setText(rs.getString(3) + " " + rs.getString(2));

        rs = stmt.executeQuery("select DATE from visit where PATIENT_THC = "
                + thc + ";");
        rs.next();
        dateField.setText(String.valueOf(rs.getDate(1)));
        
        rs = stmt.executeQuery("select VISIT_ID from visit where "
                + "PATIENT_THC = " + thc + ";");
        rs.next();
        
        int visitID = rs.getInt(1);
        
        rs = stmt.executeQuery("select TYPE from counseling where "
                + "VISIT_Visit_ID = " + visitID + ";");
        rs.next();
        
        typeField.setText(rs.getString(1));

        //
        clPanel.add(nameLabel);
        clPanel.add(nameField);
        clPanel.add(dateLabel);
        clPanel.add(dateField);
        clPanel.add(THCLabel);
        clPanel.add(THCField);
        clPanel.add(category);
        clPanel.add(categoryField);
        clPanel.add(type);
        clPanel.add(typeField);

        setTitle("Counseling");
        clMain();
        lowerPanel();

        setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        add(clPanel);
        add(clMain);
        add(lowerPanel);

    }

    private void clMain() {
        clMain = new JPanel();

        JLabel detailLabel = new JLabel("Detail: ");

        //detailText = new JTextArea(30, 40);
        detailText.setEditable(true);

        clMain.add(detailLabel);
        clMain.add(detailText);

    }

    private void lowerPanel() {
        lowerPanel = new JPanel();
        JButton saveButton = new JButton("SAVE");
        JButton exitButton = new JButton("EXIT");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (detailText.getText().length() == 0) {
                    detailText.setText("No Comment");
                }

                detail = detailText.getText();
                createReference.setCounselingComment(detail);
                ownReference.dispose();
            }

        });

        lowerPanel.add(saveButton);
        lowerPanel.add(exitButton);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    }

    public void setCreateReference(CreateInterface refer) {
        this.createReference = refer;
    }

    public void setOwnReference(Counseling ownReference) {
        this.ownReference = ownReference;
    }

}
