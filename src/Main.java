
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

/**
 * A class to build REM interface
 * @author chunyou su
 *
 */
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

    private CreateInterface createReference;

    /**
     * Empty construcotr
     */
    public Main() {//throws SQLException {


    }

    /**
     * Set the inactive panel
     * @param thc patient thc number
     * @param name patient full name
     * @param date date of today
     * @param pCat patient category
     * @param instT instrument type
     */
    public void remPanel(String thc, String name, String date,
            String pCat, String instT) {
        remPanel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        remPanel.setBorder(blackline);

        //SQLLoader connectToDatabase = new SQLLoader();
        JLabel nameLabel = new JLabel("Patien Name: ");
        JTextField nameField = new JTextField();
        nameField.setText(name);
        nameField.setEditable(false);

        JLabel dateLabel = new JLabel("Date: ");
        JTextField dateField = new JTextField();
        dateField.setText(date);
        dateField.setEditable(false);

        JLabel THCLabel = new JLabel("THC#: ");
        JTextField THCField = new JTextField(6);
        THCField.setText(thc);
        THCField.setEditable(false);

        JLabel category = new JLabel("Patient Category: ");
        JTextField categoryField = new JTextField();
        categoryField.setText(pCat);
        categoryField.setEditable(false);

        JLabel type = new JLabel("Instrument Type: ");
        JTextField typeField = new JTextField();
        typeField.setText(instT);
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
        
        setPreferredSize(new Dimension(600, 680));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

    }

    /**
     * Set save and cancel button
     */
    private void lowerPanel() {
        lowerPanel = new JPanel();
        JButton saveButton = new JButton("SAVE");
        JButton exitButton = new JButton("EXIT");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                createReference.setREMData(freqREI, tRSI, mRSI, tRSPLI,
                        tRSLI, mRSPLI, mRSLI, freqLEI, tLSI, mLSI, tLSPLI,
                        tLSLI, mLSPLI, mLSLI);
                ownReference.dispose();
            }

        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ownReference.dispose();
            }

        });

        lowerPanel.add(saveButton);
        lowerPanel.add(exitButton);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 0));

    }

    /**
     * Build the right panel to get all information from parameters
     */
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
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (freqRET.getText().length() != 0) {
                    freqREI = Integer.parseInt(freqRET.getText());
                    System.out.println("freqREI = " + freqREI);
                }
            }

        });

        tRST.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (tRST.getText().length() != 0) {
                    tRSI = Integer.parseInt(tRST.getText());
                    System.out.println("tRSI = " + tRSI);
                }
            }

        });

        mRST.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (mRST.getText().length() != 0) {
                    mRSI = Integer.parseInt(mRST.getText());
                    System.out.println("mRSI = " + mRSI);
                }
            }

        });

        tRSPLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (tRSPLT.getText().length() != 0) {
                    tRSPLI = Integer.parseInt(tRSPLT.getText());
                    System.out.println("tRSPLI = " + tRSPLI);
                }
            }

        });

        tRSLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (tRSLT.getText().length() != 0) {
                    tRSLI = Integer.parseInt(tRSLT.getText());
                    System.out.println("tRSLI = " + tRSLI);
                }
            }

        });

        mRSPLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (mRSPLT.getText().length() != 0) {
                    mRSPLI = Integer.parseInt(mRSPLT.getText());
                    System.out.println("mRSPLI = " + mRSPLI);
                }
            }

        });

        mRSLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (mRSLT.getText().length() != 0) {
                    mRSLI = Integer.parseInt(mRSLT.getText());
                    System.out.println("mRSLI = " + mRSLI);
                }
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

    /**
     * Build the left panel to get all information from parameters
     */
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
                if (freqLET.getText().length() != 0) {
                    freqLEI = Integer.parseInt(freqLET.getText());
                    System.out.println("freqLEI = " + freqLEI);
                }

            }

        });

        tLST.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (tLST.getText().length() != 0) {
                    tLSI = Integer.parseInt(tLST.getText());
                    System.out.println("tLSI = " + tLSI);
                }

            }

        });

        mLST.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (mLST.getText().length() != 0) {
                    mLSI = Integer.parseInt(mLST.getText());
                    System.out.println("mLSI = " + mLSI);
                }

            }

        });

        tLSPLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (tLSPLT.getText().length() != 0) {
                    tLSPLI = Integer.parseInt(tLSPLT.getText());
                    System.out.println("tLSPLI = " + tLSPLI);
                }

            }

        });

        tLSLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (tLSLT.getText().length() != 0) {
                    tLSLI = Integer.parseInt(tLSLT.getText());
                    System.out.println("tRSLI = " + tLSLI);
                }

            }

        });

        mLSPLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (mLSPLT.getText().length() != 0) {
                    mLSPLI = Integer.parseInt(mLSPLT.getText());
                    System.out.println("mLSPLI = " + mLSPLI);
                }

            }

        });

        mLSLT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (mLSLT.getText().length() != 0) {
                    mLSLI = Integer.parseInt(mLSLT.getText());
                    System.out.println("mLSLI = " + mLSLI);
                }

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

    /**
     * Get the CreateInterface type reference
     * @param refer CreateInterface type reference
     */
    public void setCreateReference(CreateInterface refer) {
        this.createReference = refer;
    }

    /**
     *  Get the own reference for diposal
     * @param ownReference own Reference
     */
    public void setOwnReference(Main ownReference) {
        this.ownReference = ownReference;
    }

    //public int[] getData
}
