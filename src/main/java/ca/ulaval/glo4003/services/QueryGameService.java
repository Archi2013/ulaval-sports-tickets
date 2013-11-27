package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.sports.SportUrlMapper;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionsViewModelFactory;

@Service
public class QueryGameService {

	@Inject
	private GameDao gameDao;

	@Inject
	private SectionDao sectionDao;

	@Inject
	private SportUrlMapper sportUrlMapper;

	@Inject
	private SectionsViewModelFactory viewModelFactory;

	public SectionsViewModel getAvailableSectionsForGame(String sportUrl, DateTime gameDate) throws GameDoesntExistException,
	        NoSportForUrlException {
		String sportName = sportUrlMapper.getSportName(sportUrl);
		GameDto game = gameDao.get(sportName, gameDate);
		List<SectionDto> sections = sectionDao.getAllAvailable(sportName, gameDate);
		return viewModelFactory.createViewModel(game, sections);
	}
}
