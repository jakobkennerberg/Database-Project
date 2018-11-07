package festival;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

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
		
		//btn.setBounds(280, 20, 65, 65);
		
		picturePanel = new JPanel();
		picturePanel.setBounds(200, 20, 65, 65);
		picturePanel.setBackground(randomColor());
		
		//add(btn);
		add(picturePanel);
		add(lblTime);
		add(lblStage);
		add(lblbandName);	
		add(lblweekday);
	}
	
	public String getBandname() {
		return this.bandName;
	}
	
	public int getDay() {
		int dayID = Integer.parseInt(weekday);
		return dayID;
	}
	
	public void addRadioButton(JRadioButton btn) {
		btn.setBounds(280, 20, 65, 65);
		add(btn);
	}
	
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	
	public Color randomColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color color = new Color(r, g, b);
		return color;
	}
	
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
