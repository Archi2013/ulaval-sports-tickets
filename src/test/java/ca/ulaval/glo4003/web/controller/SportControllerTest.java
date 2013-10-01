package ca.ulaval.glo4003.web.controller;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
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

import ca.ulaval.glo4003.domain.services.SportService;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.persistence.dao.SportDoesntExistException;
import ca.ulaval.glo4003.utility.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.utility.SportUrlMapperPropertieFile;
import ca.ulaval.glo4003.web.viewmodel.GameSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportViewModel;

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
	private GameSimpleViewModel gameViewModel;

	@Mock
	List<GameDto> gameDtosEmpty;

	@InjectMocks
	private SportController controller;

	List<GameDto> gameDtos;
	List<GameDto> gameDtosNonEmpty;

	List<GameSimpleViewModel> gameViewModels;
	List<GameSimpleViewModel> gameViewModelsNonEmpty;

	@Before
	public void setUp() throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		gameDtos = newArrayList();
		gameViewModels = newArrayList();

		gameDtosNonEmpty = newArrayList();
		gameDtosNonEmpty.add(gameDto);
		gameViewModelsNonEmpty = newArrayList();
		gameViewModelsNonEmpty.add(gameViewModel);

		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
	}

	@Test
	public void getSports_should_get_sports_from_service() {
		controller.getSports(model);

		verify(sportService).getSports();
	}

	@Test
	public void getSports_should_add_the_sports_to_model() {
		List<SportSimpleViewModel> viewModels = newArrayList();
		when(sportService.getSports()).thenReturn(viewModels);

		controller.getSports(model);

		verify(model).addAttribute("sports", viewModels);
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
		when(sportService.getGamesForSport(SPORT_NAME)).thenReturn(gameViewModels);

		String path = controller.getSportGames(SPORT_URL, model);

		assertEquals("sport/no-games", path);
	}

	@Test
	public void getSportsGames_should_not_add_sport_games_to_model_when_game_doesnt_have_any_game()
			throws SportDoesntExistException {
		when(sportService.getGamesForSport(SPORT_NAME)).thenReturn(gameViewModels);

		controller.getSportGames(SPORT_URL, model);

		verify(model, never()).addAttribute(eq("games"), any());
	}

	@Test
	public void getSportGames_should_return_correct_path_when_dao_return_a_non_empty_list() throws SportDoesntExistException {
		when(sportService.getGamesForSport(SPORT_NAME)).thenReturn(gameViewModelsNonEmpty);

		String path = controller.getSportGames(SPORT_URL, model);

		assertEquals("sport/games", path);
	}

	@Test
	public void getSportGames_should_add_sport_to_model_when_dao_returns_a_non_empty_list() throws SportDoesntExistException,
			RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportService.getGamesForSport(SPORT_NAME)).thenReturn(gameViewModelsNonEmpty);

		controller.getSportGames(SPORT_URL, model);

		verify(model).addAttribute("games", gameViewModelsNonEmpty);
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
