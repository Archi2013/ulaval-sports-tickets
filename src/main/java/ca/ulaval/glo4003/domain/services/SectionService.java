package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.SectionDoesntExistInPropertiesFileException;
import ca.ulaval.glo4003.domain.utilities.SectionUrlMapper;
import ca.ulaval.glo4003.domain.utilities.TicketType;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.SectionViewModelFactory;

@Service
public class SectionService {

	@Inject
	private SectionUrlMapper sectionUrlMapper;

	@Inject
	private GameDao gameDao;

	@Inject
	private SectionDao sectionDao;

	@Inject
	private SectionViewModelFactory sectionFactory;

	public SectionViewModel getSection(int gameId, String sectionUrl) throws SectionDoesntExistException {
		try {
			TicketType ticketType = sectionUrlMapper.getTicketType(sectionUrl);
			GameDto game = gameDao.get(gameId);
			SectionDto section = sectionDao.get(gameId, ticketType.admissionType, ticketType.sectionName);
			SectionViewModel sectionViewModel = sectionFactory.createViewModel(section, game);
			return sectionViewModel;
		} catch (SectionDoesntExistInPropertiesFileException | GameDoesntExistException e) {
			throw new SectionDoesntExistException();
		}
	}

}
