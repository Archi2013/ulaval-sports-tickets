package ca.ulaval.glo4003.domain.repositories;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.factories.IGameFactory;
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

@RunWith(MockitoJUnitRunner.class)
public class GameRepositoryTest {

	private static final String A_SPORT = "Sport";
	private static final int AN_ID = 1234;
	private static final int ANOTHER_ID = 2345;
	private static final String AN_OPPONENT = "Opponent";
	private static final String ANOTHER_OPPONENT = "Another";
	private static final DateTime A_DATE = new DateTime(100);
	private static final DateTime ANOTHER_DATE = new DateTime(125);
	GameDto gameDto1;
	GameDto gameDto2;
	List<GameDto> listWithOneGameDto;
	List<GameDto> listWithTwoGameDtos;
	List<Game> listGame;

	Game game1;
	Game game2;

	@Mock
	GameDao gameDaoMock;

	@Mock
	IGameFactory gameFactoryMock;

	@InjectMocks
	GameRepository gameRepository;

	@Before
	public void setUp() throws SportDoesntExistException {
		setUpListsOfDtos();
		game1 = new Game(AN_OPPONENT, A_DATE);
		game2 = new Game(ANOTHER_OPPONENT, ANOTHER_DATE);

		when(gameFactoryMock.instantiateGame(AN_OPPONENT, A_DATE)).thenReturn(game1);
		when(gameFactoryMock.instantiateGame(ANOTHER_OPPONENT, ANOTHER_DATE)).thenReturn(game2);
	}

	@Test
	public void if_one_game_is_scheduled_getGamesScheduledForSport_return_one_game() throws SportDoesntExistException {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithOneGameDto);

		List<Game> gamesReturned = gameRepository.getGamesScheduledForSport(A_SPORT);

		Assert.assertEquals(1, gamesReturned.size());
		Assert.assertSame(game1, gamesReturned.get(0));
	}

	@Test
	public void if_two_games_are_scheduled_getGamesScheduledForSport_return_two_games()
			throws SportDoesntExistException {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithTwoGameDtos);

		List<Game> gamesReturned = gameRepository.getGamesScheduledForSport(A_SPORT);

		Assert.assertEquals(2, gamesReturned.size());
		Assert.assertSame(game1, gamesReturned.get(0));
		Assert.assertSame(game2, gamesReturned.get(1));
	}

	private void setUpListsOfDtos() {
		gameDto1 = new GameDto(AN_ID, AN_OPPONENT, A_DATE, A_SPORT);
		gameDto2 = new GameDto(ANOTHER_ID, ANOTHER_OPPONENT, ANOTHER_DATE, A_SPORT);
		listWithOneGameDto = new ArrayList<>();
		listWithOneGameDto.add(gameDto1);
		listWithTwoGameDtos = new ArrayList<>();
		listWithTwoGameDtos.add(gameDto1);
		listWithTwoGameDtos.add(gameDto2);
	}
}
