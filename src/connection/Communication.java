package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Communication {
	private final String url = "jdbc:postgresql://pgserver.mah.se/jmy";
	private final String userID = "ah8378";
	private final String password = "2o0hd5wd";
	
	private int bandID = 0;
	
	public Communication() {
		//bandID = read current bandId 
	}
	
	public Connection connect() {
		//testttt
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
	 * All info of workers
	 * @return ArrayList of workers (all info)
	 */
	public ArrayList<String> getWorkers() {
		String query = "Select * From Worker";
		ArrayList<String> workers = new ArrayList<String>();
		
		try(Connection conn = connect()) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				workers.add(rs.getInt("workerid" ) + ", " + 
						rs.getString("name") + ", " + 
						rs.getInt("personnumber") + ", " + 
						rs.getString("address"));
			}
			
		} catch (SQLException e) {

		}
		
		return workers;
	}
	
	/**
	 * All the names of the workers
	 * @return
	 */
	public ArrayList<String> getWorkerNameList() {
		String query = "Select * From Worker"; //elr name
		ArrayList<String> workerName = new ArrayList<String>();
		
		try(Connection conn = connect()) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				workerName.add(rs.getString("name"));
			}
			
		} catch (SQLException e) {}
		return workerName;
	}
	
	public ArrayList<String> getBandNameList() {
		String query = "Select * From Band"; //elr name
		ArrayList<String> bandNames = new ArrayList<String>();
		
		try(Connection conn = connect()) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				bandNames.add(rs.getString("name"));
			}
			
		} catch (SQLException e) {}
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
		
		try (Connection conn = connect()) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (SQLException e) {}
		
		return count;
	}
	
	
	public static void main (String[] args) {
		Communication comm = new Communication();
		ArrayList<String> list = comm.getWorkerNameList();
		
		for(String str : list) {
			System.out.println(str);
		}
		
//		ArrayList<String> getWorkerName = comm.getWorkerName();
//		for (String workerName : getWorkerName) {
//			System.out.println(workerName);
//		}
		
		//System.out.println(comm.getCount("Band"));
		
	}

}

