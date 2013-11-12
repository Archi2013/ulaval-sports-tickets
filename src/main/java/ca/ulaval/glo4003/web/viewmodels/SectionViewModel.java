package ca.ulaval.glo4003.web.viewmodels;

import java.util.List;

public class SectionViewModel {

	public String sectionName;
	public int numberOfTickets;
	public String price;
	private String date;
	private String opponents;
	private String location;
	public String url;
	public List<String> seats;

	public SectionViewModel(String sectionName, int numberOfTickets, String price, String date,
			String opponents, String location, String url, List<String> seats) {
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.date = date;
		this.opponents = opponents;
		this.location = location;
		this.url = url;
		this.seats = seats;
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

	public List<String> getSeats() {
		return seats;
	}
	
	public boolean isGeneralAdmission() {
		if (seats.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
