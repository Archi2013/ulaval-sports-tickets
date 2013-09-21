package ca.ulaval.glo4003.controllers;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.data_access.GameDao;
import ca.ulaval.glo4003.data_access.SportDao;

@RunWith(MockitoJUnitRunner.class)
public class SportsControllerTest {

	private static final String SPORT_NAME = "SPORT_NAME";

	@Mock
	private SportDao sportDao;
	
	@Mock
	private GameDao gameDao;

	@InjectMocks
	private SportController controller;

	@Before
	public void setUp() {

	}

	@Test
	public void getSports_should_get_sports_from_dao() {
		controller.getSports();

		verify(sportDao).getAll();
	}
	
	@Test
	public void getSport_should_get_sport_with_right_name_from_dao(){
		controller.getSport(SPORT_NAME);
		
		verify(sportDao).get(SPORT_NAME);
	}
	
	@Test
	public void getSportGames_should_get_sport_games_from_games_dao(){
		controller.getSportGames(SPORT_NAME);
		
		verify(gameDao).getGamesForSport(SPORT_NAME);
	}

}
