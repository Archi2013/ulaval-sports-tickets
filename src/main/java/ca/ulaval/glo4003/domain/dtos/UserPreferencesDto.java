package ca.ulaval.glo4003.domain.dtos;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;

public class UserPreferencesDto {

	public String username;
	public List<String> selectedSports;
	private DisplayedPeriod displayedPeriod;
	public boolean localGameOnly;
	public List<String> selectedTicketKinds;
	
	public UserPreferencesDto(String username,List<String> selectedSports,
			DisplayedPeriod displayedPeriod, boolean localGameOnly,
			List<String> selectedTicketKinds) {
		super();
		if (selectedSports == null) {
			selectedSports = new ArrayList<String>();
		}
		this.username=username;
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