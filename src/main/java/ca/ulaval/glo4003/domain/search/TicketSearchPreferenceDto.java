package ca.ulaval.glo4003.domain.search;

import java.util.ArrayList;
import java.util.List;

public class TicketSearchPreferenceDto {
	public List<String> selectedSports;
	private String displayedPeriod;
	public Boolean localGameOnly;
	public List<String> selectedTicketKinds;

	public TicketSearchPreferenceDto(List<String> selectedSports,
			String displayedPeriod, Boolean localGameOnly,
			List<String> selectedTicketKinds) {
		super();
		if (selectedSports == null) {
			selectedSports = new ArrayList<String>();
		}
		this.selectedSports = selectedSports;
		this.displayedPeriod = displayedPeriod;
		this.localGameOnly = localGameOnly;
		if (selectedTicketKinds == null) {
			selectedTicketKinds = new ArrayList<String>();
		}
		this.selectedTicketKinds = selectedTicketKinds;
	}

	public List<String> getSelectedSports() {
		return selectedSports;
	}

	public void setSelectedSports(List<String> selectedSports) {
		if (selectedSports == null) {
			selectedSports = new ArrayList<String>();
		}
		this.selectedSports = selectedSports;
	}

	public String getDisplayedPeriod() {
		return displayedPeriod;
	}

	public void setDisplayedPeriod(String displayedPeriod) {
		this.displayedPeriod = displayedPeriod;
	}

	public Boolean isLocalGameOnly() {
		return localGameOnly;
	}

	public void setLocalGameOnly(boolean localGameOnly) {
		this.localGameOnly = localGameOnly;
	}

	public List<String> getSelectedTicketKinds() {
		return selectedTicketKinds;
	}

	public void setSelectedTicketKinds(List<String> selectedTicketKinds) {
		if (selectedTicketKinds == null) {
			selectedTicketKinds = new ArrayList<String>();
		}
		this.selectedTicketKinds = selectedTicketKinds;
	}
}
