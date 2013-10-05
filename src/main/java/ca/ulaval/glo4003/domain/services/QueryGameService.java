package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.SectionsViewModelFactory;

@Service
public class QueryGameService {

	@Inject
	private GameDao gameDao;

	@Inject
	private SectionsViewModelFactory viewModelFactory;

	public SectionsViewModel getSectionsForGame(long gameId) throws GameDoesntExistException {
		GameDto game = gameDao.get(gameId);
		return viewModelFactory.createViewModel(game);
	}
}
