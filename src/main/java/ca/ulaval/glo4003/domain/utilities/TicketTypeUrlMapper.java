package ca.ulaval.glo4003.domain.utilities;

public interface TicketTypeUrlMapper {
	public String getUrl(String admissionType, String sectionName);

	public TicketType getTicketType(String ticketTypeUrl) throws NoTicketTypeForUrlException;

}
