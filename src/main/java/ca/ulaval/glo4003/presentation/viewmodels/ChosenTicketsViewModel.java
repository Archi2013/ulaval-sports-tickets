package ca.ulaval.glo4003.presentation.viewmodels;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

public class ChosenTicketsViewModel {
	
	@NotNull @NotEmpty
	public String sportName;
	
	@NotNull
	public DateTime gameDate;

	@NotNull @NotEmpty
	public String sectionName;

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public DateTime getGameDate() {
		return gameDate;
	}

	public void setGameDate(DateTime gameDate) {
		this.gameDate = gameDate;
	}
}
