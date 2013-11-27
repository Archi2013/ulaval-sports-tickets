package ca.ulaval.glo4003.domain.tickets;

import ca.ulaval.glo4003.exceptions.NoTicketTypeForUrlException;



public interface TicketTypeUrlMapper {
	public String getUrl(String sectionName);

	public String getTicketType(String ticketTypeUrl) throws NoTicketTypeForUrlException;

}
