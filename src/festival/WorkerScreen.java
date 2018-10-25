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

public class WorkerScreen extends JPanel implements ActionListener {
	
	private CardSwitcher cardSwitcher;
	private JPanel bandPanel;
	private JPanel bandListPanel;
	private JPanel workerListPanel;
	private JLabel lblBand = new JLabel("Bands");
	private JLabel lblWorker = new JLabel("Assign Contact person");
	private JButton btnAddBand = new JButton("Add Band");
	private JButton btnSpecify = new JButton("Specify Concert");
	
	private int bandAmount = 50;
	private int workerAmount = 50;
	private ButtonGroup workerbg = new ButtonGroup();
	private ButtonGroup bandbg = new ButtonGroup();
	private WorkerRBListener rbWorker = new WorkerRBListener();
	private BandRBListener rbBand = new BandRBListener();
	private String currentSelectedBand;
	private String currentSelectedWorker;
	private boolean selectedWorker = false;
	private boolean selectedBand = false;
	
	public WorkerScreen() {
		setPreferredSize(new Dimension(1000, 700));
		setLayout(null);
		setBackground(Color.PINK);
		add(bandPanel());
	}
	
	public JPanel bandPanel() {
		bandPanel = new JPanel();
		bandPanel.setLayout(null);
		bandPanel.setBounds(0, 0, 1050, 750);
		bandPanel.setBackground(Color.PINK);
		Font lblFont = new Font("SansSerif", Font.BOLD, 25);
		
		bandListPanel = new JPanel();
		//bandListPanel.setLayout(new GridLayout(getBandListCount(), 1)); //Hämta antalet band från DB
		JScrollPane bandScroll = new JScrollPane(bandListPanel);
		bandScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		bandScroll.setBounds(50, 75, 300, 500);
		lblBand.setBounds(50, 25, 300, 50);
		lblBand.setFont(lblFont);
		lblBand.setHorizontalAlignment(JLabel.CENTER);
		
		workerListPanel = new JPanel();
		workerListPanel.setLayout(new GridLayout(getWorkerListCount(), 1)); //Hämta antalet workers från DB
		JScrollPane workerScroll = new JScrollPane(workerListPanel);
		workerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		workerScroll.setBounds(450, 75, 300, 500);
		lblWorker.setBounds(450, 25, 300, 50);
		lblWorker.setFont(lblFont);
		lblWorker.setHorizontalAlignment(JLabel.CENTER);
		
		btnAddBand.setBounds(50, 600, 300, 50);
		btnAddBand.addActionListener(this);
		btnSpecify.setBounds(800,600,150,50);
		//btnSpecify.setEnabled(false);
		btnSpecify.addActionListener(this);
		
		//setUpWorkerList(conn.getWorkers); | hämta namnen på arbetarna i DB
		//setUpBandList(conn.getBands()); | hämta namnen på banden i DB
		
		bandPanel.add(btnSpecify);
		bandPanel.add(btnAddBand);
		bandPanel.add(lblBand);
		bandPanel.add(lblWorker);
		bandPanel.add(workerScroll);
		bandPanel.add(bandScroll);
		
		return bandPanel;
	}
	
	
	public void setListener(CardSwitcher listener) {
		cardSwitcher = listener;
	}
	
	public void setUpBandList(ArrayList<String> bandList) {
		bandListPanel.setLayout(new GridLayout(getBandListCount(), 1));
		bandListPanel.removeAll();
		bandListPanel.repaint();
		
		for (int i = 0; i < bandList.size(); i++) {
			JRadioButton btn = new JRadioButton(bandList.get(i));
			btn.setSize(new Dimension(300, 60));
			bandbg.add(btn);
			btn.addActionListener(rbBand);
			bandListPanel.add(btn);
		}
	}
	
	public void setUpWorkerList(ArrayList<String> workerList) {
		for (int i = 0; i < workerList.size(); i++) {
			JRadioButton btn = new JRadioButton(workerList.get(i));
			btn.setSize(new Dimension(300, 60));
			workerbg.add(btn);
			btn.addActionListener(rbWorker);
			workerListPanel.add(btn);
		}
	}
	
	public int getBandListCount() {
		//bandAmount = conn.amountOfBands | hämta antalet band i DB
		return bandAmount;
	}
	
	public int getWorkerListCount() {
		//workerAmount = conn.amountOfWorkers | hämta antalet arbetare i DB
		return workerAmount;
	}
	
	public void checkIfGoodToGo() {
		if(selectedWorker == true && selectedBand == true) {
			btnSpecify.setEnabled(true);
		} else {
			btnSpecify.setEnabled(false);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAddBand) {
			new BandAdderPanel(this);
		}
		if(e.getSource()==btnSpecify) {
			cardSwitcher.nextPanel();	
		}
	}
	
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
	
	private class BandRBListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JRadioButton rb = (JRadioButton)e.getSource();
			currentSelectedBand = rb.getText();
			selectedBand = true;
			checkIfGoodToGo();
		}
	}
	
	public static void main(String[] args) {
		WorkerScreen screen = new WorkerScreen();
	}

}
