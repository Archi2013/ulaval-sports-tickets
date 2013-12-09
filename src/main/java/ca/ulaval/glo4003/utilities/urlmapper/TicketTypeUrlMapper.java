package ca.ulaval.glo4003.utilities.urlmapper;

import ca.ulaval.glo4003.exceptions.NoTicketTypeForUrlException;



public interface TicketTypeUrlMapper {
	public String getUrl(String sectionName);

	public String getTicketType(String ticketTypeUrl) throws NoTicketTypeForUrlException;

}
