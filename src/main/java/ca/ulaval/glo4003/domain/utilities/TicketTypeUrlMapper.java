package ca.ulaval.glo4003.domain.utilities;

import ca.ulaval.glo4003.persistence.daos.TicketType;


public interface TicketTypeUrlMapper {
	public String getUrl(String admissionType, String sectionName);

	public TicketType getTicketType(String ticketTypeUrl) throws NoTicketTypeForUrlException;

}
