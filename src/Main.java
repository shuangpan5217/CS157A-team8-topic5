
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;
import javax.swing.Box;

public class Main extends JFrame {

    /**
     * static final serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private Statement stmt;
    private Main ownReference;

    private int freqREI, tRSI, mRSI, tRSPLI, tRSLI, mRSPLI, mRSLI;
    private int freqLEI, tLSI, mLSI, tLSPLI, tLSLI, mLSPLI, mLSLI;

    private JPanel remPanel, remMain, lowerPanel, leftPanel, rightPanel;

    final Container contentPane = getContentPane();

    public static void main(String[] args) throws SQLException {

        String thc = "1";
        new Main(thc);
    }

    public Main(String thc) throws SQLException {//throws SQLException {

        remPanel(thc);

        setPreferredSize(new Dimension(550, 680));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
    private void remPanel(String thc) throws SQLException {
        remPanel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        remPanel.setBorder(blackline);

        //SQLLoader connectToDatabase = new SQLLoader();
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

        JLabel type = new JLabel("Instrument Type: ");
        JTextField typeField = new JTextField();
        typeField.setEditable(false);

        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cs157A?useTimezone=true&serverTimezone=UTC", "shuangpan", "FEIfei5217?");
        stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("select* from patient where"
                + " THC = " + thc + ";");

        rs.next();

        THCField.setText(thc);
        categoryField.setText(String.valueOf(rs.getInt(4)));
        nameField.setText(rs.getString(3) + " " + rs.getString(2));

        rs = stmt.executeQuery("select DATE from visit where PATIENT_THC = "
                + thc + ";");
        rs.next();
        dateField.setText(String.valueOf(rs.getDate(1)));

        rs = stmt.executeQuery("select* from "
                + "instrument where VISIT_PATIENT_THC = " + thc + ";");
        rs.next();

        int instTypeID = rs.getInt(5);

        rs = stmt.executeQuery("select NAME from instrument_types where "
                + "IT_ID = " + instTypeID + ";");
        rs.next();

        typeField.setText(rs.getString(1));

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
        mainLeftP();
        mainRightP();

        //remMain();
        lowerPanel();

        setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        remMain = new JPanel();
        remMain.setLayout(new GridLayout(1, 2, 40, 0));

        remMain.add(leftPanel);
        remMain.add(rightPanel);

        add(remPanel);

        add(remMain);
        //add(leftPanel);
        //add(rightPanel);
        //add(remMain);
        add(lowerPanel);

    }

    private void lowerPanel() {
        lowerPanel = new JPanel();
        JButton saveButton = new JButton("SAVE");
        JButton exitButton = new JButton("EXIT");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ownReference.dispose();
            }

        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                freqREI = 0;
                tRSI = 0;
                mRSI = 0;
                tRSPLI = 0;
                tRSLI = 0;
                mRSPLI = 0;
                mRSLI = 0;

                freqLEI = 0;
                tLSI = 0;
                mLSI = 0;
                tLSPLI = 0;
                tLSLI = 0;
                mLSPLI = 0;
                mLSLI = 0;
                ownReference.dispose();
            }

        });

        lowerPanel.add(saveButton);
        lowerPanel.add(exitButton);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 0));

    }

    private void mainRightP() {
        Container vBox = Box.createVerticalBox();

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 2, 0, 10));

        JLabel freqRE = new JLabel("Freq RE: ");
        JLabel tRS = new JLabel("Th R SPL: ");
        JLabel mRS = new JLabel("Mix R SL: ");
        JLabel tRSPL = new JLabel("Tol R SPL: ");
        JLabel tRSL = new JLabel("Tol R SL: ");
        JLabel mRSPL = new JLabel("Max R SPL: ");
        JLabel mRSL = new JLabel("Max R SL: ");

        JTextField freqRET = new JTextField(5);
        freqRET.setEditable(true);

        JTextField tRST = new JTextField(5);
        tRST.setEditable(true);

        JTextField mRST = new JTextField(5);
        mRST.setEditable(true);

        JTextField tRSPLT = new JTextField(5);
        tRSPLT.setEditable(true);

        JTextField tRSLT = new JTextField(5);
        tRSLT.setEditable(true);

        JTextField mRSPLT = new JTextField(5);
        mRSPLT.setEditable(true);

        JTextField mRSLT = new JTextField(5);
        mRSLT.setEditable(true);

        //
        freqRET.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                freqREI = 0;
            }

            @Override
            public void focusLost(FocusEvent fe) {
                freqREI = Integer.parseInt(freqRET.getText());
                System.out.println("freqREI = " + freqREI);
            }

        });

        tRST.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                tRSI = 0;
            }

            @Override
            public void focusLost(FocusEvent fe) {
                tRSI = Integer.parseInt(tRST.getText());
                System.out.println("tRSI = " + tRSI);
            }

        });

        mRST.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                mRSI = 0;
            }

            @Override
            public void focusLost(FocusEvent fe) {
                mRSI = Integer.parseInt(mRST.getText());
                System.out.println("mRSI = " + mRSI);
            }

        });

        tRSPLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                tRSPLI = 0;
            }

            @Override
            public void focusLost(FocusEvent fe) {
                tRSPLI = Integer.parseInt(tRSPLT.getText());
                System.out.println("tRSPLI = " + tRSPLI);
            }

        });

        tRSLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                tRSPLI = 0;
            }

            @Override
            public void focusLost(FocusEvent fe) {
                tRSLI = Integer.parseInt(tRSLT.getText());
                System.out.println("tRSLI = " + tRSLI);
            }

        });

        mRSPLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                mRSPLI = 0;
            }

            @Override
            public void focusLost(FocusEvent fe) {
                mRSPLI = Integer.parseInt(mRSPLT.getText());
                System.out.println("mRSPLI = " + mRSPLI);
            }

        });

        mRSLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                mRSLI = 0;
            }

            @Override
            public void focusLost(FocusEvent fe) {
                mRSLI = Integer.parseInt(mRSLT.getText());
                System.out.println("mRSLI = " + mRSLI);
            }

        });

        //
        leftPanel.add(freqRE);
        leftPanel.add(freqRET);
        leftPanel.add(tRS);
        leftPanel.add(tRST);
        leftPanel.add(mRS);
        leftPanel.add(mRST);
        leftPanel.add(tRSPL);
        leftPanel.add(tRSPLT);
        leftPanel.add(tRSL);
        leftPanel.add(tRSLT);
        leftPanel.add(mRSPL);
        leftPanel.add(mRSPLT);
        leftPanel.add(mRSL);
        leftPanel.add(mRSLT);

    }

    private void mainLeftP() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(7, 2, 0, 10));
        JLabel freqLE = new JLabel("Freq LE: ");
        JLabel tLS = new JLabel("Th L SPL: ");
        JLabel mLS = new JLabel("Mix L SL: ");
        JLabel tLSPL = new JLabel("Tol L SPL: ");
        JLabel tLSL = new JLabel("Tol L SL: ");
        JLabel mLSPL = new JLabel("Max L SPL: ");
        JLabel mLSL = new JLabel("Max L SL: ");

        JTextField freqLET = new JTextField(5);
        freqLET.setEditable(true);

        JTextField tLST = new JTextField(5);
        tLST.setEditable(true);

        JTextField mLST = new JTextField(5);
        mLST.setEditable(true);

        JTextField tLSPLT = new JTextField(5);
        tLSPLT.setEditable(true);

        JTextField tLSLT = new JTextField(5);
        tLSLT.setEditable(true);

        JTextField mLSPLT = new JTextField(5);
        mLSPLT.setEditable(true);

        JTextField mLSLT = new JTextField(5);
        mLSLT.setEditable(true);

        //
        freqLET.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                freqLEI = Integer.parseInt(freqLET.getText());
                System.out.println("freqLEI = " + freqLEI);

            }

        });

        tLST.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                tLSI = Integer.parseInt(tLST.getText());
                System.out.println("tLSI = " + tLSI);

            }

        });

        mLST.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                mRSI = Integer.parseInt(mLST.getText());
                System.out.println("mLSI = " + mLSI);

            }

        });

        tLSPLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                tLSPLI = Integer.parseInt(tLSPLT.getText());
                System.out.println("tLSPLI = " + tLSPLI);

            }

        });

        tLSLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                tLSLI = Integer.parseInt(tLSLT.getText());
                System.out.println("tRSLI = " + tLSLI);

            }

        });

        mLSPLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {

                mLSPLI = Integer.parseInt(mLSPLT.getText());
                System.out.println("mLSPLI = " + mLSPLI);

            }

        });

        mLSLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {

                mLSLI = Integer.parseInt(mLSLT.getText());
                System.out.println("mLSLI = " + mLSLI);

            }

        });

        //
        rightPanel.add(freqLE);
        rightPanel.add(freqLET);
        rightPanel.add(tLS);
        rightPanel.add(tLST);
        rightPanel.add(mLS);
        rightPanel.add(mLST);
        rightPanel.add(tLSPL);
        rightPanel.add(tLSPLT);
        rightPanel.add(tLSL);
        rightPanel.add(tLSLT);
        rightPanel.add(mLSPL);
        rightPanel.add(mLSPLT);
        rightPanel.add(mLSL);
        rightPanel.add(mLSLT);

    }

    public void setOwnReference(Main reference) {
        this.ownReference = reference;
    }

}
