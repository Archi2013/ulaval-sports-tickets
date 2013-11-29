package ca.ulaval.glo4003.presentation.viewmodels;

import java.util.Set;

public class SectionViewModel {

	public String sectionName;
	public int numberOfTickets;
	public String price;
	private String date;
	private String opponents;
	private String location;
	public String url;
	public Set<String> seats;
	private Boolean generalAdmission;

	public SectionViewModel(String sectionName, int numberOfTickets, String price, String date, String opponents,
			String location, String url, Set<String> seats, Boolean generalAdmission) {
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.date = date;
		this.opponents = opponents;
		this.location = location;
		this.url = url;
		this.seats = seats;
		this.generalAdmission = generalAdmission;
	}

	public Boolean getGeneralAdmission() {
		return generalAdmission;
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

	public String getLocation() {
		return location;
	}

	public String getDate() {
		return date;
	}

	public String getUrl() {
		return url;
	}

	public Set<String> getSeats() {
		return seats;
	}
}
