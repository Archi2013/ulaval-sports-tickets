package ca.ulaval.glo4003.web.viewmodels;

public class TicketToSearchViewModel {
	public String sport;
	public String opponents;
	public String date;
	public String admissionType;
	public String section;
	public Integer numberOfTicket;
	public String price;
	
	public TicketToSearchViewModel(String sport, String opponents, String date,
			String admissionType, String section, Integer numberOfTicket,
			String price) {
		super();
		this.sport = sport;
		this.opponents = opponents;
		this.date = date;
		this.admissionType = admissionType;
		this.section = section;
		this.numberOfTicket = numberOfTicket;
		this.price = price;
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
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getAdmissionType() {
		return admissionType;
	}
	
	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
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
}
