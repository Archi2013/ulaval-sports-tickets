package ca.ulaval.glo4003.domain.utilities.user;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;

@Component
public class UserPreferences {
	
	@Inject
	Constants constants;
	
	private String username;
	public List<String> selectedSports;
	private DisplayedPeriod displayedPeriod;
	public boolean localGameOnly;
	public List<TicketKind> selectedTicketKinds;
	
	public UserPreferences(){
		this.username = "TEST";
	}

	public String getTest() {
		return username;
	}

	public void setTest(String username) {
		this.username = username;
	}
	
	public void initializeUserPreferences(){
		
		this.selectedSports=constants.getSportList();
		this.displayedPeriod=DisplayedPeriod.ONE_DAY;
		this.localGameOnly=true;
		this.selectedTicketKinds = constants.getTicketKinds();
		
	}
	
	

}
