package ca.ulaval.glo4003.constants;

import java.util.ArrayList;
import java.util.List;

public enum TicketKind {
	GENERAL_ADMISSION, WITH_SEAT;

	public String toString() {
		String name = "";
		switch (ordinal()) {
		case 0:
			name = "admission générale";
			break;
		case 1:
			name = "avec siège";
			break;
		default:
			name = "Erreur";
			break;
		}
		return name;
	}

	public static List<TicketKind> getTicketKinds() {
		List<TicketKind> ticketTypes = new ArrayList<>();
		ticketTypes.add(TicketKind.GENERAL_ADMISSION);
		ticketTypes.add(TicketKind.WITH_SEAT);
		return ticketTypes;
	}

}