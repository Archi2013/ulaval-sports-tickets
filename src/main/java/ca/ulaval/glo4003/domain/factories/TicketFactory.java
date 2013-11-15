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
	private static final String GENERAL_SECTION = "Générale";

	public PersistableTicket instantiateTicket(double price) {
		return instantiateTicket(new TicketDto(null, GENERAL_SECTION, null, 0, price, true));
	}

	public PersistableTicket instantiateTicket(String seat, String section, double price, boolean available) {
		return instantiateTicket(new TicketDto(null, null, 0, price, seat, section, available));
	}

	public PersistableTicket instantiateTicket(TicketDto data) {
		if (sectionIsGeneral(data.getSection())) {
			System.out.println("TicketFactory: creation d'un ticket general non assigne");
			return new GeneralTicket(data.getPrice(), createAssignationState(data));
		}
		return new SeatedTicket(data.getSeat(), data.getSection(), data.getPrice(), createAssignationState(data));
	}

	private boolean sectionIsGeneral(String section) {
		if (section == null) {
			return false;
		}
		return section.equals(GENERAL_SECTION);
	}

	private TicketAssignationState createAssignationState(TicketDto data) {
		if (data.getSportName() == null || data.getGameDate() == null || data.getTicketId() < 0) {
			return new UnassignedTicketState();
		}
		return new AssignedTicketState(data.getSportName(), data.getGameDate(), data.getTicketId());
	}

}
