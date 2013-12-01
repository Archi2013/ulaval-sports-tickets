package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.sports.SportUrlMapper;
import ca.ulaval.glo4003.domain.tickets.TicketTypeUrlMapper;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.NoTicketTypeForUrlException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenGeneralTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenWithSeatTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.ChosenTicketsViewModelFactory;
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
	private ChosenTicketsViewModelFactory chosenTicketsViewModelFactory;
	
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

	public ChosenGeneralTicketsViewModel getChosenGeneralTicketsViewModel(String sportName, DateTime gameDate, String sectionUrl)
			throws SectionDoesntExistException {
		try {
			String sectionName = ticketTypeUrlMapper.getTicketType(sectionUrl);
			GameDto gameDto = gameDao.get(sportName, gameDate);
			SectionDto sectionDto = sectionDao.getAvailable(sportName, gameDate, sectionName);

			return chosenTicketsViewModelFactory.createViewModelForGeneralTickets(gameDto, sectionDto);
		} catch (GameDoesntExistException | NoTicketTypeForUrlException e) {
			throw new SectionDoesntExistException();
		}
	}
	
	public ChosenWithSeatTicketsViewModel getChosenWithSeatTicketsViewModel(String sportName, DateTime gameDate, String sectionUrl)
			throws SectionDoesntExistException {
		try {
			String sectionName = ticketTypeUrlMapper.getTicketType(sectionUrl);
			GameDto gameDto = gameDao.get(sportName, gameDate);
			SectionDto sectionDto = sectionDao.getAvailable(sportName, gameDate, sectionName);

			return chosenTicketsViewModelFactory.createViewModelForWithSeatTickets(gameDto, sectionDto);
		} catch (GameDoesntExistException | NoTicketTypeForUrlException e) {
			throw new SectionDoesntExistException();
		}
	}

}
