package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.datafilter.DataFilter;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.persistence.dao.GameDao;
import ca.ulaval.glo4003.persistence.dao.SportDao;
import ca.ulaval.glo4003.persistence.dao.SportDoesntExistException;
import ca.ulaval.glo4003.web.converter.GameSimpleConverter;
import ca.ulaval.glo4003.web.converter.SportSimpleConverter;
import ca.ulaval.glo4003.web.viewmodel.GameSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;

@Service
public class SportService {
	@Inject
	private SportDao sportDao;

	@Inject
	private GameDao gameDao;

	@Inject
	private DataFilter<GameDto> filter;

	@Inject
	private SportSimpleConverter sportSimpleConverter;

	@Inject
	private GameSimpleConverter gameConverter;

	public List<SportSimpleViewModel> getSports() {
		List<SportDto> sports = sportDao.getAll();
		return sportSimpleConverter.convert(sports);
	}

	public List<GameSimpleViewModel> getGamesForSport(String sportName) throws SportDoesntExistException {
		List<GameDto> games = gameDao.getGamesForSport(sportName);
		filter.applyFilterOnList(games);
		return gameConverter.convert(games);
	}

}
