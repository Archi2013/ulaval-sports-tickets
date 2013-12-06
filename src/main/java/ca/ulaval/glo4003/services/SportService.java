package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.sports.SportDao;
import ca.ulaval.glo4003.domain.sports.SportDto;
import ca.ulaval.glo4003.presentation.viewmodels.SportsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SportsViewModelFactory;

@Service
public class SportService {
	@Inject
	private SportDao sportDao;

	@Inject
	private SportsViewModelFactory sportsViewModelFactory;

	public SportsViewModel getSports() {
		List<SportDto> sports = sportDao.getAll();
		return sportsViewModelFactory.createViewModel(sports);
	}
}
