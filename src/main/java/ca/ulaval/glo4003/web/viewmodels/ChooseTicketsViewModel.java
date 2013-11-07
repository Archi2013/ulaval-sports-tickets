package ca.ulaval.glo4003.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ChooseTicketsViewModel {
	
	public Integer numberOfTicketsToBuy;
	public List<String> selectedSeats;

	@NotNull @NotEmpty
	public String sectionName;
	
	@NotNull @Min(1)
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
