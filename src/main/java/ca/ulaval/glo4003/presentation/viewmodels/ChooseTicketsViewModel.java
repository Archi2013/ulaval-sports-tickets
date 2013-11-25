package ca.ulaval.glo4003.presentation.viewmodels;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

public class ChooseTicketsViewModel {
	
	public Integer numberOfTicketsToBuy;
	public List<String> selectedSeats;
	
	@NotNull @NotEmpty
	public String sportName;
	
	@NotNull
	public DateTime gameDate;

	@NotNull @NotEmpty
	public String sectionName;
	
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

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public DateTime getGameDate() {
		return gameDate;
	}

	public void setGameDate(DateTime gameDate) {
		this.gameDate = gameDate;
	}
}
