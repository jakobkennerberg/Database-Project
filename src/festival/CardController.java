package festival;

import java.awt.CardLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import connection.Communication;

/**
 * This class is the controller of the worker side
 * @author JakobK98
 *
 */
public class CardController extends JFrame implements CardSwitcher {
	CardLayout cardLayout = new CardLayout();
	private JPanel cards = new JPanel(cardLayout);
	private String currentCard;
	private ConcertPanel concertCard;
	private WorkerScreen workerCard; 
	private Communication dbManager;
	
	/**
	 * Constructor
	 * @param comm
	 */
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
	
	/**
	 * Method used to set up the panels of the Worker side
	 */
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
	
	/**
	 * Method used to switch to the next panel
	 */
	public void nextPanel() {
		if(currentCard.equals("workerScreen")) {
			currentCard = "concertScreen";
		}
		cardLayout.show(cards, currentCard);
	}
	
	/**
	 * Method used to switch to the previous panel
	 */
	public void goBack() {
		if(currentCard.equals("concertScreen")) {
			currentCard = "workerScreen";
		}
		cardLayout.show(cards, currentCard);
	}
	
	/**
	 * Method used to get the list of booked times of a specific day and stage from the communcation class, and gives it to the GUI
	 * @param day
	 * @param stage
	 */
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
	
	/**
	 * Method used to get the correct values for a insert of a concert, and passes it on to the communication class
	 * @param band
	 * @param day
	 * @param stage
	 * @param time
	 */
	public void specifyConcert(String band, String day, String stage, String time) {
		int bandID = 0;
		int dayID = 0;
		int stageID = 0;
		String parts[] = time.split(" - ");
		String starttime= parts[0];
		String endtime = parts[1];
		
		dayID = getDayID(day);
		stageID = getStageID(stage);
		
		try {
			bandID = dbManager.getBandID(band);
			dbManager.insertConcert(bandID, dayID, stageID, starttime, endtime);
		} catch (SQLException e) {}
	}
	
	/**
	 * Method which acts as a middle hand when assignig a contact person to a band
	 * @param band
	 * @param contact
	 */
	public void assignContact(String band, String contact) {
		try {
			dbManager.insertContact(band, contact);
		} catch (SQLException e) {}
	}
	
	/**
	 * Method which handles the logic to control if a band already have a contactperson
	 * @param bandname
	 */
	public void checkContact(String bandname) {
		try {
			boolean assigned = dbManager.checkContact(bandname);
			if(assigned == true) {
				setAlreadyAssigned();
			} else if(assigned == false) {
				workerCard.setAssigned("-", false);
				workerCard.setUpWorkerList(dbManager.getWorkerNameList(), true);
			}
		} catch (SQLException e) {}
	}
	
	/**
	 * Method used to update the GUI when the band already is assigned to a contactperson
	 */
	public void setAlreadyAssigned() {
		workerCard.setAssigned(dbManager.getContactName(), true);
		workerCard.setUpWorkerList(dbManager.getWorkerNameList(), false);
	}
	
	/**
	 * Method used to get the amount of either workers or bands from the communication class
	 * @param str
	 * @return
	 */
	public int getGridSize(String str) {
		int gridSize = dbManager.getCount(str);
		return gridSize;
	}
	
	/**
	 * Method used to get all the names of the bands from the communication class
	 * @param list
	 * @return
	 */
	public ArrayList<String> getBandList(ArrayList<String> list ) {
		list = dbManager.getBandNameList();
		return list;
	}
	
	/**
	 * Method used to get all the names of the workers from the communication class
	 * @return
	 */
	public ArrayList<String> getWorkerList() {
		ArrayList<String> list = dbManager.getWorkerNameList();
		return list;
	}
	
	/**
	 * Method used to call a method in the GUI to update the availible times to book
	 */
	public void updateAvailible() {
		concertCard.getBookedTime();
	}
	
	/**
	 * Method which handles the logic when a band shall be inserted into the database 
	 * @param bandname
	 * @param orgin
	 * @param list
	 */
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
	
	/**
	 * Method used to get the ID of a stage
	 * @param stage
	 * @return
	 */
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
	
	/**
	 * Method used to get the ID of the day selected
	 * @param day
	 * @return
	 */
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
	
	/**
	 * Method used to get the name of the day based on the ID
	 * @param id
	 * @return
	 */
	public String getDay(int id) {
		String day = "";
		switch(id) {
		
		case 1: {
			day = "Thursday" ;
		}
		break;
		case 2 : {
			day = "Friday";
		}
		break;
		case 3 : {
			day = "Saturday";
		}
		break;
		}
		
		return day;
	}
	
	/**
	 * Method which update the concert-booking GUI with text, based on the choices made in the previous GUI panel
	 * @param band
	 * @param worker
	 */
	public void updateLabels(String band, String worker) {
		concertCard.setBandLabel(band);
		concertCard.setContactLabel(worker);
		try {
			ArrayList<BandSpecificTime> list = dbManager.getBandSpecificTimes(band);
			if(list.size() > 0) {
				concertCard.setUpBookedTimes(band, list);
			}
			
		} catch (SQLException e) {}
	}
}
