package festival;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import connection.Communication;

public class VisitorScreen extends JPanel implements ActionListener {
	private VisitorController controller;
	private JLabel lblschedule = new JLabel("Schedule");
	private JLabel lblinfo = new JLabel("Band Information");
	private JButton btnView = new JButton("View Band Information");
	private JButton btnUp = new JButton("Previous");
	private JButton btnDown = new JButton("Next");
	private JTextArea informationArea = new JTextArea();
	private JPanel schedulePanel = new JPanel();
	private ButtonGroup bg = new ButtonGroup();
	private RadioButtonListener rbgroup = new RadioButtonListener();
	private String currentChosenBand;
	private ArrayList<SchedulePanel> scheduleList = new ArrayList<SchedulePanel>();
	
	private int scheduleSize = 5;
	private String bandInfoShowing = "";
	
	public VisitorScreen(VisitorController controller) {
		setPreferredSize(new Dimension(1000, 700));
		setLayout(null);
		this.controller = controller;
		add(leftPanel());
		add(rightPanel());
		scheduleList = controller.getSchedule(scheduleList);
		updateSchedule(scheduleList);
		//scheduleSize = scheduleList.size();
		
		btnView.addActionListener(this);
		btnUp.addActionListener(this);
		btnDown.addActionListener(this);
		showScreen();
		
	}
	
	public JPanel leftPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 500, 700);
		panel.setBackground(Color.PINK);
		
		lblschedule.setBounds(20, 60, 150, 100);
		lblschedule.setFont(new Font("SansSerif", Font.BOLD, 25));
		
		schedulePanel.setBounds(175, 90, 325, 500);
		schedulePanel.setBackground(Color.WHITE);
		schedulePanel.setLayout(new GridLayout(scheduleSize,1));
		
//		JScrollPane scroll = new JScrollPane(schedulePanel);
//		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		JViewport vp = scroll.getViewport();
//		vp.add(schedulePanel);
//		vp.setBounds(175, 90, 325, 500);
//		scroll.setBounds(175, 90, 325, 500);
		
//		updateSchedule("hahhahahhhaahhahhahah");
		btnUp.setBounds(175, 25, 325, 50);
		btnUp.setEnabled(false);
		btnDown.setBounds(175, 600, 325, 50);
		
		panel.add(btnUp);
		panel.add(btnDown);
		panel.add(schedulePanel);
		panel.add(lblschedule);
		panel.add(btnView);
		return panel;
	}
	
	public JPanel rightPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(500, 0, 500, 700);
		panel.setBackground(Color.PINK);
		lblinfo.setBounds(100, 30, 300, 75);
		lblinfo.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblinfo.setHorizontalAlignment(JLabel.CENTER);
		informationArea.setBounds(100, 90, 300, 500);
		informationArea.setEnabled(false);
		
		btnView.setBounds(100, 600, 300, 50);
		btnView.setEnabled(false);
		btnView.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		panel.add(btnView);
		panel.add(informationArea);
		panel.add(lblinfo);
		return panel;
	}
	
	public void updateSchedule(ArrayList<SchedulePanel> list) {
		for(SchedulePanel panel : list) {
			JRadioButton btn = new JRadioButton();
			btn.addActionListener(rbgroup);
			btn.setText(panel.getBandname());
			btn.setForeground(Color.WHITE);
			bg.add(btn);
			panel.addRadioButton(btn);
			//panel.setSize(325, 65);
			schedulePanel.add(panel);
		}
//		for(int i = 0; i < scheduleSize; i++) {
//			JRadioButton btn = new JRadioButton();
//			btn.addActionListener(rbgroup);
//			btn.setText(bandname);
//			btn.setForeground(Color.WHITE);
//			bg.add(btn);
//			SchedulePanel panel = new SchedulePanel(btn);
//			//panel.setSize(325, 65);
//			schedulePanel.add(panel);
//		}
	}
	
	public int getScheduleSize() {
//		int = conn.getScheduleSize();
		return scheduleSize;
	}
	
	public void addBandInfo(ArrayList<String> infoList) {
		bandInfoShowing += "Bandname: " + infoList.get(0) + "\nOrigin: " + infoList.get(1) + "\nMembers:\n\n";
		informationArea.setText(bandInfoShowing);
	}
	
	public void addBandMemberInfo(ArrayList<BandMember> memberList) {
		for(BandMember member : memberList) {
			bandInfoShowing += "Name: "+ member.getName()+"\nOrigin: "+member.getCountry() + "\nInstrument: " + member.getInstrument() + "\nExtra info: " + member.getXtraInfo() + "\n\n";
		}
		informationArea.setText(bandInfoShowing);
	}
	
	public void showScreen() {
		JFrame frame = new JFrame("Visitor Screen");
		frame.setPreferredSize(new Dimension(1000, 700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnView) {
			informationArea.setText("");
			controller.getBandInfo(currentChosenBand);
			controller.getBandMemberInfo(currentChosenBand);
		}
		if(e.getSource()==btnUp) {
			
		}
		if(e.getSource()==btnDown) {
			
		}
	}
	
	private class RadioButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnView.setEnabled(true);
			try {
				JRadioButton rb = (JRadioButton)e.getSource();
				String bandName = rb.getText();
				currentChosenBand = bandName;

			} catch (Exception e1) {}
		}
	}
		
//	public static void main(String[] args) {
//		VisitorScreen screen = new VisitorScreen();
//	}
}
