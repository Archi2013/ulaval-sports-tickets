package ca.ulaval.glo4003.presentation.viewmodels;

import java.util.List;

import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;

public class TicketSearchViewModel {
	public List<String> selectedSports;
	private DisplayedPeriod displayedPeriod;
	public boolean localGameOnly;
	public List<TicketKind> selectedTicketKinds;
	
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

	public boolean isLocalGameOnly() {
		return localGameOnly;
	}

	public void setLocalGameOnly(boolean localGameOnly) {
		this.localGameOnly = localGameOnly;
	}

	public List<TicketKind> getSelectedTicketKinds() {
		return selectedTicketKinds;
	}

	public void setSelectedTicketKinds(List<TicketKind> admissionTypes) {
		this.selectedTicketKinds = admissionTypes;
	}
}
