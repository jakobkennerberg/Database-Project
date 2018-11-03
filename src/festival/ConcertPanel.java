package festival;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

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
	private CardController controller;
	
	private ImageIcon stageMallorca = new ImageIcon("files/MallorcaScene.jpg");
	private ImageIcon stageDielsel = new ImageIcon("files/DieselTent.jpg");
	private ImageIcon stageForum = new ImageIcon("files/ForumScene.jpg");
	private ImageIcon stageIbiza = new ImageIcon("files/IbizaScene.jpg");
	Font lblFont = new Font("SansSerif", Font.BOLD, 20);
	private Timer timer;
	
	
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
		
		lblDay.setBounds(30, 100, 200, 50);
		lblDay.setFont(lblFont);
		dayBox.setBounds(25, 135, 200, 50);
		dayBox.addItem("Thursday");
		dayBox.addItem("Friday");
		dayBox.addItem("Saturday");
		
		lblTime.setBounds(30, 300, 200, 50); 
		lblTime.setFont(lblFont);
		timeBox.setBounds(25, 335, 200, 50); 
		timeBox.addItem("13.00 - 15.00");
		timeBox.addItem("16.00 - 18.00");
		timeBox.addItem("19.00 - 21.00");
		timeBox.addItem("22.00 - 24.00");
		timeBox.addItem("01.00 - 03.00");
		
		
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
	
	public void setListener(CardSwitcher listener) {
		cardSwitcher = listener;
	}
	
	public void setBandLabel(String str) {
		lblBand.setText("Band: " + str);
	}
	
	public void setContactLabel(String str) {
		lblContactPerson.setText("Contact person: " + str);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnBack) {
			controller.setAlreadyAssigned();
			cardSwitcher.goBack();
		}
		if(e.getSource()==btnPublish) {
			btnPublish.setText("Published");
			btnPublish.setForeground(Color.GREEN);
			
			timer = new Timer(3000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardSwitcher.goBack();
					stopTimer();	
				}
			});
			timer.start();
		}
		if(e.getSource()==stageBox) {
			String stage = (String)stageBox.getSelectedItem();
			stageSelector(stage);
		}
		
	}
	
	public void stopTimer() {
		timer.stop();
		btnPublish.setText("Publish Concert");
		btnPublish.setForeground(Color.BLACK);
	}
	
	public ImageIcon scalePicture(ImageIcon image) {
		Image transImage = image.getImage();
		Image scaledImage = transImage.getScaledInstance(625, 335, Image.SCALE_SMOOTH);		
		image = new ImageIcon(scaledImage);
		return image;
	}
	
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
