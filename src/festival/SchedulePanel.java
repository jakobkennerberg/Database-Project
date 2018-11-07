package festival;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Class which represents the individual booked concerts which is displayed to the visitors
 * @author JakobK98
 *
 */
public class SchedulePanel extends JPanel {
	private JLabel lblTime;
	private JLabel lblStage;
	private JLabel lblbandName;
	private JLabel lblweekday;
	private JPanel picturePanel;
	private String weekday;
	private String time;
	private String stage;
	private String bandName;
	private Random rand = new Random();
	
	/**
	 * Constructor
	 * @param scene
	 * @param bandname
	 * @param day
	 * @param starttime
	 * @param endtime
	 */
	public SchedulePanel(String scene, String bandname, int day, String starttime, String endtime) {
		weekday = getDay(day);
		time = starttime + " - " + endtime;
		bandName = bandname;
		stage = scene;
		lblTime = new JLabel(time);
		lblStage = new JLabel(stage);
		lblbandName = new JLabel(bandName);
		lblweekday = new JLabel(weekday);
		
		setLayout(null);
		setPreferredSize(new Dimension(325, 65));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblweekday.setBounds(30, 10, 200, 21);
		lblTime.setBounds(30, 30, 200, 21);
		lblStage.setBounds(30, 51, 200, 21);
		lblbandName.setBounds(30, 72, 200, 21);
				
		picturePanel = new JPanel();
		picturePanel.setBounds(200, 20, 65, 65);
		picturePanel.setBackground(randomColor());
		
		add(picturePanel);
		add(lblTime);
		add(lblStage);
		add(lblbandName);	
		add(lblweekday);
	}
	
	/**
	 * Method used to get the bandname of the panel
	 * @return
	 */
	public String getBandname() {
		return this.bandName;
	}
	
	/**
	 * Method used to get the day ID of the panel (used to convert to the day name)
	 * @return
	 */
	public int getDay() {
		int dayID = Integer.parseInt(weekday);
		return dayID;
	}
	
	/**
	 * Method used to add a radiobutton to the panel
	 * @param btn
	 */
	public void addRadioButton(JRadioButton btn) {
		btn.setBounds(280, 20, 65, 65);
		add(btn);
	}
	
	/**
	 * Method used to set the name of the day
	 * @param weekday
	 */
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	
	/**
	 * Method used to generate a random color
	 * @return
	 */
	public Color randomColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color color = new Color(r, g, b);
		return color;
	}
	
	/**
	 * Method used to convert the dayID to the actual name of the day
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
}
