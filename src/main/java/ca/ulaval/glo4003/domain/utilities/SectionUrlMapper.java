package ca.ulaval.glo4003.domain.utilities;

public interface SectionUrlMapper {
	public String getSectionUrl(String admissionType, String sectionName) throws SectionDoesntExistInPropertiesFileException;

	public TicketType getTicketType(String ticketTypeUrl) throws SectionDoesntExistInPropertiesFileException;

}
