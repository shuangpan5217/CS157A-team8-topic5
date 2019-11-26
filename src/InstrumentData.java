import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;


import javax.swing.BorderFactory;
import java.time.LocalDate;
import javax.swing.JComboBox;


public class InstrumentData extends JFrame {
	private InstrumentData ownReference;
	private JPanel instrPanel;
	private JLabel instrName, instrModel, instrCategory, instrType;
	private JTextField instrNameF, instrModelF, instrCategoryF, instrTypeF;
	private JButton addInstrumentBtn, editInstrumentBtn, cancelBtn, saveBtn;
	final Container contentPane = getContentPane();
	
	public InstrumentData() {

		InstrumentPanel();
		createButton();
		setTitle("ADD/EDIT Instrument");
        setPreferredSize(new Dimension(450, 300));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
	}
	private void InstrumentPanel() {
		instrPanel = new JPanel();
		instrName = new JLabel("Instrument's Name: ");
		instrModel = new JLabel("Model: ");
		instrCategory = new JLabel("Category: ");
		instrType = new JLabel("Type: ");
		
		instrNameF = new JTextField(10);
		instrNameF.setEditable(true);
		instrModelF = new JTextField(1);
		instrModelF.setEditable(true);
		instrCategoryF = new JTextField(10);
		instrCategoryF.setEditable(true);
		instrTypeF = new JTextField(10);
		instrTypeF.setEditable(true);
		
		
		instrPanel.add(instrName);
		instrPanel.add(instrNameF);
		instrPanel.add(instrModel);
		instrPanel.add(instrModelF);
		instrPanel.add(instrCategory);
		instrPanel.add(instrCategoryF);
		instrPanel.add(instrType);
		instrPanel.add(instrTypeF);
		
		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		add(instrPanel);

		
	}
	

	private void createButton() {
		instrPanel = new JPanel();
		JButton addInstrumentBtn = new JButton("ADD");
		JButton editInstrumentBtn = new JButton("EDIT");
		JButton cancelBtn = new JButton("CANCEL");
		JButton saveBtn = new JButton("SAVE");
		
		instrPanel.add(addInstrumentBtn);
		instrPanel.add(editInstrumentBtn);
		instrPanel.add(saveBtn);
		instrPanel.add(cancelBtn);
		
		add(instrPanel);
	}
	
	
	public void setOwnReference(InstrumentData ownReference) {
		this.ownReference = ownReference;
		
	}
}
