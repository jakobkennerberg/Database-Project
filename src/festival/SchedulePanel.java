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
	private String weekday = "Wednesday";
	private String time = "15.00 - 17.00";
	private String stage = "Ibiza";
	private String bandName = "Yuns pågar";
	private Random rand = new Random();
	
	public SchedulePanel(JRadioButton btn) {
		lblTime = new JLabel(time);
		lblStage = new JLabel(stage);
		lblbandName = new JLabel(bandName);
		lblweekday = new JLabel(weekday);
		
		setLayout(null);
		setSize(new Dimension(325, 65));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblweekday.setBounds(30, 10, 200, 21);
		lblTime.setBounds(30, 30, 200, 21);
		lblStage.setBounds(30, 51, 200, 21);
		lblbandName.setBounds(30, 72, 200, 21);
		
		btn.setBounds(280, 20, 65, 65);
		
		picturePanel = new JPanel();
		picturePanel.setBounds(200, 20, 65, 65);
		picturePanel.setBackground(randomColor());
		
		add(btn);
		add(picturePanel);
		add(lblTime);
		add(lblStage);
		add(lblbandName);	
		add(lblweekday);
	}
	
	public Color randomColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color color = new Color(r, g, b);
		return color;
	}
}
