package ca.ulaval.glo4003.web.viewmodels;

import java.util.List;

import ca.ulaval.glo4003.domain.utilities.Constants.AdmissionType;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;

public class TicketSearchViewModel {
	public List<String> selectedSports;
	private DisplayedPeriod displayedPeriod;
	public boolean localGameOnly;
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

	public boolean isLocalGameOnly() {
		return localGameOnly;
	}

	public void setLocalGameOnly(boolean localGameOnly) {
		this.localGameOnly = localGameOnly;
	}

	public List<AdmissionType> getSelectedTicketTypes() {
		return selectedTicketTypes;
	}

	public void setSelectedTicketTypes(List<AdmissionType> admissionTypes) {
		this.selectedTicketTypes = admissionTypes;
	}
}
