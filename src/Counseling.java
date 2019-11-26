 
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
import java.time.LocalDate;

public class Counseling extends JFrame{
	/**
	 * static final serialVersionUID.
	 */
        
        private JPanel clMain, clPanel;
        
	final Container contentPane = getContentPane();
        
        public static void main(String[] args) {
            new Counseling();
        }
        
	public Counseling() {

           
            clPanel();

            setPreferredSize(new Dimension(450, 300));
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    pack();
	    setVisible(true);
	}
        
        
        private void clPanel() {
            clPanel = new JPanel();
            Border blackline = BorderFactory.createLineBorder(Color.black);
            clPanel.setBorder(blackline);
            
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
            
            JLabel type = new JLabel("Follow-up(PU) Type: ");
            JTextField typeField = new JTextField();
            typeField.setText("Typexxx");
            typeField.setEditable(false);
            
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
            
            setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
            
            add(clPanel);
            add(clMain);
                   
        }
        
        private void clMain() {
            clMain = new JPanel();
            
            JLabel detailLabel = new JLabel("Detail: ");
            
            JTextArea detailText = new JTextArea(10, 20);
            
            detailText.setEditable(true);
            
            
            clMain.add(detailLabel);
            clMain.add(detailText);
            
        }
	
}