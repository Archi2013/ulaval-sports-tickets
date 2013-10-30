package ca.ulaval.glo4003.web.viewmodels;

import java.util.Map;

public class TicketSearchViewModel {
	public String [] selectedSports;
	public String displayedPeriod;
	public boolean localGame;
	public String [] selectedTicketTypes;
	
	public String[] getSelectedSports() {
		return selectedSports;
	}
	
	public void setSelectedSports(String[] selectedSports) {
		this.selectedSports = selectedSports;
	}

	public String getDisplayedPeriod() {
		return displayedPeriod;
	}

	public void setDisplayedPeriod(String displayedPeriod) {
		this.displayedPeriod = displayedPeriod;
	}

	public boolean isLocalGame() {
		return localGame;
	}

	public void setLocalGame(boolean localGame) {
		this.localGame = localGame;
	}

	public String[] getSelectedTicketTypes() {
		return selectedTicketTypes;
	}

	public void setSelectedTicketTypes(String[] selectedTicketTypes) {
		this.selectedTicketTypes = selectedTicketTypes;
	}
}
