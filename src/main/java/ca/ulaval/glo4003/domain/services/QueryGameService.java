package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
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
