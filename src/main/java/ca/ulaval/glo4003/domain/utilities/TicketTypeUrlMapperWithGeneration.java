package ca.ulaval.glo4003.domain.utilities;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.TicketType;

@Component
public class TicketTypeUrlMapperWithGeneration extends UrlMapper implements TicketTypeUrlMapper {
	
	@Inject
	SectionDao sectionDao;

	@Override
	public String getUrl(String admissionType, String sectionName) {
		String admissionTypeUrl = createUrl(admissionType);
		String sectionNameUrl = createUrl(sectionName);
		String result = admissionTypeUrl + "--" + sectionNameUrl;
		return result;
	}

	@Override
	public TicketType getTicketType(String sourceTicketTypeUrl) throws NoTicketTypeForUrlException {
		Set<TicketType> ticketTypes = sectionDao.getAllTicketTypes();
		
		for (TicketType ticketType : ticketTypes) {
			String url = getUrl(ticketType.admissionType, ticketType.sectionName);
			
			if (url.equals(sourceTicketTypeUrl)) {
				return ticketType;
			}
		}
		
		throw new NoTicketTypeForUrlException();
	}
}
