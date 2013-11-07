package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.web.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.SectionsViewModelFactory;

@Service
public class QueryGameService {

	@Inject
	private GameDao gameDao;

	@Inject
	private SectionDao sectionDao;

	@Inject
	private SectionsViewModelFactory viewModelFactory;

	public SectionsViewModel getSectionsForGame(Long gameId) throws GameDoesntExistException {
		GameDto game = gameDao.get(gameId);
		List<SectionDto> sections = sectionDao.getAll(gameId);
		return viewModelFactory.createViewModel(game, sections);
	}
}
