package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.domain.utilities.NoTicketTypeForUrlException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.domain.utilities.TicketTypeUrlMapper;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.ChooseTicketsViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionViewModelFactory;

@Service
public class SectionService {

	@Inject
	private TicketTypeUrlMapper ticketTypeUrlMapper;

	@Inject
	private GameDao gameDao;

	@Inject
	private SectionDao sectionDao;

	@Inject
	private SectionViewModelFactory sectionFactory;

	@Inject
	private ChooseTicketsViewModelFactory chooseTicketsViewModelFactory;
	
	@Inject
	private SportUrlMapper sportUrlMapper;

	public SectionViewModel getSection(String sportNameUrl, DateTime gameDate, String sectionUrl) throws SectionDoesntExistException {
		try {
			String sportName = sportUrlMapper.getSportName(sportNameUrl);
			String sectionName = ticketTypeUrlMapper.getTicketType(sectionUrl);
			GameDto game = gameDao.get(sportName, gameDate);
			SectionDto section = sectionDao.get(sportName, gameDate, sectionName);
			SectionViewModel sectionViewModel = sectionFactory.createViewModel(section, game);

			return sectionViewModel;
		} catch (GameDoesntExistException | NoTicketTypeForUrlException | NoSportForUrlException e) {
			throw new SectionDoesntExistException();
		}
	}

	public SectionViewModel getAvailableSection(String sportNameUrl, DateTime gameDate, String sectionUrl) throws SectionDoesntExistException {
		try {
			String sportName = sportUrlMapper.getSportName(sportNameUrl);
			String sectionName = ticketTypeUrlMapper.getTicketType(sectionUrl);
			GameDto game = gameDao.get(sportName, gameDate);
			SectionDto section = sectionDao.getAvailable(sportName, gameDate, sectionName);
			SectionViewModel sectionViewModel = sectionFactory.createViewModel(section, game);

			return sectionViewModel;
		} catch (GameDoesntExistException | NoTicketTypeForUrlException | NoSportForUrlException e) {
			throw new SectionDoesntExistException();
		}
	}

	public ChooseTicketsViewModel getChooseTicketsViewModel(String sportNameUrl, DateTime gameDate, String sectionUrl) throws SectionDoesntExistException {
		try {
			String sectionName = ticketTypeUrlMapper.getTicketType(sectionUrl);
			String sportName = sportUrlMapper.getSportName(sportNameUrl);
			GameDto gameDto = gameDao.get(sportName, gameDate);
			SectionDto sectionDto = sectionDao.get(sportName, gameDate, sectionName);

			return chooseTicketsViewModelFactory.createViewModel(gameDto, sectionDto);
		} catch (GameDoesntExistException | NoTicketTypeForUrlException | NoSportForUrlException e) {
			throw new SectionDoesntExistException();
		}
	}

	public ChooseTicketsViewModel getAvailableChooseTicketsViewModel(String sportName, DateTime gameDate, String sectionUrl)
			throws SectionDoesntExistException {
		try {
			String sectionName = ticketTypeUrlMapper.getTicketType(sectionUrl);
			GameDto gameDto = gameDao.get(sportName, gameDate);
			SectionDto sectionDto = sectionDao.getAvailable(sportName, gameDate, sectionName);

			return chooseTicketsViewModelFactory.createViewModel(gameDto, sectionDto);
		} catch (GameDoesntExistException | NoTicketTypeForUrlException e) {
			throw new SectionDoesntExistException();
		}
	}

}
