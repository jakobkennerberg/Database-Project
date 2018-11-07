package festival;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class represents the GUI used to book concerts
 * @author JakobK98
 *
 */
public class ConcertPanel extends JPanel implements ActionListener {
	private CardSwitcher cardSwitcher;
	private JButton btnPublish = new JButton("Publish Concert");
	private JButton btnBack = new JButton("<- Back");
	private JComboBox<String> stageBox = new JComboBox<String>();
	private JComboBox<String> dayBox = new JComboBox<String>();
	private JComboBox<String> timeBox = new JComboBox<String>();
	private JPanel stagePanel = new JPanel();
	private JLabel lblStagePic = new JLabel();
	private JLabel lblBand = new JLabel("Band: ");
	private JLabel lblContactPerson = new JLabel("Contact person: ");
	private JLabel lblTime = new JLabel("Time");
	private JLabel lblStage = new JLabel("Stage");
	private JLabel lblDay = new JLabel("Day");
	private JLabel lblBooked = new JLabel("");
	private CardController controller;
	
	private ImageIcon stageMallorca = new ImageIcon("files/MallorcaScene.jpg");
	private ImageIcon stageDielsel = new ImageIcon("files/DieselTent.jpg");
	private ImageIcon stageForum = new ImageIcon("files/ForumScene.jpg");
	private ImageIcon stageIbiza = new ImageIcon("files/IbizaScene.jpg");
	Font lblFont = new Font("SansSerif", Font.BOLD, 20);
	private Timer timer;
	private String selectedBand;
	private ArrayList<String> times = new ArrayList<String>();
	
	/**
	 * Constructor
	 * @param controller
	 */
	public ConcertPanel(CardController controller) {
		setPreferredSize(new Dimension(1000, 700));
		setBackground(Color.PINK);
		setLayout(null);
		this.controller = controller;
		stageMallorca = scalePicture(stageMallorca);
		stageDielsel = scalePicture(stageDielsel);
		stageForum = scalePicture(stageForum);
		stageIbiza = scalePicture(stageIbiza);
		add(specifyPanel());
	}
	
