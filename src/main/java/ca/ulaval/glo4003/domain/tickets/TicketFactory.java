package ca.ulaval.glo4003.domain.tickets;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.tickets.dto.GeneralTicketDto;
import ca.ulaval.glo4003.tickets.dto.SeatedTicketDto;
import ca.ulaval.glo4003.tickets.dto.TicketDto;

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
			return new GeneralTicket(data.price, data.available, createAssignationState(data));
		}
		return new SeatedTicket(data.seat, data.section, data.price, data.available, createAssignationState(data));
	}

	private TicketAssignationState createAssignationState(TicketDto data) {
		if (data.sportName == null || data.gameDate == null || data.ticketId == null || data.ticketId < 0) {
			return new UnassignedTicketState();
		}
		return new AssignedTicketState(data.sportName, data.gameDate, data.ticketId);
	}

}
