package ca.ulaval.glo4003.web.viewmodels;

public class SectionViewModel {

	public String admissionType;
	public String sectionName;
	public int numberOfTickets;
	public String price;
	private String date;
	private String opponents;
	public String url;

	public SectionViewModel(String admissionType, String sectionName, int numberOfTickets, String price, String date,
			String opponents, String url) {
		this.admissionType = admissionType;
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.date = date;
		this.opponents = opponents;
		this.url = url;
	}

	public String getAdmissionType() {
		return admissionType;
	}

	public String getSectionName() {
		return sectionName;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public String getPrice() {
		return price;
	}

	public String getOpponents() {
		return opponents;
	}

	public String getDate() {
		return date;
	}

	public String getUrl() {
		return url;
	}
}
