package festival;

import java.awt.CardLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import connection.Communication;

public class CardController extends JFrame implements CardSwitcher {
	CardLayout cardLayout = new CardLayout();
	private JPanel cards = new JPanel(cardLayout);
	private String currentCard;
	private ConcertPanel concertCard; // = new ConcertPanel(this);
	private WorkerScreen workerCard; // = new WorkerScreen(this);
	private Communication dbManager;
	
	public CardController(Communication comm) {
		dbManager = comm;
		setUpCards();
		currentCard = "workerScreen";
		cardLayout.show(cards, currentCard);
		getContentPane().add(cards);
		this.setBackground(Color.PINK);
		this.setTitle("Office Screen");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}
	
	public void setUpCards() {
		
		workerCard = new WorkerScreen(this);
		workerCard.setListener(this);
		JPanel cardWorker = new JPanel();
		cardWorker.add(workerCard);
		
		concertCard = new ConcertPanel(this);
		concertCard.setListener(this);
		JPanel cardConcert = new JPanel();
		cardConcert.add(concertCard);
		
		cards.add(cardWorker, "workerScreen");
		cards.add(cardConcert, "concertScreen");
		
	}
	
	public void nextPanel() {
		if(currentCard.equals("workerScreen")) {
			currentCard = "concertScreen";
		}
		cardLayout.show(cards, currentCard);
	}
	
	public void goBack() {
		if(currentCard.equals("concertScreen")) {
			currentCard = "workerScreen";
		}
		cardLayout.show(cards, currentCard);
	}
	
	public int getGridSize(String str) {
		int gridSize = dbManager.getCount(str);
		return gridSize;
	}
	
	public ArrayList<String> getBandList() {
		ArrayList<String> list = dbManager.getBandNameList();
		return list;
	}
	
	public ArrayList<String> getWorkerList() {
		ArrayList<String> list = dbManager.getWorkerNameList();
		return list;
	}
	
	public void addBand(String bandname, String orgin, ArrayList<BandMember> list) {	
		try {
			dbManager.insertBand(bandname, orgin, list);
			for(BandMember mem : list) {
				System.out.println(mem.getName());
				dbManager.insertMember(mem.getName(), mem.getCountry(), mem.getInstrument(), mem.getXtraInfo());
				System.out.println("igenom första loopen");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public void updateLabels(String band, String worker) {
		concertCard.setBandLabel(band);
		concertCard.setContactLabel(worker);
	}
}
