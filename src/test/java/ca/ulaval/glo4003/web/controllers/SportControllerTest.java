package ca.ulaval.glo4003.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.services.SportService;
import ca.ulaval.glo4003.domain.utilities.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapperPropertieFile;
import ca.ulaval.glo4003.persistence.dao.SportDoesntExistException;
import ca.ulaval.glo4003.web.viewmodel.GamesViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportsViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SportControllerTest {

	private static final String SPORT_NAME = "Basketball Féminin";
	private static final String SPORT_URL = "basketball-feminin";

	@Mock
	private Model model;

	@Mock
	private SportUrlMapperPropertieFile sportUrlMapper;

	@Mock
	private SportService sportService;

	@Mock
	private SportDto sportDto;

	@Mock
	private GameDto gameDto;

	@Mock
	private SportViewModel sportViewModel;

	@Mock
	private GamesViewModel gamesViewModel;

	@Mock
	List<GameDto> gameDtosEmpty;

	@InjectMocks
	private SportController controller;

	List<GameDto> gameDtos;
	List<GameDto> gameDtosNonEmpty;

	@Before
	public void setUp() throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		when(gamesViewModel.hasGames()).thenReturn(true);
	}

	@Test
	public void getSports_should_get_sports_from_service() {
		controller.getSports(model);

		verify(sportService).getSports();
	}

	@Test
	public void getSports_should_add_the_sports_to_model() {
		SportsViewModel viewModel = new SportsViewModel();
		when(sportService.getSports()).thenReturn(viewModel);

		controller.getSports(model);

		verify(model).addAttribute("sports", viewModel);
	}

	@Test
	public void getSports_should_return_right_path() {
		String path = controller.getSports(model);

		assertEquals("sport/list", path);
	}

	@Test
	public void getSportGames_should_get_games_from_service() throws SportDoesntExistException {

		controller.getSportGames(SPORT_URL, model);

		verify(sportService).getGamesForSport(SPORT_NAME);
	}

	@Test
	public void getSportsGames_should_return_no_games_path_when_sport_doesnt_have_any_game() throws SportDoesntExistException {
		when(gamesViewModel.hasGames()).thenReturn(false);
		when(sportService.getGamesForSport(SPORT_NAME)).thenReturn(gamesViewModel);

		String path = controller.getSportGames(SPORT_URL, model);

		assertEquals("sport/no-games", path);
	}

	@Test
	public void getSportsGames_should_add_view_model_to_model_when_game_doesnt_have_any_game() throws SportDoesntExistException {
		when(gamesViewModel.hasGames()).thenReturn(false);
		when(sportService.getGamesForSport(SPORT_NAME)).thenReturn(gamesViewModel);

		controller.getSportGames(SPORT_URL, model);

		verify(model).addAttribute("games", gamesViewModel);
	}

	@Test
	public void getSportGames_should_return_correct_path_when_games_exist() throws SportDoesntExistException {
		when(sportService.getGamesForSport(SPORT_NAME)).thenReturn(gamesViewModel);

		String path = controller.getSportGames(SPORT_URL, model);

		assertEquals("sport/games", path);
	}

	@Test
	public void getSportGames_should_add_sport_to_model_when_games_exist() throws SportDoesntExistException {

		when(sportService.getGamesForSport(SPORT_NAME)).thenReturn(gamesViewModel);

		controller.getSportGames(SPORT_URL, model);

		verify(model).addAttribute("games", gamesViewModel);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getSportGames_should_redirect_to_404_path_when_sport_doesnt_exist_in_propertie_file() throws RuntimeException,
			SportDoesntExistInPropertieFileException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenThrow(SportDoesntExistInPropertieFileException.class);

		String path = controller.getSportGames(SPORT_URL, model);

		assertEquals("error/404", path);
	}
}
