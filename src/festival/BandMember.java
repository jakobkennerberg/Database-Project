package festival;

public class BandMember {
	private String name;
	private String country;
	private String instrument;
	private String xtraInfo;
	
	public BandMember(String name, String country, String instrument) {
		this.name = name;
		this.country = country;
		this.instrument = instrument;
	}
	
	public BandMember(String name, String country, String instrument, String xtraInfo) {
		this.name = name;
		this.country = country;
		this.instrument = instrument;
		this.xtraInfo = xtraInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public String getXtraInfo() {
		return xtraInfo;
	}

	public void setXtraInfo(String xtraInfo) {
		this.xtraInfo = xtraInfo;
	}

}
