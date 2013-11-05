package ca.ulaval.glo4003.web.viewmodels;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;

public class SectionForPaymentViewModel {
	public Integer numberOfTicketsToBuy;
	public List<String> selectedSeats;
	
	public TicketKind ticketKind;
	public String admissionType;
	public String sectionName;
	public DateTime date;
	public String opponents;
	public String sport;
	
	public Integer getNumberOfTicketsToBuy() {
		return numberOfTicketsToBuy;
	}
	
	public void setNumberOfTicketsToBuy(Integer numberOfTicketsToBuy) {
		this.numberOfTicketsToBuy = numberOfTicketsToBuy;
	}
	
	public List<String> getSelectedSeats() {
		return selectedSeats;
	}
	
	public void setSelectedSeats(List<String> selectedSeats) {
		this.selectedSeats = selectedSeats;
	}
	
	public TicketKind getTicketKind() {
		return ticketKind;
	}
	
	public void setTicketKind(TicketKind ticketKind) {
		this.ticketKind = ticketKind;
	}
	
	public String getAdmissionType() {
		return admissionType;
	}
	
	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
	}
	
	public String getSectionName() {
		return sectionName;
	}
	
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	public DateTime getDate() {
		return date;
	}
	
	public void setDate(DateTime date) {
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
}
