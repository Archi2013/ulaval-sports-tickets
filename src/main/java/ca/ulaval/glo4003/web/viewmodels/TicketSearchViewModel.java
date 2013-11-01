package ca.ulaval.glo4003.web.viewmodels;

import java.util.List;

import ca.ulaval.glo4003.domain.utilities.Constants.AdmissionType;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;

public class TicketSearchViewModel {
	public List<String> selectedSports;
	private DisplayedPeriod displayedPeriod;
	public boolean localGame;
	public List<AdmissionType> selectedTicketTypes;
	
	public List<String> getSelectedSports() {
		return selectedSports;
	}
	
	public void setSelectedSports(List<String> selectedSports) {
		this.selectedSports = selectedSports;
	}
	
	public DisplayedPeriod getDisplayedPeriod() {
		return displayedPeriod;
	}
	
	public void setDisplayedPeriod(DisplayedPeriod displayedPeriod2) {
		this.displayedPeriod = displayedPeriod2;
	}

	public boolean isLocalGame() {
		return localGame;
	}

	public void setLocalGame(boolean localGame) {
		this.localGame = localGame;
	}

	public List<AdmissionType> getSelectedTicketTypes() {
		return selectedTicketTypes;
	}

	public void setSelectedTicketTypes(List<AdmissionType> admissionTypes) {
		this.selectedTicketTypes = admissionTypes;
	}
}
