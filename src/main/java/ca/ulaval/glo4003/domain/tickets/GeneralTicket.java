package ca.ulaval.glo4003.domain.tickets;

import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.tickets.dto.GeneralTicketDto;
import ca.ulaval.glo4003.tickets.dto.TicketDto;

public class GeneralTicket extends Ticket {

	public GeneralTicket(double price, boolean available, TicketAssignationState associationState) {
		super(associationState, price, available);
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
		this.assignationState.fillDataInDto(data);
		return data;

	}

}
