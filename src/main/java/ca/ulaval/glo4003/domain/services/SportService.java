package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.datafilter.DataFilter;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.persistence.dao.SportDao;
import ca.ulaval.glo4003.web.converter.SportConverter;
import ca.ulaval.glo4003.web.converter.SportSimpleConverter;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportViewModel;

@Service
public class SportService {
	@Inject
	private SportDao sportDao;

	@Inject
	private DataFilter<GameDto> filter;

	@Inject
	private SportConverter sportConverter;

	@Inject
	private SportSimpleConverter sportSimpleConverter;

	public List<SportSimpleViewModel> getSports() {
		List<SportDto> sports = sportDao.getAll();
		return sportSimpleConverter.convert(sports);
	}

	public SportViewModel getSport(String sportName) {
		SportDto sport = sportDao.get(sportName);
		filter.applyFilterOnList(sport.getGames());
		return sportConverter.convert(sport);
	}

}
