package ca.ulaval.glo4003.domain.dtos;

import java.util.List;

import ca.ulaval.glo4003.domain.utilities.Constants.AdmissionType;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;

public class TicketSearchPreferenceDto {
	public List<String> selectedSports;
	private DisplayedPeriod displayedPeriod;
	public boolean localGame;
	public List<AdmissionType> selectedTicketTypes;
	
	public TicketSearchPreferenceDto(List<String> selectedSports,
			DisplayedPeriod displayedPeriod, boolean localGame,
			List<AdmissionType> selectedTicketTypes) {
		super();
		this.selectedSports = selectedSports;
		this.displayedPeriod = displayedPeriod;
		this.localGame = localGame;
		this.selectedTicketTypes = selectedTicketTypes;
	}

	public List<String> getSelectedSports() {
		return selectedSports;
	}

	public void setSelectedSports(List<String> selectedSports) {
		this.selectedSports = selectedSports;
	}

	public DisplayedPeriod getDisplayedPeriod() {
		return displayedPeriod;
	}

	public void setDisplayedPeriod(DisplayedPeriod displayedPeriod) {
		this.displayedPeriod = displayedPeriod;
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

	public void setSelectedTicketTypes(List<AdmissionType> selectedTicketTypes) {
		this.selectedTicketTypes = selectedTicketTypes;
	}
}