	/**
	 * Method used to create the GUI
	 * @return
	 */
	public JPanel specifyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1000, 700);
		panel.setBackground(Color.PINK);
		
		btnBack.setBounds(25, 25, 190, 50);
		btnBack.addActionListener(this);
		btnPublish.setBounds(735, 605, 190, 50);
		btnPublish.addActionListener(this);
		
		lblBand.setBounds(30, 390, 400, 100);
		lblBand.setFont(lblFont);
		lblContactPerson.setBounds(30, 490, 400, 100);
		lblContactPerson.setFont(lblFont);
		lblBooked.setBounds(550, 250, 400, 300);
		
		lblDay.setBounds(30, 100, 200, 50);
		lblDay.setFont(lblFont);
		dayBox.setBounds(25, 135, 200, 50);
		dayBox.addItem("Thursday");
		dayBox.addItem("Friday");
		dayBox.addItem("Saturday");
		dayBox.addActionListener(this);
			
		lblStage.setBounds(30, 200, 200, 50); 
		lblStage.setFont(lblFont);
		stageBox.setBounds(25, 235, 200, 50); 
		stageBox.addItem("Mallorca Stage");
		stageBox.addItem("Diesel Tent");
		stageBox.addItem("Forum Stage");
		stageBox.addItem("Ibiza Stage");
		stageBox.addActionListener(this);
		
		stagePanel.setBounds(275, 25, 650, 350);
		stagePanel.setBackground(Color.WHITE);
		stagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblStagePic.setBounds(0, 50, 625, 335);
		lblStagePic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		String selectedStage = (String) stageBox.getSelectedItem();
		stageSelector(selectedStage);
		stagePanel.add(lblStagePic);
		
		lblTime.setBounds(30, 300, 200, 50); 
		lblTime.setFont(lblFont);
		timeBox.setBounds(25, 335, 200, 50); 
		
		setUpTimes();
		
		panel.add(lblBooked);
		panel.add(lblDay);
		panel.add(lblStage);
		panel.add(lblTime);
		panel.add(stageBox);
		panel.add(dayBox);
		panel.add(timeBox);
		
		panel.add(stagePanel);
		panel.add(lblContactPerson);
		panel.add(lblBand);
		panel.add(btnBack);
		panel.add(btnPublish);
		
		return panel;
	}
	
	/**
	 * Method which sets the listener to the class
	 * @param listener
	 */
	public void setListener(CardSwitcher listener) {
		cardSwitcher = listener;
	}
	
	/**
	 * Method which sets the band label to the current band being booked
	 * @param bandname
	 */
	public void setBandLabel(String bandname) {
		lblBand.setText("Band: " + bandname);
		selectedBand = bandname;
	}
	
	/**
	 * Method which sets the contactperson label to the assigned contactperson of the band being booked
	 * @param str
	 */
	public void setContactLabel(String str) {
		lblContactPerson.setText("Contact person: " + str);
	}
	
	/**
	 * Method which updates the GUI with the availible times
	 * @param bookedTimes
	 */
	public void updateAvailible(ArrayList<String> bookedTimes) {
		timeBox.removeAllItems();
		boolean eraseTime = false;
		for(int i = 0; i < times.size(); i++) {
			String currentTime = times.get(i);
			for(int j = 0; j < bookedTimes.size(); j++) {
				if(currentTime.equals((String)bookedTimes.get(j))) {
					eraseTime = true;
					break;
				}
			}
			if(eraseTime == false) {
				timeBox.addItem(currentTime);
			}
			eraseTime = false;
		}
		if(timeBox.getItemCount()< 1) {
			btnPublish.setEnabled(false);
		} else if(timeBox.getItemCount() > 0) {
			btnPublish.setEnabled(true);
		}
	}
	
	/**
	 * Method which sets up the original choices of times to book
	 */
	public void setUpTimes() {
		times.add("13:00 - 15:00");
		times.add("16:00 - 18:00");
		times.add("19:00 - 21:00");
		times.add("22:00 - 24:00");
		times.add("01:00 - 03:00");
	}

	/**
	 * Method which listens to the buttons and boxes in the GUI
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnBack) {
			controller.setAlreadyAssigned();
			lblBooked.setText("");
			cardSwitcher.goBack();
		}
		if(e.getSource()==btnPublish) {
			controller.specifyConcert(selectedBand, (String)dayBox.getSelectedItem(), (String)stageBox.getSelectedItem(), (String)timeBox.getSelectedItem());
			btnPublish.setText("Published");
			btnPublish.setForeground(Color.GREEN);
			
			timer = new Timer(2000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardSwitcher.goBack();
					stopTimer();	
				}
			});
			timer.start();
		}
		if(e.getSource()==stageBox) {
			getBookedTime();
			String stage = (String)stageBox.getSelectedItem();
			stageSelector(stage);
		}
		if(e.getSource()==dayBox) {
			getBookedTime();
		}
		
	}
	
	/**
	 * Method used to stop the timer to switch back a panel when a band have been booked
	 */
	public void stopTimer() {
		timer.stop();
		btnPublish.setText("Publish Concert");
		btnPublish.setForeground(Color.BLACK);
	}
	
	/**
	 * Method used to scale the pictures
	 * @param image
	 * @return
	 */
	public ImageIcon scalePicture(ImageIcon image) {
		Image transImage = image.getImage();
		Image scaledImage = transImage.getScaledInstance(625, 335, Image.SCALE_SMOOTH);		
		image = new ImageIcon(scaledImage);
		return image;
	}
	
	/**
	 * Method used to display the days and times which the current band already has been booked for
	 * @param bandname
	 * @param list
	 */
	public void setUpBookedTimes(String bandname, ArrayList<BandSpecificTime> list) {
		String booked = "<html>"+bandname + " already have the following times scheduled:<br><html>";
		for(BandSpecificTime time : list) {
			booked += "<html>" + controller.getDay(time.getDayID()) + ",  " + time.getStarttime()+ " - " + time.getEndtime() + "<br><html>";
		}
		lblBooked.setText(booked);
	}
	
	/**
	 * Method which selects the corresponding picture to the selected stage
	 * @param stageName
	 */
	public void stageSelector(String stageName) {
		switch(stageName) {
		
		case "Mallorca Stage" : {
			lblStagePic.setIcon(stageMallorca);
		}
		break;
		case "Diesel Tent" : {
			lblStagePic.setIcon(stageDielsel);
		}
		break;
		case "Forum Stage" : {
			lblStagePic.setIcon(stageForum);
		}
		break;
		case "Ibiza Stage" : {
			lblStagePic.setIcon(stageIbiza);
		}
		break;
		}
	}
	
	/**
	 * Method used to get the booked times of a certain day and stage
	 */
	public void getBookedTime() {
		String day = (String)dayBox.getSelectedItem();
		String scene = (String)stageBox.getSelectedItem();
		controller.getBookedTimes(day, scene);
	}
	
//	public static void main(String[] args) {
//		ConcertPanel panel = new ConcertPanel();
//		JFrame frame = new JFrame("Office Screen");
//		frame.setPreferredSize(new Dimension(1000, 700));
//		frame.pack();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.add(panel);
//		frame.setVisible(true);
//		frame.setLocationRelativeTo(null);
//	}

}
