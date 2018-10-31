package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import festival.BandMember;

public class Communication {
	private final String url = "jdbc:postgresql://pgserver.mah.se/jmy";
	private final String userID = "ah8378";
	private final String password = "2o0hd5wd";
	
	private int bandID = 0;
	private int memberID = 0;
	private Connection conn;
	private int currentBandID = 0;
	
	public Communication() {
		try {
			conn = connect();
			initiateApp();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	
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

