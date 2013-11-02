package ca.ulaval.glo4003.domain.dtos;

import java.util.List;

import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;

public class TicketSearchPreferenceDto {
	public List<String> selectedSports;
	private DisplayedPeriod displayedPeriod;
	public boolean localGameOnly;
	public List<TicketKind> selectedTicketKinds;
	
	public TicketSearchPreferenceDto(List<String> selectedSports,
			DisplayedPeriod displayedPeriod, boolean localGameOnly,
			List<TicketKind> selectedTicketKinds) {
		super();
		this.selectedSports = selectedSports;
		this.displayedPeriod = displayedPeriod;
		this.localGameOnly = localGameOnly;
		this.selectedTicketKinds = selectedTicketKinds;
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

	public List<TicketKind> getSelectedTicketKinds() {
		return selectedTicketKinds;
	}

	public void setSelectedTicketKinds(List<TicketKind> selectedTicketKinds) {
		this.selectedTicketKinds = selectedTicketKinds;
	}
}
