package festival;

import java.sql.SQLException;
import java.util.ArrayList;

import connection.Communication;

public class VisitorController {
	private Communication dbManager;
	private VisitorScreen vScreen;

	public VisitorController(Communication comm) {
		dbManager = comm;
		vScreen = new VisitorScreen(this);
	}
	
	public ArrayList<SchedulePanel> getSchedule(ArrayList<SchedulePanel> schedule) {
		try {
			schedule = dbManager.getSchedule(schedule);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return schedule;
	}
	
	public void getBandMemberInfo(String bandname) {
		ArrayList<BandMember> memberList = new ArrayList<BandMember>();
		try {
			memberList = dbManager.getBandMemberInfo(bandname);
		} catch (SQLException e) {}
		
		vScreen.addBandMemberInfo(memberList);
	}
	
	public void getBandInfo(String bandname) {
		ArrayList<String> bandInfo = new ArrayList<String>();
		try {
			bandInfo = dbManager.getBandInfo(bandname);
		} catch (SQLException e) {}
		
		vScreen.addBandInfo(bandInfo);
	}
	
}
