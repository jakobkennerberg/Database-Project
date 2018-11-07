package festival;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import connection.Communication;
/**
 * Class which is used to start the application, and gives the user a option to choose to log in as a worker or a visitor
 * @author JakobK98
 *
 */
public class AppStarter extends JPanel implements ActionListener {
	private JLabel title = new JLabel("Welcome");
	private JLabel lbldescribe = new JLabel("Made for demostrative purposes only");
	private JLabel lblworker = new JLabel("Worker");
	private JLabel lblvisitor = new JLabel("Visitor");
	private JFrame frame;
	private JButton workerBtn;
	private JButton visitorBtn;
	private ImageIcon workerpic = new ImageIcon("files/serviceman.png");
	private ImageIcon visitorpic = new ImageIcon("files/tourist.png");
	private Communication dbManager;
	
	/**
	 * Constructor
	 */
	public AppStarter() {
		dbManager = new Communication();
		setLayout(null);
		workerpic = scalePicture(workerpic);
		visitorpic = scalePicture(visitorpic);
		add(menuPanel());
		add(titlePanel());
		add(bottomPanel());
		showStartMenu();
	}
	
	/**
	 * Method used to create the middle section of the GUI
	 * @return
	 */
	public JPanel menuPanel() {
		JPanel mpanel = new JPanel(null);
		mpanel.setBounds(0, 100, 700, 300);
		mpanel.setBackground(Color.PINK);
		Font lblfont = new Font("SansSerif", Font.BOLD, 30);
		
		workerBtn = new JButton(workerpic);
		visitorBtn = new JButton(visitorpic);
		workerBtn.addActionListener(this);
		visitorBtn.addActionListener(this);
		visitorBtn.setBounds(100, 50, 150, 200);
		workerBtn.setBounds(450, 50, 150, 200);
		
		lblworker.setBounds(450, 0, 150, 50);
		lblvisitor.setBounds(100, 0, 150, 50);
		lblworker.setHorizontalAlignment(JLabel.CENTER);
		lblvisitor.setHorizontalAlignment(JLabel.CENTER);
		lblworker.setFont(lblfont);
		lblvisitor.setFont(lblfont);
		mpanel.add(lblworker);
		mpanel.add(lblvisitor);
		mpanel.add(visitorBtn);
		mpanel.add(workerBtn);
		return mpanel;
	}
	
	/**
	 * Method used to create the upper section of the GUI
	 * @return
	 */
	public JPanel titlePanel() {
		JPanel tpanel = new JPanel(null);
		tpanel.setBounds(0, 0, 700, 100);
		tpanel.setBackground(Color.PINK);
		title.setFont(new Font("SansSerif", Font.BOLD, 50));
		title.setBounds(150, 0, 400, 100);
		title.setHorizontalAlignment(JLabel.CENTER);
		tpanel.add(title);
		return tpanel;
	}
	
	/**
	 * Method used to create the lower section of the GUI
	 * @return
	 */
	public JPanel bottomPanel() {
		JPanel bpanel = new JPanel(null);
		bpanel.setBounds(0, 400, 700, 100);
		bpanel.setBackground(Color.PINK);
		lbldescribe.setBounds(450, 10, 250, 50);
		lbldescribe.setForeground(Color.WHITE);
		bpanel.add(lbldescribe);
		return bpanel;
	}
	
	/**
	 * Method which listens to the buttons in the GUI
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==workerBtn) {
			frame.dispose();
			new CardController(dbManager);
		}
		if(e.getSource()==visitorBtn) {
			frame.dispose();
			new VisitorController(dbManager);
		}
	}
	
	/**
	 * Method used to scale the pictures 
	 * @param image
	 * @return
	 */
	public ImageIcon scalePicture(ImageIcon image) {
		Image transImage = image.getImage();
		Image scaledImage = transImage.getScaledInstance(140, 190, Image.SCALE_SMOOTH);		
		image = new ImageIcon(scaledImage);
		return image;
	}
	
	/**
	 * Method used to show the frame
	 */
	public void showStartMenu() {
		frame = new JFrame("Starting menu");
		frame.setPreferredSize(new Dimension(700, 500));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	/**
	 * The main method, used to start the application
	 * @param args
	 */
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AppStarter();
			}
		});	
	}
}
