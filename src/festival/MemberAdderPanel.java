package festival;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MemberAdderPanel extends JPanel implements ActionListener, DocumentListener {
	private BandAdderPanel bandAdder;
	private JFrame frame;
	private ImageIcon memberPic = new ImageIcon("files/singer.png");
	private JButton btnDone = new JButton("Done");
	private JButton btnAddMember = new JButton("Add Member");
	private JTextField tfName = new JTextField();
	private final JTextField tfXtra = new JTextField(20);
	private JTextField tfOrgin = new JTextField();
	private JLabel lblPic;
	private JLabel lblName = new JLabel("Name: ");
	private JLabel lblOrgin = new JLabel("Country: ");
	private JLabel lblXtra = new JLabel("Extra information:");
	private JLabel lblInstruments = new JLabel("Select Instrument");
	private boolean correctName = false;
	private boolean correctOrgin = false;
	private final JComboBox<String> instrumentBox = new JComboBox<String>();
	
	
	public MemberAdderPanel(BandAdderPanel panel) {
		bandAdder = panel;
		setLayout(null);
		memberPic = bandAdder.scalePicture(memberPic, 125, 175);
		setBackground(Color.PINK);
		add(infoPanel());
		showStartMenu();
	}
	
	public JPanel infoPanel() {
		JPanel apanel = new JPanel();
		apanel.setLayout(null);
		apanel.setBounds(0, 0, 700, 500);
		apanel.setBackground(Color.PINK);
		Font lblFont = new Font("SansSerif", Font.BOLD, 25);
		
		tfName.setBounds(300, 50, 300, 50);
		tfOrgin.setBounds(300, 125, 300, 50);
		tfName.getDocument().addDocumentListener(this);
		tfOrgin.getDocument().addDocumentListener(this);
		lblName.setBounds(175, 50, 250, 50);
		lblName.setFont(lblFont);
		lblOrgin.setFont(lblFont);
		lblOrgin.setBounds(175, 125, 250, 50);
		lblXtra.setBounds(300, 220, 300, 50);
		lblXtra.setHorizontalAlignment(JLabel.CENTER);
		lblXtra.setFont(lblFont);
		tfXtra.setBounds(300, 270, 300, 200);
		
		lblPic = new JLabel(memberPic);
		lblPic.setBounds(10, 25, 150, 175);
		
		lblInstruments.setBounds(20, 225, 225, 50);
		lblInstruments.setHorizontalAlignment(JLabel.CENTER);
		lblInstruments.setFont(lblFont);
		
		instrumentBox.setBounds(30, 275, 185, 40);
		instrumentBox.addActionListener(this); //???
	    instrumentBox.addItem("Singer");
	    instrumentBox.addItem("Guitar");
	    instrumentBox.addItem("Keyboard");
	    instrumentBox.addItem("Drums");
	    instrumentBox.addItem("Bass");
	    instrumentBox.addItem("Trumpet");
	    
	    btnAddMember.setBounds(30, 350, 180, 50);
	    btnDone.setBounds(30, 420, 180, 50);
	    btnDone.setEnabled(false);
	    btnAddMember.setEnabled(false);
	    btnDone.addActionListener(this);
	    btnAddMember.addActionListener(this);
		
	    apanel.add(lblInstruments);
	    apanel.add(btnAddMember);
	    apanel.add(btnDone);
	    apanel.add(lblPic);
	    apanel.add(tfXtra);
	    apanel.add(lblXtra);
		apanel.add(instrumentBox);
		apanel.add(lblOrgin);
		apanel.add(lblName);
		apanel.add(tfName);
		apanel.add(tfOrgin);
		
		return apanel;
	}
	
	public void showStartMenu() {
		frame = new JFrame("Add Band Member");
		frame.setPreferredSize(new Dimension(700, 500));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);
	}
	
	public void checkMember() {
		if(!tfName.getText().trim().isEmpty()) {
			correctName = true;
		} else {
			correctName = false;
		}
		if(!tfOrgin.getText().trim().isEmpty()) {
			correctOrgin = true;
		} else {
			correctOrgin = false;
		}
		if(correctName == true && correctOrgin == true && !(bandAdder.getMemberCounter() >= 5)) {
			btnAddMember.setEnabled(true);
		} else {
			btnAddMember.setEnabled(false);
		}
	}
	
	public void insertUpdate(DocumentEvent e) {
		checkMember();
	}


	public void removeUpdate(DocumentEvent e) {
		checkMember();
	}

	
	public void changedUpdate(DocumentEvent e) {
		checkMember();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAddMember) {
			String instrument = (String)instrumentBox.getSelectedItem();
			bandAdder.updateMemberList(tfName.getText(), instrument);
			tfName.setText("");
			tfOrgin.setText("");
			btnDone.setEnabled(true);
		}
		
		if(e.getSource()==btnDone) {
			bandAdder.checkSubmit();
			frame.dispose();
		}
	}
	
	public static void main(String[] args) {
		MemberAdderPanel panel = new MemberAdderPanel(new BandAdderPanel(new WorkerScreen()));
	}
}
