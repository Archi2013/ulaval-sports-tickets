package ca.ulaval.glo4003.domain.factories;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GeneralTicketDto;
import ca.ulaval.glo4003.domain.dtos.SeatedTicketDto;
import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.tickets.GeneralTicket;
import ca.ulaval.glo4003.domain.tickets.SeatedTicket;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.domain.tickets.state.AssignedTicketState;
import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;
import ca.ulaval.glo4003.domain.tickets.state.UnassignedTicketState;

@Component
public class TicketFactory {

	public Ticket createGeneralTicket(double price, boolean available) {
		return createTicket(new GeneralTicketDto(price, available));
	}

	public Ticket createSeatedTicket(String section, String seat, double price, boolean available) {
		return createTicket(new SeatedTicketDto(section, seat, price, available));
	}

	public Ticket createTicket(TicketDto data) {
		if (data.isGeneral()) {
			return new GeneralTicket(data.price, createAssignationState(data));
		}
		return new SeatedTicket(data.seat, data.section, data.price, createAssignationState(data));
	}

	private TicketAssignationState createAssignationState(TicketDto data) {
		if (data.sportName == null || data.gameDate == null || data.ticketId < 0) {
			return new UnassignedTicketState();
		}
		return new AssignedTicketState(data.sportName, data.gameDate, data.ticketId);
	}

}
