package festival;

/**
 * Method used to store information about the booked times of a band
 * @author JakobK98
 *
 */
public class BandSpecificTime {
	private int dayID;
	private String starttime;
	private String endtime;

	public BandSpecificTime(int dayID, String starttime, String endtime) {
		this.dayID = dayID;
		this.starttime = starttime;
		this.endtime = endtime;
	}

	public int getDayID() {
		return dayID;
	}

	public void setDayID(int dayID) {
		this.dayID = dayID;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
}
