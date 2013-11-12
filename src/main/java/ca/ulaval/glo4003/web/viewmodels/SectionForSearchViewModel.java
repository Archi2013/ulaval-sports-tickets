package ca.ulaval.glo4003.web.viewmodels;

public class SectionForSearchViewModel {
	public String sport;
	public String opponents;
	public String location;
	public String date;
	public String section;
	public Integer numberOfTicket;
	public String price;
	public String url;

	public SectionForSearchViewModel(String sport, String opponents, String location, String date, String section, Integer numberOfTicket,
	        String price, String url) {
		super();
		this.sport = sport;
		this.opponents = opponents;
		this.location = location;
		this.date = date;
		this.section = section;
		this.numberOfTicket = numberOfTicket;
		this.price = price;
		this.url = url;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getOpponents() {
		return opponents;
	}

	public void setOpponents(String opponents) {
		this.opponents = opponents;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Integer getNumberOfTicket() {
		return numberOfTicket;
	}

	public void setNumberOfTicket(Integer numberOfTicket) {
		this.numberOfTicket = numberOfTicket;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
