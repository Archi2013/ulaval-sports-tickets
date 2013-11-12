package ca.ulaval.glo4003.domain.factories;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.tickets.GeneralTicket;
import ca.ulaval.glo4003.domain.tickets.PersistableTicket;
import ca.ulaval.glo4003.domain.tickets.SeatedTicket;
import ca.ulaval.glo4003.domain.tickets.state.AssignedTicketState;
import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;
import ca.ulaval.glo4003.domain.tickets.state.UnassignedTicketState;

@Component
public class TicketFactory {

	public PersistableTicket instantiateTicket() {
		return instantiateTicket(new TicketDto(null, null, 0, 0, true));
	}

	public PersistableTicket instantiateTicket(String seat, String section, boolean available) {
		return instantiateTicket(new TicketDto(null, null, 0, 0, seat, section, available));
	}

	public PersistableTicket instantiateTicket(TicketDto data) {
		if (sectionIsGeneral(data.getSection())) {
			return new GeneralTicket(createAssignationState(data));
		}
		return new SeatedTicket(data.getSeat(), data.getSection(), createAssignationState(data));
	}

	private boolean sectionIsGeneral(String section) {
		return section == null;
	}

	private TicketAssignationState createAssignationState(TicketDto data) {
		if (data.getSportName() == null || data.getGameDate() == null || data.getTicketNumber() < 0) {
			return new UnassignedTicketState();
		}
		return new AssignedTicketState(data.getSportName(), data.getGameDate(), data.getTicketNumber());
	}

}
