package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.NoTicketTypeForUrlException;
import ca.ulaval.glo4003.domain.utilities.TicketType;
import ca.ulaval.glo4003.domain.utilities.TicketTypeUrlMapper;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.ChooseTicketsViewModelFactory;
import ca.ulaval.glo4003.web.viewmodels.factories.SectionViewModelFactory;

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

	public SectionViewModel getSection(Long gameId, String sectionUrl) throws SectionDoesntExistException {
		try {
			TicketType ticketType = ticketTypeUrlMapper.getTicketType(sectionUrl);
			GameDto game = gameDao.get(gameId);
			SectionDto section = sectionDao.get(gameId, ticketType.sectionName);
			SectionViewModel sectionViewModel = sectionFactory.createViewModel(section, game);
			
			return sectionViewModel;
		} catch (GameDoesntExistException | NoTicketTypeForUrlException e) {
			throw new SectionDoesntExistException();
		}
	}

	public ChooseTicketsViewModel getChooseTicketsViewModel(Long gameId, String sectionUrl) throws SectionDoesntExistException {
		try {
			TicketType ticketType = ticketTypeUrlMapper.getTicketType(sectionUrl);
			GameDto gameDto = gameDao.get(gameId);
			SectionDto sectionDto = sectionDao.get(gameId, ticketType.sectionName);
			
			return chooseTicketsViewModelFactory.createViewModel(gameDto, sectionDto);
		} catch (GameDoesntExistException | NoTicketTypeForUrlException e) {
			throw new SectionDoesntExistException();
		}
	}

}
