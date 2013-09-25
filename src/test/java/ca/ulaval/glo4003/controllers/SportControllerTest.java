package ca.ulaval.glo4003.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.data_access.GameDao;
import ca.ulaval.glo4003.data_access.SportDao;
import ca.ulaval.glo4003.data_access.SportDoesntExistException;
import ca.ulaval.glo4003.dtos.GameDto;
import ca.ulaval.glo4003.dtos.SportDto;

@RunWith(MockitoJUnitRunner.class)
public class SportControllerTest {

	private static final String SPORT_NAME = "SPORT_NAME";

	@Mock
	private SportDao sportDaoMock;
	
	@Mock
	private GameDao gameDaoMock;
	
	@Mock
	private Model modelMock;

	@InjectMocks
	private SportController controller;

	@Before
	public void setUp() {

	}

	@Test
	public void getSports_should_get_sports_from_dao() {
		controller.getSports(modelMock);

		verify(sportDaoMock).getAll();
	}
	
	@Test
	public void getSports_should_add_sports_list_to_model(){
		List<SportDto> sports = new ArrayList<SportDto>();
		when(sportDaoMock.getAll()).thenReturn(sports);
		
		controller.getSports(modelMock);
		
		verify(modelMock).addAttribute("sports", sports);
	}
	
	@Test
	public void getSportGames_should_add_sport_games_to_model() throws SportDoesntExistException{
		List<GameDto> games = new ArrayList<GameDto>();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);
		
		controller.getSportGames(SPORT_NAME, modelMock);
		
		verify(modelMock).addAttribute("games", games);
	}
	
	@Test
	public void getSportGames_should_get_sport_games_from_games_dao() throws SportDoesntExistException {
		controller.getSportGames(SPORT_NAME, modelMock);
		
		verify(gameDaoMock).getGamesForSport(SPORT_NAME);
	}

}
