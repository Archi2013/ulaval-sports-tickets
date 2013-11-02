package ca.ulaval.glo4003.domain.dtos;

import java.util.List;

import ca.ulaval.glo4003.domain.utilities.Constants.AdmissionType;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;

public class TicketSearchPreferenceDto {
	public List<String> selectedSports;
	private DisplayedPeriod displayedPeriod;
	public boolean localGameOnly;
	public List<AdmissionType> selectedTicketTypes;
	
	public TicketSearchPreferenceDto(List<String> selectedSports,
			DisplayedPeriod displayedPeriod, boolean localGameOnly,
			List<AdmissionType> selectedTicketTypes) {
		super();
		this.selectedSports = selectedSports;
		this.displayedPeriod = displayedPeriod;
		this.localGameOnly = localGameOnly;
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

	public boolean isLocalGameOnly() {
		return localGameOnly;
	}

	public void setLocalGameOnly(boolean localGameOnly) {
		this.localGameOnly = localGameOnly;
	}

	public List<AdmissionType> getSelectedTicketTypes() {
		return selectedTicketTypes;
	}

	public void setSelectedTicketTypes(List<AdmissionType> selectedTicketTypes) {
		this.selectedTicketTypes = selectedTicketTypes;
	}
}
