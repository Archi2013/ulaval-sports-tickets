package ca.ulaval.glo4003.presentation.viewmodels;


public class SectionForCartViewModel {
	public Boolean generalAdmission;
	
	public Integer numberOfTicketsToBuy;
	public String selectedSeats;
	
	public String sportName;
	public String gameDate;
	public String sectionName;
	
	public String opponents;
	public String location;
	public String subtotal;
	
	public Boolean getGeneralAdmission() {
		return generalAdmission;
	}
	
	public void setGeneralAdmission(Boolean generalAdmission) {
		this.generalAdmission = generalAdmission;
	}
	
	public Integer getNumberOfTicketsToBuy() {
		return numberOfTicketsToBuy;
	}
	
	public void setNumberOfTicketsToBuy(Integer numberOfTicketsToBuy) {
		this.numberOfTicketsToBuy = numberOfTicketsToBuy;
	}
	
	public String getSelectedSeats() {
		return selectedSeats;
	}
	
	public void setSelectedSeats(String selectedSeats) {
		this.selectedSeats = selectedSeats;
	}
	
	public String getSportName() {
		return sportName;
	}
	
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	
	public String getGameDate() {
		return gameDate;
	}
	
	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}
	
	public String getSectionName() {
		return sectionName;
	}
	
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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
	
	public String getSubtotal() {
		return subtotal;
	}
	
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
}
