package festival;

import java.sql.SQLException;
import java.util.ArrayList;
import connection.Communication;

/**
 * This class is the controller of the visitor side
 * @author JakobK98
 *
 */
public class VisitorController {
	private Communication dbManager;
	private VisitorScreen vScreen;

	/**
	 * Constructor
	 * @param comm
	 */
	public VisitorController(Communication comm) {
		dbManager = comm;
		vScreen = new VisitorScreen(this);
	}
	
	/**
	 * Method used to get the entire booked schedule
	 * @param schedule
	 * @return
	 */
	public ArrayList<SchedulePanel> getSchedule(ArrayList<SchedulePanel> schedule) {
		try {
			schedule = dbManager.getSchedule(schedule);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return schedule;
	}
	
	/**
	 * Method used to get the information of each one of the bandmembers in a band
	 * @param bandname
	 */
	public void getBandMemberInfo(String bandname) {
		ArrayList<BandMember> memberList = new ArrayList<BandMember>();
		try {
			memberList = dbManager.getBandMemberInfo(bandname);
		} catch (SQLException e) {}
		
		vScreen.addBandMemberInfo(memberList);
	}
	
	/**
	 * Method used to get the information of the band
	 * @param bandname
	 */
	public void getBandInfo(String bandname) {
		ArrayList<String> bandInfo = new ArrayList<String>();
		try {
			bandInfo = dbManager.getBandInfo(bandname);
		} catch (SQLException e) {}
		
		vScreen.addBandInfo(bandInfo);
	}
	
}
