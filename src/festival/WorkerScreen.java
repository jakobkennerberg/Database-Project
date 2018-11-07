package festival;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

/**
 * This class represents the start panel of the Worker GUI, used to assign contact persons among things
 * @author JakobK98
 *
 */
public class WorkerScreen extends JPanel implements ActionListener {
	private CardSwitcher cardSwitcher;
	private JPanel bandPanel;
	private JPanel bandListPanel;
	private JPanel workerListPanel;
	private JLabel lblBand = new JLabel("Bands");
	private JLabel lblWorker = new JLabel("Assign Contact person");
	private JLabel lblAssigned = new JLabel("Assigned:");
	private JButton btnAddBand = new JButton("Add Band");
	private JButton btnAssign = new JButton("Assign");
	
	private int bandAmount = 0;
	private int workerAmount = 0;
	private ButtonGroup workerbg = new ButtonGroup();
	private ButtonGroup bandbg = new ButtonGroup();
	private WorkerRBListener rbWorker = new WorkerRBListener();
	private BandRBListener rbBand = new BandRBListener();
	private String currentSelectedBand;
	private String currentSelectedWorker;
	private boolean selectedWorker = false;
	private boolean selectedBand = false;
	private boolean assigned = false;
	private CardController controller;
	
	/**
	 * Constructor
	 * @param controller
	 */
	public WorkerScreen(CardController controller) {
		setPreferredSize(new Dimension(1000, 700));
		setLayout(null);
		this.controller = controller;
		setBackground(Color.PINK);
		add(bandPanel());
	}
	
	/**
	 * Method used to create the GUI
	 * @return
	 */
	public JPanel bandPanel() {
		bandPanel = new JPanel();
		bandPanel.setLayout(null);
		bandPanel.setBounds(0, 0, 1050, 750);
		bandPanel.setBackground(Color.PINK);
		Font lblFont = new Font("SansSerif", Font.BOLD, 25);
		
		bandListPanel = new JPanel();
		bandListPanel.setLayout(new GridLayout(getBandListCount(), 1)); 
		JScrollPane bandScroll = new JScrollPane(bandListPanel);
		bandScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		bandScroll.setBounds(50, 75, 300, 500);
		lblBand.setBounds(50, 25, 300, 50);
		lblBand.setFont(lblFont);
		lblBand.setHorizontalAlignment(JLabel.CENTER);
		
		workerListPanel = new JPanel();
		workerListPanel.setLayout(new GridLayout(getWorkerListCount(), 1)); 
		JScrollPane workerScroll = new JScrollPane(workerListPanel);
		workerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		workerScroll.setBounds(450, 75, 300, 500);
		lblWorker.setBounds(450, 25, 300, 50);
		lblWorker.setFont(lblFont);
		lblWorker.setHorizontalAlignment(JLabel.CENTER);
		
		btnAddBand.setBounds(50, 600, 300, 50);
		btnAddBand.addActionListener(this);
		btnAssign.setBounds(800,600,150,50);
		btnAssign.setEnabled(false);
		btnAssign.addActionListener(this);
		
		lblAssigned.setBounds(450, 600, 300, 50);
		lblAssigned.setFont(lblFont);
		
		setUpWorkerList(controller.getWorkerList(), false);
		setUpBandList(getBandList()); 
		
		bandPanel.add(lblAssigned);
		bandPanel.add(btnAssign);
		bandPanel.add(btnAddBand);
		bandPanel.add(lblBand);
		bandPanel.add(lblWorker);
		bandPanel.add(workerScroll);
		bandPanel.add(bandScroll);
		
		return bandPanel;
	}
	
	/**
	 * Method used to set the listener to this class
	 * @param listener
	 */
	public void setListener(CardSwitcher listener) {
		cardSwitcher = listener;
	}
	
	/**
	 * Method used to get the list of bandnames to display
	 * @return
	 */
	public ArrayList<String> getBandList() {
		ArrayList<String> list = new ArrayList<String>();
		list = controller.getBandList(list);
		return list;
	}
	
