package festival;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CardController extends JFrame implements CardSwitcher {
	CardLayout cardLayout = new CardLayout();
	private JPanel cards = new JPanel(cardLayout);
	private String currentCard;
	private ConcertPanel concertCard = new ConcertPanel();
	private WorkerScreen workerCard = new WorkerScreen();
	
	public CardController() {
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
		
		workerCard = new WorkerScreen();
		workerCard.setListener(this);
		JPanel cardWorker = new JPanel();
		cardWorker.add(workerCard);
		
		concertCard = new ConcertPanel();
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
}
