package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.presentation.converters.GameConverter;
import ca.ulaval.glo4003.presentation.viewmodels.GameViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;

@Component
public class GamesViewModelFactory {

	@Inject
	private GameConverter converter;

	public GamesViewModel createViewModel(String sportName, List<GameDto> games) {
		GamesViewModel viewModel = new GamesViewModel(sportName);
		List<GameViewModel> gamesViewModels = converter.convert(games);
		viewModel.getGames().addAll(gamesViewModels);
		return viewModel;
	}
}
