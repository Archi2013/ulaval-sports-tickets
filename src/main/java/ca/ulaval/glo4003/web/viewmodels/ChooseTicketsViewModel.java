package ca.ulaval.glo4003.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class ChooseTicketsViewModel {
	public Integer numberOfTicketsToBuy;
	public List<String> selectedSeats;

	public String sectionName;
	public Long gameId;
	
	public ChooseTicketsViewModel() {
		this.selectedSeats = new ArrayList<String>();
	}
	
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
		if (selectedSeats != null) {
			this.selectedSeats = selectedSeats;
		}
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
}
