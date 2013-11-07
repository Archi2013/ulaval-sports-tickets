package ca.ulaval.glo4003.domain.tickets;

import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;

public class GeneralTicket extends PersistableTicket {

	public GeneralTicket(String seat, String section, TicketAssignationState associationState) {
		super(associationState);
	}

	@Override
	public boolean isSame(Ticket otherTicket) {
		return false;
	}

	@Override
	public boolean isSeat(String seat) {
		return false;
	}

	@Override
	public boolean isSection(String section) {
		return section.equals(TicketKind.GENERAL_ADMISSION.toString());
	}

}
