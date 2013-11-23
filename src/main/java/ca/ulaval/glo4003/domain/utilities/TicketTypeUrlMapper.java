package ca.ulaval.glo4003.domain.utilities;



public interface TicketTypeUrlMapper {
	public String getUrl(String sectionName);

	public String getTicketType(String ticketTypeUrl) throws NoTicketTypeForUrlException;

}
