
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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private String comment;
    private int instrumentModel;
//	private VisitMain vm;

    private String newTHC;

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

    private void connectToDataBase() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        // Connect to the database
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157A?useTimezone=true&serverTimezone=UTC", "shuangpan", "FEIfei5217?");
        
        // Create a Statement
        stmt = conn.createStatement();
    }

    private void createVisitPanel() {
        visitPanel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        visitPanel.setBorder(blackline);

        JLabel visitIDLabel = new JLabel("Visit ID: ");
        JTextField visitIDField = new JTextField(6);
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
                            visitIDField.setText(Integer.toString(rs.getInt("id")) + 1);
                        }

                        rs = stmt.executeQuery("select max(visit_nr) as nr from visit where date = '" + LocalDate.now().toString() + "'");
                        if (rs.next() && rs.getString("nr") == null) {
                            visitNumberField.setText("1");
                        } else {
                            visitNumberField.setText(Integer.toString(rs.getInt("id")) + 1);
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

        middlePanel.add(categoryLabel);
        middlePanel.add(categoryField);
        middlePanel.add(instruLabel);
        middlePanel.add(ins);
        middlePanel.add(instruCategory);
        middlePanel.add(instruCateField);
        middlePanel.add(remLabel);
        middlePanel.add(rem);
    }

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
                try {
                    Main newREM = new Main(newTHC);
                    newREM.setOwnReference(newREM);
                } catch (SQLException ex) {
                    Logger.getLogger(CreateInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        counsel = new JButton("Counseling Details");
        counsel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Counseling newCounsel = new Counseling();
            }

        });

        others.add(instrument);
        others.add(REM);
        others.add(counsel);
    }

    public void createLowerPanel() {
        lowerPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ownReference.dispose();
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

    public void setOwnReference(CreateInterface reference) {
        this.ownReference = reference;
    }

    public void setInstrumentData(String comment, int instrumentModel) {
        this.instrumentModel = instrumentModel;
        this.comment = comment;
        System.out.println(this.instrumentModel + " " + comment);
    }
}
