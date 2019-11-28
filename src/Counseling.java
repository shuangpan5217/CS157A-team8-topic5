
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

    public Counseling() {

    }

    public void clPanel(String thc, String name, String vID, 
            String vDate, String fuType) {
        clPanel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        clPanel.setBorder(blackline);


        JLabel nameLabel = new JLabel("Patien Name: ");
        JTextField nameField = new JTextField();
        nameField.setText(name);
        nameField.setEditable(false);

        JLabel dateLabel = new JLabel("Date: ");
        JTextField dateField = new JTextField();
        dateField.setText(vDate);
        dateField.setEditable(false);

        JLabel THCLabel = new JLabel("THC#: ");
        JTextField THCField = new JTextField(6);
        THCField.setText(thc);
        THCField.setEditable(false);

        JLabel category = new JLabel("Visit ID: ");
        JTextField categoryField = new JTextField();
        categoryField.setText(vID);
        categoryField.setEditable(false);

        JLabel type = new JLabel("Follow-up(PU) Type: ");
        JTextField typeField = new JTextField();
        typeField.setText(fuType);
        typeField.setEditable(false);

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

        setPreferredSize(new Dimension(600, 680));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

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
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                detailText.setText("No Comment");
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
