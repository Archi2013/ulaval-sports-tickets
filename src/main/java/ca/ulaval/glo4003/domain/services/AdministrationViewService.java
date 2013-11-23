package ca.ulaval.glo4003.domain.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GameSelectionViewModel;
import ca.ulaval.glo4003.utilities.time.DisplayDate;

@Service
public class AdministrationViewService {

	@Inject
	private GameDao gameDao;

	public List<GameSelectionViewModel> getGameSelectionForSport(String sport) throws SportDoesntExistException {
		List<GameDto> gameDtos = gameDao.getGamesForSport(sport);

		List<GameSelectionViewModel> toReturn = new ArrayList<>();

		for (GameDto data : gameDtos) {
			toReturn.add(new GameSelectionViewModel(data.getOpponents(), new DisplayDate(data.getGameDate())));
		}

		return toReturn;

	}
}
