package ca.ulaval.glo4003.presentation.viewmodels;

import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;

public class SectionForPaymentViewModel {
	public Integer numberOfTicketsToBuy;
	public String selectedSeats;
	
	public TicketKind ticketKind;
	public String sectionName;
	public String date;
	public String opponents;
	public String sport;
	public String subtotal;
	
	public Boolean isGeneralAdmission() {
		if (ticketKind.equals(TicketKind.GENERAL_ADMISSION)) {
			return true;
		} else {
			return false;
		}
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
	
	public TicketKind getTicketKind() {
		return ticketKind;
	}
	
	public void setTicketKind(TicketKind ticketKind) {
		this.ticketKind = ticketKind;
	}
	
	public String getSectionName() {
		return sectionName;
	}
	
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getOpponents() {
		return opponents;
	}
	
	public void setOpponents(String opponents) {
		this.opponents = opponents;
	}
	
	public String getSport() {
		return sport;
	}
	
	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
}
