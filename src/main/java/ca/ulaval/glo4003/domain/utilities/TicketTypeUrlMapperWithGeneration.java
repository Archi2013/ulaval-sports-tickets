package ca.ulaval.glo4003.domain.utilities;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.persistence.daos.SectionDao;

@Component
public class TicketTypeUrlMapperWithGeneration extends UrlMapper implements TicketTypeUrlMapper {
	
	@Inject
	SectionDao sectionDao;

	@Override
	public String getUrl(String sectionName) {
		return createUrl(sectionName);
	}

	@Override
	public String getTicketType(String sourceTicketTypeUrl) throws NoTicketTypeForUrlException {
		Set<String> sections = sectionDao.getAllSections();
		
		for (String sectionName : sections) {
			String url = getUrl(sectionName);
			
			if (url.equals(sourceTicketTypeUrl)) {
				return sectionName;
			}
		}
		
		throw new NoTicketTypeForUrlException();
	}
}
