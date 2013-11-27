package ca.ulaval.glo4003.presentation.viewmodels;

import ca.ulaval.glo4003.constants.TicketKind;

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
