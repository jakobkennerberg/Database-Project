package festival;

import java.sql.SQLException;
import java.util.ArrayList;

import connection.Communication;

public class VisitorController {
	private Communication dbManager;

	public VisitorController(Communication comm) {
		dbManager = comm;
		new VisitorScreen(this);
	}
	
	public ArrayList<SchedulePanel> getSchedule(ArrayList<SchedulePanel> schedule) {
		try {
			schedule = dbManager.getSchedule(schedule);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return schedule;
	}
	
	public ArrayList<BandMember> getBandMemberInfo(String bandname) {
		ArrayList<BandMember> memberList = dbManager.getBandMemberInfo(bandname);
		return memberList;
		
	}
	
	public ArrayList<String> getBandInfo(String bandname) {
		ArrayList<String> bandInfo = dbManager.getBandInfo(bandname);
		return bandInfo;
				
	}
	
}
