package ca.ulaval.glo4003.web.viewmodel.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.web.converter.GameSimpleConverter;
import ca.ulaval.glo4003.web.viewmodel.GameSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.GamesViewModel;

@Component
public class GamesViewModelFactory {

	@Inject
	private GameSimpleConverter converter;

	public GamesViewModel createViewModel(String sportName, List<GameDto> games) {
		GamesViewModel viewModel = new GamesViewModel(sportName);
		List<GameSimpleViewModel> gamesViewModels = converter.convert(games);
		viewModel.getGames().addAll(gamesViewModels);
		return viewModel;
	}
}