	/**
	 * Method used to set up the section displaying the bands
	 * @param bandList
	 */
	public void setUpBandList(ArrayList<String> bandList) {
		bandListPanel.removeAll();
		bandListPanel.setLayout(new GridLayout(getBandListCount(), 1));
		bandListPanel.repaint();
		
		for (int i = 0; i < bandList.size(); i++) {
			JRadioButton btn = new JRadioButton(bandList.get(i));
			btn.setSize(new Dimension(300, 60));
			bandbg.add(btn);
			btn.addActionListener(rbBand);
			bandListPanel.add(btn);
		}
	}
	
	/**
	 * Method used to set up the section displaying the workers
	 * @param workerList
	 * @param value
	 */
	public void setUpWorkerList(ArrayList<String> workerList, boolean value) {
		workerListPanel.removeAll();
		workerListPanel.setLayout(new GridLayout(getWorkerListCount(), 1));
		workerListPanel.repaint();
		
		for (int i = 0; i < workerList.size(); i++) {
			JRadioButton btn = new JRadioButton(workerList.get(i));
			btn.setSize(new Dimension(300, 80));
			btn.setEnabled(value);
			workerbg.add(btn);
			btn.addActionListener(rbWorker);
			workerListPanel.add(btn);
		}
	}
	
	/**
	 * Method used to get the amount of bands in the database
	 * @return
	 */
	public int getBandListCount() {
		bandAmount = controller.getGridSize("Band");
		return bandAmount;
	}
	
	/**
	 * Method used to get the amount of workers in the database
	 * @return
	 */
	public int getWorkerListCount() {
		workerAmount = controller.getGridSize("Worker");
		return workerAmount;
	}
	
	/**
	 * Method used to submit the band created
	 * @param bandname
	 * @param orgin
	 * @param members
	 */
	public void submitBand(String bandname, String orgin, ArrayList<BandMember> members) {
		controller.addBand(bandname, orgin, members);
	}
	
	/**
	 * Method used to check if the user should be able to go to the next panel(to book a concert)
	 */
	public void checkIfGoodToGo() {
		if(selectedWorker == true && selectedBand == true) {
			btnAssign.setEnabled(true);
		}
	}
	
	/**
	 * Method which is used to display the status of the band regarding being assigned a contact person
	 * @param name
	 * @param value
	 */
	public void setAssigned(String name, boolean value) {
		lblAssigned.setText("Assigned: " + name);
		btnAssign.setEnabled(value);
		currentSelectedWorker = name;
		
		if(value == false) {
			selectedWorker = false;
			assigned = false;
		} else {
			assigned = true;
			
		}
	}

	/**
	 * Method which listens to the buttons in the GUI
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAddBand) {
			new BandAdderPanel(this);
		}
		if(e.getSource()==btnAssign) {
			if(assigned == false) { //if the band not already has been assigned a worker, the selected worker will be assigned
				controller.assignContact(currentSelectedBand, currentSelectedWorker);
			}
			controller.updateLabels(currentSelectedBand, currentSelectedWorker);
			cardSwitcher.nextPanel();
			controller.updateAvailible();
		}
	}
	
	/**
	 * Inner class which is used to listen to the buttons of the workers
	 * @author JakobK98
	 *
	 */
	private class WorkerRBListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				JRadioButton rb = (JRadioButton)e.getSource();
				currentSelectedWorker = rb.getText();
				selectedWorker = true;
				checkIfGoodToGo();
			} catch (Exception e1) {}
		}
	}
	
	/**
	 * Inner class which is used to listen to the buttons of the bands
	 * @author JakobK98
	 *
	 */
	private class BandRBListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JRadioButton rb = (JRadioButton)e.getSource();
			currentSelectedBand = rb.getText();
			controller.checkContact(currentSelectedBand);
			selectedBand = true;
			checkIfGoodToGo();
		}
	}
	
//	public static void main(String[] args) {
//		WorkerScreen screen = new WorkerScreen();
//	}

}
