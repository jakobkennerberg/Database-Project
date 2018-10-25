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
	public ArrayList<String> getWorkerName() {
		String query = "Select * From Worker";
		ArrayList<String> workerName = new ArrayList<String>();
		
		try(Connection conn = connect()) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				workerName.add(rs.getString("name"));
			}
			
		} catch (SQLException e) {

		}
		return workerName;
	}
	
	/**
	 * The numbers of Workers
	 * @return integer with Workers
	 */
	
	public int getWorkerNumbers() {
		int count = 0;
		String quary = "Select Count(*) From Worker";
		try (Connection conn = connect()) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(quary);
			
			while(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			
		}
		return count;
	}
	
	
	public static void main (String[] args) {
		Communication comm = new Communication();
		
//		ArrayList<String> getWorkerName = comm.getWorkerName();
//		for (String workerName : getWorkerName) {
//			System.out.println(workerName);
//		}
		System.out.println(comm.getWorkerNumbers());
		
	}

}

