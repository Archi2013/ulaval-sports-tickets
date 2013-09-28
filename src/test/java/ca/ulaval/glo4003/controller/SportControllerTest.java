package ca.ulaval.glo4003.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.dao.GameDao;
import ca.ulaval.glo4003.dao.SportDao;
import ca.ulaval.glo4003.dao.SportDoesntExistException;
import ca.ulaval.glo4003.datafilter.DataFilter;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.utility.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.utility.SportUrlMapper;

@RunWith(MockitoJUnitRunner.class)
public class SportControllerTest {

	private static final String SPORT_NAME = "Basketball Féminin";
	private static final String SPORT_URL = "basketball-feminin";

	@Mock
	private SportDao sportDaoMock;

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private Model modelMock;

	@Mock
	private DataFilter<GameDto> filterMock;

	@Mock
	private SportUrlMapper sportUrlMapperMock;
	
	@InjectMocks
	private SportController controller;

	private List<GameDto> nonEmptyGames;
	private List<SportDto> nonEmptySports;
	private SportDto ASportDto;

	@Before
	public void setUp() throws SportDoesntExistException {
		nonEmptyGames = new ArrayList<>();
		nonEmptyGames.add(new GameDto(1, "", DateTime.now().plusDays(1)));
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(nonEmptyGames);
		
		nonEmptySports = new ArrayList<>();
		ASportDto = new SportDto(SPORT_NAME);
		nonEmptySports.add(ASportDto);
		when(sportDaoMock.getAll()).thenReturn(nonEmptySports);
	}

	@Test
	public void getSports_should_get_sports_from_dao() {
		controller.getSports(modelMock);

		verify(sportDaoMock).getAll();
	}

	@Test
	public void with_an_empty_sport_list_getSports_should_add_an_empty_map_to_model() {
		List<SportDto> sports = new ArrayList<SportDto>();
		when(sportDaoMock.getAll()).thenReturn(sports);

		controller.getSports(modelMock);
		
		verify(modelMock).addAttribute("sportUrls", new HashMap<SportDto, String>());
	}
	
	@Test
	public void with_a_sport_list_getSports_should_add_a_map_to_model() throws RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportDaoMock.getAll()).thenReturn(nonEmptySports);
		when(sportUrlMapperMock.getSportUrl(SPORT_NAME)).thenReturn(SPORT_URL);

		controller.getSports(modelMock);
		
		Map<SportDto, String> map = new HashMap<>();
		map.put(ASportDto, SPORT_URL);
		verify(modelMock).addAttribute("sportUrls", map);
	}

	@Test
	public void getSportGames_should_add_sport_name_to_model() throws RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		
		controller.getSportGames(SPORT_URL, modelMock);

		verify(modelMock).addAttribute("sportName", SPORT_NAME);
	}

	@Test
	public void getSportGames_should_use_filter_on_list_returned_by_dao() throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(nonEmptyGames);
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);

		controller.getSportGames(SPORT_URL, modelMock);

		verify(filterMock).applyFilterOnList(nonEmptyGames);
	}

	@Test
	public void getSportGames_should_add_sport_games_to_model_when_dao_return_a_non_empty_list()
			throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(nonEmptyGames);
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		
		controller.getSportGames(SPORT_URL, modelMock);

		verify(modelMock).addAttribute("games", nonEmptyGames);
	}

	@Test
	public void getSportGames_should_return_correct_path_when_dao_return_a_non_empty_list() throws RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		
		String path = controller.getSportGames(SPORT_URL, modelMock);

		assertEquals("sport/games", path);
	}

	@Test
	public void getSportGames_should_redirect_to_404_path_when_sport_doesnt_exist() throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenThrow(SportDoesntExistException.class);

		String path = controller.getSportGames(SPORT_URL, modelMock);

		assertEquals("error/404", path);
	}

	@Test
	public void getSportGames_should_redirect_to_404_path_when_sport_doesnt_exist_in_propertie_file() throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenThrow(SportDoesntExistInPropertieFileException.class);

		String path = controller.getSportGames(SPORT_URL, modelMock);

		assertEquals("error/404", path);
	}
	
	@Test
	public void getSportsGames_should_not_add_sport_games_to_model_when_dao_returns_empty_list() throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		List<GameDto> games = new ArrayList<GameDto>();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);

		controller.getSportGames(SPORT_URL, modelMock);

		verify(modelMock, never()).addAttribute(eq("games"), anyString());
	}

	@Test
	public void getSportsGames_should_return_no_games_path_when_sport_doesnt_have_any_game()
			throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		List<GameDto> games = new ArrayList<GameDto>();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);

		String path = controller.getSportGames(SPORT_NAME, modelMock);

		assertEquals("sport/no-games", path);
	}

}
