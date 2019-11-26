import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VisitMain extends JFrame{
	@SuppressWarnings("unused")
	private VisitMain ownReference;
	private JPanel mainPanel;
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	public VisitMain() {
		createButton();
		
		add(mainPanel);
		
		setTitle("Visit");
//		setPreferredSize(new Dimension(450, 300)); //
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setVisible(true);
	}
	
	private void createButton() {
		mainPanel = new JPanel();
		
		JButton newVisit = new JButton("Add New Visit");
		JButton editVisit = new JButton("View/Edit Vists");
		
		mainPanel.add(newVisit);
		mainPanel.add(editVisit);
		
		newVisit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				ownReference.setVisible(false);
				try {
					@SuppressWarnings("unused")
					CreateInterface cif = new CreateInterface();
					cif.setOwnReference(cif);
//					cif.setVM(ownReference); //
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			  
		});
		
		editVisit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				ownReference.setVisible(false);
				try {
					@SuppressWarnings("unused")
					ViewEditVisit vev = new ViewEditVisit();
//					cif.setVM(ownReference);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
	}
	
	public void setOwnReference(VisitMain ownReference) {
		this.ownReference = ownReference;
		
	}
}
