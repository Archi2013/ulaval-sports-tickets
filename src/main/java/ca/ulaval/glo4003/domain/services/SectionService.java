package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.SectionDoesntExistInPropertiesFileException;
import ca.ulaval.glo4003.domain.utilities.SectionUrlMapper;
import ca.ulaval.glo4003.domain.utilities.TicketType;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.converters.SectionConverter;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;

@Service
public class SectionService {

	@Inject
	private SectionUrlMapper sectionUrlMapper;

	@Inject
	private SectionDao sectionDao;

	@Inject
	private SectionConverter sectionConverter;

	public SectionViewModel getSection(int gameId, String sectionUrl) throws SectionDoesntExistException {
		try {
			TicketType ticketType = sectionUrlMapper.getTicketType(sectionUrl);
			SectionDto section = sectionDao.get(gameId, ticketType.admissionType, ticketType.sectionName);
			SectionViewModel sectionViewModel = sectionConverter.convert(section);
			return sectionViewModel;
		} catch (SectionDoesntExistInPropertiesFileException e) {
			throw new SectionDoesntExistException();
		}
	}

}
