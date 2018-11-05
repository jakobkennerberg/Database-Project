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
	
	public void getBookedTimes(String day, String stage) {
		int stageID = 0;
		int dayID = 0;
		dayID = getDayID(day);
		stageID = getStageID(stage);

		try {
			ArrayList<String> booked = dbManager.getBookedTimes(dayID, stageID);
			concertCard.updateAvailible(booked);
		}catch (SQLException e) {}
	}
	
	public void specifyConcert(String band, String day, String stage, String time) {
		int bandID = 0;
		int dayID = 0;
		int stageID = 0;
		String divide = time;
		String parts[] = divide.split(" - ");
		String starttime= parts[0];
		String endtime = parts[1];
		
		dayID = getDayID(day);
		stageID = getStageID(stage);
		
		try {
			bandID = dbManager.getBandID(band);
			dbManager.insertConcert(bandID, dayID, stageID, starttime, endtime);
		} catch (SQLException e) {
		}
	}
	
	public void assignContact(String band, String contact) {
		try {
			dbManager.insertContact(band, contact);
		} catch (SQLException e) {}
	}
	
	public void checkContact(String bandname) {
		try {
			boolean assigned = dbManager.checkContact(bandname);
			if(assigned == true) {
				setAlreadyAssigned();
			} else if(assigned == false) {
				workerCard.setAssigned("-", false);
				workerCard.setUpWorkerList(dbManager.getWorkerNameList(), true);
			}
		} catch (SQLException e) {
		}
	}
	
	public void setAlreadyAssigned() {
		workerCard.setAssigned(dbManager.getContactName(), true);
		workerCard.setUpWorkerList(dbManager.getWorkerNameList(), false);
	}
	
	public int getGridSize(String str) {
		int gridSize = dbManager.getCount(str);
		return gridSize;
	}
	
	public ArrayList<String> getBandList(ArrayList<String> list ) {
		list = dbManager.getBandNameList();
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
				dbManager.memberControl(mem.getName(), mem.getCountry(), mem.getInstrument(), mem.getXtraInfo());
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public int getStageID(String stage) {
		int stageID = 0;
		switch(stage) {
		
		case "Mallorca Stage" : {
			stageID = 1;
		}
		break;
		case "Diesel Tent" : {
			stageID = 2;
		}
		break;
		case "Forum Stage" : {
			stageID = 3;
		}
		break;
		case "Ibiza Stage" : {
			stageID = 4;
		}
		break;
		}
		return stageID;
	}
	
	public int getDayID(String day) {
		int dayID = 0;
		switch(day) {
		
		case "Thursday" : {
			dayID = 1;
		}
		break;
		case "Friday" : {
			dayID = 2;
		}
		break;
		case "Saturday" : {
			dayID = 3;
		}
		break;
		}
		
		return dayID;
	}
	
	public void updateLabels(String band, String worker) {
		concertCard.setBandLabel(band);
		concertCard.setContactLabel(worker);
	}
}
