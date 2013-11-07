package ca.ulaval.glo4003.domain.tickets.state;

public interface TicketAssociationState {

	boolean isAssociable();

	TicketAssociationState associate();

}
