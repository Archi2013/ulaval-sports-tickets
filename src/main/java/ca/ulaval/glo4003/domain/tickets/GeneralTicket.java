package ca.ulaval.glo4003.domain.tickets;

import ca.ulaval.glo4003.domain.dtos.GeneralTicketDto;
import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;

public class GeneralTicket extends Ticket {

	public GeneralTicket(double price, TicketAssignationState associationState) {
		super(associationState, price);
	}

	@Override
	public boolean isSame(Ticket otherTicket) {
		return false;
	}

	@Override
	public boolean hasSeat(String seat) {
		return false;
	}

	@Override
	public boolean hasSection(String section) {
		return section.equals(TicketKind.GENERAL_ADMISSION.toString());
	}

	@Override
	public TicketDto saveDataInDTO() {
		TicketDto data = new GeneralTicketDto(price, available);
		associationState.fillDataInDto(data);
		return data;

	}

}
