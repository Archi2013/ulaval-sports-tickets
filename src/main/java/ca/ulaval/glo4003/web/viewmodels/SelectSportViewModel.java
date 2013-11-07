package ca.ulaval.glo4003.web.viewmodels;

import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;

public class SelectSportViewModel {
	String sport;
	TicketKind typeBillet;

	public TicketKind getTypeBillet() {
		return typeBillet;
	}

	public void setTypeBillet(TicketKind typeBillet) {
		this.typeBillet = typeBillet;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}
}
