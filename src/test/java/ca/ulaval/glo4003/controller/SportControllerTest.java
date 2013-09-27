package ca.ulaval.glo4003.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.controller.SportController;
import ca.ulaval.glo4003.dao.GameDao;
import ca.ulaval.glo4003.dao.SportDao;
import ca.ulaval.glo4003.dao.SportDoesntExistException;
import ca.ulaval.glo4003.datafilter.DataFilter;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;

@RunWith(MockitoJUnitRunner.class)
public class SportControllerTest {

	private static final String SPORT_NAME = "SPORT_NAME";

	@Mock
	private SportDao sportDaoMock;

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private Model modelMock;

	@Mock
	private DataFilter<GameDto> filterMock;

	@InjectMocks
	private SportController controller;

	private List<GameDto> games;

	@Before
	public void setUp() throws SportDoesntExistException {
		games = new ArrayList<GameDto>();
		games.add(new GameDto(1, "", DateTime.now()));
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);
	}

	@Test
	public void getSports_should_get_sports_from_dao() {
		controller.getSports(modelMock);

		verify(sportDaoMock).getAll();
	}

	@Test
	public void getSports_should_add_sports_list_to_model() {
		List<SportDto> sports = new ArrayList<SportDto>();
		when(sportDaoMock.getAll()).thenReturn(sports);

		controller.getSports(modelMock);

		verify(modelMock).addAttribute("sports", sports);
	}

	@Test
	public void getSportGames_should_add_sport_name_to_model() {
		controller.getSportGames(SPORT_NAME, modelMock);

		verify(modelMock).addAttribute("sportName", SPORT_NAME);
	}

	@Test
	public void getSportGames_should_use_filter_on_list_returned_by_dao() throws SportDoesntExistException {
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);

		controller.getSportGames(SPORT_NAME, modelMock);

		verify(filterMock).applyFilterOnList(games);
	}

	@Test
	public void getSportGames_should_add_sport_games_to_model_when_dao_return_a_non_empty_list()
			throws SportDoesntExistException {
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);
		controller.getSportGames(SPORT_NAME, modelMock);

		verify(modelMock).addAttribute("games", games);
	}

	@Test
	public void getSportGames_should_return_correct_path_when_dao_return_a_non_empty_list() {

		String path = controller.getSportGames(SPORT_NAME, modelMock);

		assertEquals("sport/games", path);
	}

	@Test
	public void getSportGames_should_redirect_to_home_path_when_sport_doesnt_exist() throws SportDoesntExistException {
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenThrow(SportDoesntExistException.class);

		String path = controller.getSportGames(SPORT_NAME, modelMock);

		assertEquals("error/404", path);
	}

	@Test
	public void getSportsGames_should_not_add_sport_games_to_model_when_dao_returns_empty_list()
			throws SportDoesntExistException {
		List<GameDto> games = new ArrayList<GameDto>();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);

		controller.getSportGames(SPORT_NAME, modelMock);

		verify(modelMock, never()).addAttribute(eq("games"), anyString());
	}

	@Test
	public void getSportsGames_should_return_no_games_path_when_sport_doesnt_have_any_game()
			throws SportDoesntExistException {
		List<GameDto> games = new ArrayList<GameDto>();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);

		String path = controller.getSportGames(SPORT_NAME, modelMock);

		assertEquals("sport/no-games", path);
	}

}
