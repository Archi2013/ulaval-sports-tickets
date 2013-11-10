package ca.ulaval.glo4003.domain.utilities;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.persistence.daos.SectionDao;

@Component
public class SectionUrlMapperWithGeneration extends UrlMapper {
	
	@Inject
	SectionDao sectionDao;

	public String getSectionUrl(String admissionType, String sectionName) {
		String admissionTypeUrl = createUrl(admissionType);
		String sectionNameUrl = createUrl(sectionName);
		String result = admissionTypeUrl + "--" + sectionNameUrl;
		return result;
	}

	public TicketType getTicketType(String sourceTicketTypeUrl) throws NoTicketTypeForUrlException {
		Set<TicketType> ticketTypes = sectionDao.getAllTicketTypes();
		
		for (TicketType ticketType : ticketTypes) {
			String url = getSectionUrl(ticketType.admissionType, ticketType.sectionName);
			
			if (url.equals(sourceTicketTypeUrl)) {
				return ticketType;
			}
		}
		
		throw new NoTicketTypeForUrlException();
	}
}
