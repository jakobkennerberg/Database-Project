package connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import festival.BandMember;
import festival.BandSpecificTime;
import festival.ConcertPanel;
import festival.SchedulePanel;

public class Communication {
	private final String url = "jdbc:postgresql://pgserver.mah.se/jmy";
	private final String userID = "ah8378";
	private final String password = "2o0hd5wd";
	
	private int bandID = 0;
	private int memberID = 0;
	private Connection conn;
	private int currentBandID = 0;
	private String currentContact = "";
	
	/**
	 * Constructor
	 */
	public Communication() {
		try {
			conn = connect();
			initiateApp();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	/**
	 * Method used to connect to the database
	 * @return
	 */
	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,userID,password);
			System.out.println("Connected to the PostgreSQL server successfully.");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
 		return conn;
	}
	
	/**
	 * Initiate the values for the IDs so the can stay unique
	 * @throws SQLException
	 */
	public void initiateApp() throws SQLException {
		String bandIDQuery = "Select max(bandid) from Band";
		String memberIDQuery = "Select max(memberid) from Member";
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(bandIDQuery);
		
		while(rs.next()) {
			bandID=rs.getInt(1);
			bandID++;
		}
		Statement st2 = conn.createStatement();
		ResultSet rs2 = st2.executeQuery(memberIDQuery);
		
		while(rs2.next()) {
			memberID=rs2.getInt(1);
			memberID++;
		}
		
		System.out.println("BandID: " + bandID);
		System.out.println("MemberID: " + memberID);
	}	
	
	
	/**
	 * All info of workers
	 * @return ArrayList of workers (all info)
	 */
	public ArrayList<String> getWorkers()throws SQLException { //DO we need????
		String query = "Select * From Worker";
		ArrayList<String> workers = new ArrayList<String>();
		
		//try(Connection conn = connect()) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while (rs.next()) {
				workers.add(rs.getInt("workerid" ) + ", " + 
					rs.getString("name") + ", " + 
					rs.getInt("personnumber") + ", " + 
					rs.getString("address"));
			}
		}catch (SQLException e) {}
			
		return workers;
	}
	
	public ArrayList<SchedulePanel> getSchedule(ArrayList<SchedulePanel> schedule) throws SQLException {
		String scheduleQuery = "Select scene.scenename, band.bandname, cs.day, cs.starttime, cs.endtime from ConcertSchedule AS cs join band on band.bandid = cs.bandid join scene on scene.scenenumber=cs.sceneid order by cs.day ASC, cs.starttime ASC";
		PreparedStatement pst = conn.prepareStatement(scheduleQuery);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			schedule.add(new SchedulePanel(rs.getString("scenename"), rs.getString("bandname"), rs.getInt("day"), rs.getString("starttime"), rs.getString("endtime")));
		}
		
		return schedule;
	}
	
	public ArrayList<String> getBookedTimes(int day, int stage) throws SQLException {
		String bookedQuery = "select starttime, endtime from concertSchedule where sceneid="+stage+" AND day="+day+" order by starttime ASC, endtime ASC";
		ArrayList<String> bookedTimes = new ArrayList<String>();
		
		PreparedStatement pst = conn.prepareStatement(bookedQuery);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			String bookedConcert = rs.getString("starttime") + " - " + rs.getString("endtime"); //lägg ihop start o endtime till ex 15.00 - 17.00
			bookedTimes.add(bookedConcert);
		}
		
		return bookedTimes;
		
		
	}
	
	public void insertConcert(int bandid, int dayid, int stageid, String starttime, String endtime) throws SQLException {
		String insertConcert = "insert into ConcertSchedule(sceneid, bandid, day, starttime, endtime) values ("+stageid+", "+bandid+", "+dayid+", '"+starttime+"', '"+endtime+"')"; 
		
		PreparedStatement pst = conn.prepareStatement(insertConcert);
		pst.executeUpdate();
	}
	
	
	public ArrayList<BandSpecificTime> getBandSpecificTimes(String bandname) throws SQLException  {
		String bandTimesQuery = "select cs.day, cs.starttime, cs.endtime from ConcertSchedule AS cs join band on cs.bandID=band.bandid where bandname='"+bandname+"'";
		ArrayList<BandSpecificTime> list = new ArrayList<BandSpecificTime>();
		
		PreparedStatement pst = conn.prepareStatement(bandTimesQuery);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			list.add(new BandSpecificTime(rs.getInt("day"), rs.getString("starttime"), rs.getString("endtime")));		
		}
		return list;
	}
	
	/**
	 * Inserts the created band into the db
	 * @param bandname
	 * @param origin
	 * @param members
	 * @throws SQLException
	 */
	public void insertBand(String bandname, String origin, ArrayList<BandMember> members) throws SQLException {
		String insertQueryB = "insert into Band(bandid, bandname, origin) values ('" + bandID +"', '"+ bandname + "', '" + origin + "')";
		PreparedStatement pst = conn.prepareStatement(insertQueryB);
		pst.executeUpdate();
		currentBandID = bandID;
		bandID++;
	}
	
	
	/**
	 * Inserts the members in the created band into the db
	 * @param name
	 * @param origin
	 * @param instrument
	 * @param xtrainfo
	 * @throws SQLException
	 */
	public void insertMember(String name, String origin, String instrument, String xtrainfo) throws SQLException {
		String insertMember = "insert into Member(memberid, name, origin, instrument, extrainfo) values ('" + memberID + "', '" + name + "', '" + origin + "', '" + instrument + "', '" + xtrainfo + "')";
		String insertMO = "insert into MemberOf(bandid, memberid) values ('" + currentBandID + "', '" + memberID + "')";
		PreparedStatement pst = conn.prepareStatement(insertMember);
		pst.executeUpdate();

		PreparedStatement pst2 = conn.prepareStatement(insertMO);
		pst2.executeUpdate();
		
		memberID++;	
	}
	
	/**
	 * Method used only when the musican already exists in the database(inserts only in memberof)
	 * @param sameID
	 * @throws SQLException
	 */
	public void insertWhatBand(int sameID) throws SQLException {
		String insertMO = "insert into MemberOf(bandid, memberid) values ('" + currentBandID + "', '" + sameID + "')";
		PreparedStatement pst2 = conn.prepareStatement(insertMO);
		pst2.executeUpdate();
	}
	
	/**
	 * Method used to control if the current artist already exists in the database
	 * @param name
	 * @param country
	 * @param instrument
	 * @param xtraInfo
	 * @throws SQLException
	 */
	public void memberControl(String name, String country, String instrument, String xtraInfo) throws SQLException {
		String sameQuery = "Select memberof.memberID, member.name, band.bandname from Memberof join member on memberof.memberID = member.memberID join band on memberof.bandID=band.bandID where member.name = '"+name+"'";
		boolean sameMusican = false;
		
		PreparedStatement pst = conn.prepareStatement(sameQuery);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			int id = rs.getInt(1);
			String duplicateName = rs.getString(2);
			String band = rs.getString(3);
			sameMusican = memberChooser(duplicateName, band);
			
			if(sameMusican == true) {
				insertWhatBand(id);
				break;
			}
		}
		
		if(sameMusican == false) {
			insertMember(name, country, instrument, xtraInfo);
		}
	}
	
	/**
	 * Method used to provide the user with the opportunity to choose if it is the same artist or a different with the same name
	 * @param name
	 * @param band
	 * @return
	 */
	public boolean memberChooser(String name, String band) {
		boolean sameMusican = false;
		String[] options = {"Same musician", "Another musician"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("<html> There is already a " + name + " playing in <br>band name: "+ band + "</html>");
		panel.add(lbl);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Knas",
				JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
		
		if (selectedOption == JOptionPane.YES_OPTION) {	
			sameMusican = true;
		}
		return sameMusican;
	}
	
	/**
	 * Method used to collect the info about the bandmembers when asked for (Visitor side)
	 * @param bandName
	 * @return
	 */
	public ArrayList<BandMember> getBandMemberInfo(String bandName) throws SQLException {
		String memberInfoQuery = "select member.name, member.origin, member.instrument, member.extrainfo from band join memberof on band.bandid=memberof.bandid join member on memberof.memberid=member.memberid where band.bandname='"+bandName+"'";
		ArrayList<BandMember> list = new ArrayList<BandMember>();
		
		PreparedStatement pst = conn.prepareStatement(memberInfoQuery);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			list.add(new BandMember(rs.getString("name"), rs.getString("origin"), rs.getString("instrument"), rs.getString("extrainfo")));
		}
		
		return list;
	}
	
	
	/**
	 * Method used to collect the info about the band when asked for (Visitor side)
	 * @param bandname
	 * @return
	 */
	public ArrayList<String> getBandInfo(String bandname) throws SQLException  {
		String bandInfoQuery = "select bandname, origin from band where bandname='"+bandname+"'";
		ArrayList<String> info = new ArrayList<String>();
		
		PreparedStatement pst = conn.prepareStatement(bandInfoQuery);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			info.add(rs.getString("bandname"));
			info.add(rs.getString("origin"));
		}
		
		return info;
	}
	
	
	
	/**
	 * Method used to insert an assigned contactperson and the selected unassigned band in the db
	 * @param band
	 * @param worker
	 * @throws SQLException
	 */
	public void insertContact(String band, String worker) throws SQLException {
		String queryBandID = "select bandid from band where bandname = '"+band+"'";
		String queryWorkerID = "select workerid from worker where name='"+worker+"'"; //person nummer??
		int insertBandID = 0;
		int insertWorkerID = 0;
		
		PreparedStatement pst = conn.prepareStatement(queryBandID);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			insertBandID = rs.getInt("bandid");
		}
		
		PreparedStatement pst2 = conn.prepareStatement(queryWorkerID);
		ResultSet rs2 = pst2.executeQuery();
		while(rs2.next()) {
			insertWorkerID = rs2.getInt("workerid");
		}
		
		String insertContact = "insert into contactperson(workerid, bandid) values ('"+insertWorkerID+"', '"+insertBandID+"')";
		currentContact = worker;
				
		PreparedStatement pst3 = conn.prepareStatement(insertContact);
		pst3.executeUpdate();
	}
	
	
	/**
	 * Method used to check if a band has a contactperson assigned to them
	 * @param bandname
	 * @return
	 * @throws SQLException
	 */
	public boolean checkContact(String bandname)throws SQLException {
		String checkQuery = "Select worker.workerid, worker.name from worker join contactperson on worker.workerid=contactperson.workerid join band on contactperson.bandid=band.bandid where band.bandname='"+bandname+"'";
		//Do we need worker.workerid??
		boolean assigned = false;
		
		PreparedStatement pst = conn.prepareStatement(checkQuery);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			currentContact = rs.getString("name");
			assigned = true;
		}
		return assigned;
	}
	
	/**
	 * Method used to get the latest checked/assigned contactpersons name
	 * @return
	 */
	public String getContactName() {
		return this.currentContact;
	}
	
	
	/**
	 * All the names of the workers
	 * @return
	 */
	public ArrayList<String> getWorkerNameList() {
		String query = "Select * From Worker"; //elr name
		ArrayList<String> workerName = new ArrayList<String>();
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while (rs.next()) {
				workerName.add(rs.getString("name"));
			}
		}catch (SQLException e) {}
		return workerName;
	}
	
	public ArrayList<String> getBandNameList() {
		String query = "Select * From Band"; //elr name
		ArrayList<String> bandNames = new ArrayList<String>();
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while (rs.next()) {
				bandNames.add(rs.getString("bandname"));
			}
		}catch (SQLException e) {}
			
		return bandNames;
	}
	
	public int getBandID(String bandname) throws SQLException {
		String idQuery = "Select bandid from band where bandname='" +bandname+"'";
		int bandID = 0;
		
		PreparedStatement pst = conn.prepareStatement(idQuery);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			bandID = rs.getInt("bandid");
		}
		
		return bandID;
	}
	
	/**
	 * The numbers of either workers or bands, depending on the parameters
	 * @return integer with Workers
	 */
	public int getCount(String type) {
		int count = 0;
		String query = "";
		String queryWorker = "Select Count(*) From Worker";
		String queryBand = "Select Count(*) from Band";
		if(type == "Band") {
			query = queryBand;
		} else if (type == "Worker") {
			query = queryWorker;
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while(rs.next()) {
				count=rs.getInt(1);
			}
		}catch (SQLException e) {}
	
		return count;
	}
	
	
	public static void main (String[] args) {
		Communication comm = new Communication();
		
//		ArrayList<String> list = comm.getWorkerNameList();
//		
//		for(String str : list) {
//			System.out.println(str);
//		}
		
//		ArrayList<String> getWorkerName = comm.getWorkerName();
//		for (String workerName : getWorkerName) {
//			System.out.println(workerName);
//		}
		
		//System.out.println(comm.getCount("Band"));
		
	}

}

