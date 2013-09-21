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
import ca.ulaval.glo4003.dtos.GameDto;
import ca.ulaval.glo4003.dtos.SportDto;

@RunWith(MockitoJUnitRunner.class)
public class SportsControllerTest {

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
	public void getSport_should_get_sport_with_right_name_from_dao(){
		controller.getSport(SPORT_NAME, modelMock);
		
		verify(sportDaoMock).get(SPORT_NAME);
	}
	
	@Test
	public void getSport_should_add_sport_to_model(){
		SportDto sport = new SportDto();
		when(sportDaoMock.get(SPORT_NAME)).thenReturn(sport);
		
		controller.getSport(SPORT_NAME, modelMock);
				
		verify(modelMock).addAttribute("sport", sport);
	}
	
	@Test
	public void getSportGames_should_add_sport_games_to_model(){
		List<GameDto> games = new ArrayList<GameDto>();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);
		
		controller.getSportGames(SPORT_NAME, modelMock);
		
		verify(modelMock).addAttribute("games", games);
	}
	
	@Test
	public void getSportGames_should_get_sport_games_from_games_dao() {
		controller.getSportGames(SPORT_NAME, modelMock);
		
		verify(gameDaoMock).getGamesForSport(SPORT_NAME);
	}

}
