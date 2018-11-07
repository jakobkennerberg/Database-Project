package festival;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Class which represents the GUI used to add a band
 * @author JakobK98
 *
 */
public class BandAdderPanel extends JPanel implements ActionListener, DocumentListener {
	private JFrame frame;
	private WorkerScreen wScreen;
	private MemberAdderPanel memberPanel;
	private JTextField tfBandName = new JTextField();
	private JTextField tfOrgin = new JTextField();
	private JLabel lblBandName = new JLabel("Band name:");
	private JLabel lblOrgin = new JLabel("Country (orgin) :");
	private JLabel lblmbr1 = new JLabel("");
	private JLabel lblmbr2 = new JLabel("");
	private JLabel lblmbr3 = new JLabel("");
	private JLabel lblmbr4 = new JLabel("");
	private JLabel lblmbr5 = new JLabel("");
	private ImageIcon standardPic = new ImageIcon("files/band.png");
	private JLabel lblPic;
	private JLabel lblmember = new JLabel("Members");
	private JButton btnSubmit = new JButton("Submit Band");
	private JButton btnAdd = new JButton("Add Band Members");
	private boolean correctName = false;
	private boolean correctOrgin = false;
	private int memberCounter = 0;
	private ArrayList<BandMember> memberList = new ArrayList<BandMember>();
	
	/**
	 * Constructor
	 * @param wScreen
	 */
	public BandAdderPanel(WorkerScreen wScreen) {
		this.wScreen = wScreen;
		setLayout(null);
		standardPic = scalePicture(standardPic, 190, 190);
		setBackground(Color.PINK);
		add(infoPanel());
		showStartMenu();
	}
	
	/**
	 * Method used to create the GUI
	 * @return
	 */
	public JPanel infoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.PINK);
		panel.setBounds(0, 0, 700, 500);
		Font lblFont = new Font("SansSerif", Font.BOLD, 25);
		
		tfBandName.setBounds(300, 50, 300, 50);
		tfOrgin.setBounds(300, 125, 300, 50);
		tfBandName.getDocument().addDocumentListener(this);
		tfOrgin.getDocument().addDocumentListener(this);
		lblBandName.setBounds(50, 50, 250, 50);
		lblBandName.setFont(lblFont);
		lblOrgin.setFont(lblFont);
		lblOrgin.setBounds(50, 125, 250, 50);
		
		lblmember.setBounds(350, 175, 200, 50);
		lblmember.setFont(lblFont);
		lblmember.setHorizontalAlignment(JLabel.CENTER);
		lblmbr1.setBounds(350, 220, 200, 40);
		lblmbr1.setHorizontalAlignment(JLabel.CENTER);
		lblmbr2.setBounds(350, 260, 200, 40);
		lblmbr2.setHorizontalAlignment(JLabel.CENTER);
		lblmbr3.setBounds(350, 300, 200, 40);
		lblmbr3.setHorizontalAlignment(JLabel.CENTER);
		lblmbr4.setBounds(350, 340, 200, 40);
		lblmbr4.setHorizontalAlignment(JLabel.CENTER);
		lblmbr5.setBounds(350, 380, 200, 40);
		lblmbr5.setHorizontalAlignment(JLabel.CENTER);
		
		btnSubmit.setBounds(50, 430, 200, 40);
		btnAdd.setBounds(350, 430, 200, 40);
		btnSubmit.addActionListener(this);
		btnSubmit.setEnabled(false);
		btnAdd.addActionListener(this);
		
		lblPic = new JLabel(standardPic);
		lblPic.setBounds(50, 200, 200, 200);
		
		panel.add(lblPic);
		panel.add(lblmbr1);
		panel.add(lblmbr2);
		panel.add(lblmbr3);
		panel.add(lblmbr4);
		panel.add(lblmbr5);
		panel.add(lblmember);
		panel.add(lblBandName);
		panel.add(lblOrgin);
		panel.add(tfBandName);
		panel.add(tfOrgin);
		panel.add(btnSubmit);
		panel.add(btnAdd);
		
		return panel;
	}
	
	/**
	 * Method used to scale the pictures
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public ImageIcon scalePicture(ImageIcon image, int width, int height) {
		Image transImage = image.getImage();
		Image scaledImage = transImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);		
		image = new ImageIcon(scaledImage);
		return image;
	}
	
	/**
	 * Method used to get the current amount of members in the band
	 * @return
	 */
	public int getMemberCounter() {
		return memberCounter;
	}
	
	/**
	 * Method used to update the GUI with the name and instrument of the added member
	 * @param name
	 * @param instrument
	 */
	public void updateMemberList(String name, String instrument) {
		memberCounter++;
	
		switch(memberCounter) {
		
		case 1 : {
			lblmbr1.setText(name + ", " + instrument);
		}
		break;
		case 2 : {
			lblmbr2.setText(name + ", " + instrument);
		}
		break;
		case 3 : {
			lblmbr3.setText(name + ", " + instrument);
		}
		break;
		case 4 : {
			lblmbr4.setText(name + ", " + instrument);
		}
		break;
		case 5 : {
			lblmbr5.setText(name + ", " + instrument);
		}
		break;
		
		}
	}
	
	/**
	 * Method used to show the frame
	 */
	public void showStartMenu() {
		frame = new JFrame("Add Band");
		frame.setPreferredSize(new Dimension(700, 500));
		frame.pack();
		frame.add(this);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	/**
	 * Method used to check if its ok to submit the band
	 */
	public void checkSubmit() {
		if(!tfBandName.getText().trim().isEmpty()) {
			correctName = true;
		} else {
			correctName = false;
		}
		if(!tfOrgin.getText().trim().isEmpty()) {
			correctOrgin = true;
		} else {
			correctOrgin = false;
		}
		if(correctName == true && correctOrgin == true && memberCounter > 0) {
			btnSubmit.setEnabled(true);
		} else {
			btnSubmit.setEnabled(false);
		}
	}
	
	/**
	 * Method used to check the membersize and sets a cap on 5 members
	 */
	public void checkMemberSize() {
		if(memberCounter >= 5) {
			btnAdd.setEnabled(false);
		} else {
			btnAdd.setEnabled(true);
		}
	}
	
	/**
	 * Method which adds a member to a list, used to store the information
	 * @param member
	 */
	public void addMemberToList(BandMember member) {
		memberList.add(member);	
	}
	
	/**
	 * The following methods are activated when text is inserted into a textfield
	 */
	public void insertUpdate(DocumentEvent e) {
		checkSubmit();
	}

	public void removeUpdate(DocumentEvent e) {
		checkSubmit();
	}

	public void changedUpdate(DocumentEvent e) {
		checkSubmit();
	}

	/**
	 * Method which listens to the buttons in the GUI
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSubmit) {
			memberCounter = 0;
			wScreen.submitBand(tfBandName.getText(), tfOrgin.getText(), memberList);
			wScreen.setUpBandList(wScreen.getBandList());
			frame.dispose();
		}
		
		if(e.getSource()==btnAdd) {
			memberPanel = new MemberAdderPanel(this);
			btnAdd.setEnabled(false);
		}
	}
	
//	public static void main(String[] args) {
//		BandAdderPanel bap = new BandAdderPanel(new WorkerScreen());
//	}
}
