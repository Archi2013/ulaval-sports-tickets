package ca.ulaval.glo4003.domain.game;

import static org.mockito.Mockito.*;

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

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.domain.tickets.TicketRepository;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;

@RunWith(MockitoJUnitRunner.class)
public class GameRepositoryTest {

	private static final String A_SPORT = "Sport";
	private static final String AN_OPPONENT = "Opponent";
	private static final String ANOTHER_OPPONENT = "Another";
	private static final String A_LOCATION = "Stade Telus";
	private static final String ANOTHER_LOCATION = "La Lune";
	private static final DateTime A_DATE = new DateTime(100);
	private static final DateTime ANOTHER_DATE = new DateTime(125);
	GameDto gameDto1;
	GameDto gameDto2;
	List<GameDto> listWithOneGameDto;
	List<GameDto> listWithTwoGameDtos;
	List<PersistableGame> listGame;
	List<Ticket> ticketList1 = new ArrayList<>();
	List<Ticket> ticketList2 = new ArrayList<>();

	@Mock
	PersistableGame existingGameMock1;

	@Mock
	PersistableGame existingGameMock2;

	@Mock
	PersistableGame newGameMock1;

	@Mock
	PersistableGame newGameMock2;

	@Mock
	GameDao gameDaoMock;

	@Mock
	IGameFactory gameFactoryMock;

	@Mock
	TicketRepository ticketRepository;

	@InjectMocks
	GameRepository gameRepository;

	@Before
	public void setUp() throws SportDoesntExistException, GameDoesntExistException {
		setUpListsOfDtos();

		when(gameDaoMock.get(A_SPORT, A_DATE)).thenReturn(gameDto1);
		when(ticketRepository.getAll(A_SPORT, A_DATE)).thenReturn(ticketList1);
		when(ticketRepository.getAll(A_SPORT, ANOTHER_DATE)).thenReturn(ticketList2);
		when(gameFactoryMock.instantiateGame(gameDto1, ticketList1)).thenReturn(existingGameMock1);
		when(gameFactoryMock.instantiateGame(gameDto2, ticketList2)).thenReturn(existingGameMock2);
		when(gameFactoryMock.instantiateGame(AN_OPPONENT, A_LOCATION)).thenReturn(newGameMock1);
		when(gameFactoryMock.instantiateGame(ANOTHER_OPPONENT, ANOTHER_LOCATION)).thenReturn(newGameMock2);
		when(existingGameMock1.saveDataInDTO()).thenReturn(gameDto1);
		when(existingGameMock2.saveDataInDTO()).thenReturn(gameDto2);
		when(newGameMock1.saveDataInDTO()).thenReturn(gameDto1);
		when(newGameMock2.saveDataInDTO()).thenReturn(gameDto2);
	}

	@Test
	public void if_one_game_is_scheduled_getGamesScheduledForSport_return_one_game() throws SportDoesntExistException {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithOneGameDto);

		List<Game> gamesReturned = gameRepository.getAll(A_SPORT);

		Assert.assertEquals(1, gamesReturned.size());
		Assert.assertSame(existingGameMock1, gamesReturned.get(0));
	}

	@Test
	public void if_two_games_are_scheduled_getGamesScheduledForSport_return_two_games()
			throws SportDoesntExistException {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithTwoGameDtos);

		List<Game> gamesReturned = gameRepository.getAll(A_SPORT);

		Assert.assertEquals(2, gamesReturned.size());
		Assert.assertSame(existingGameMock1, gamesReturned.get(0));
		Assert.assertSame(existingGameMock2, gamesReturned.get(1));
	}

	@Test
	public void getGame_returns_the_game_with_correct_sport_name_and_date() throws GameDoesntExistException {
		Game gameReturned = gameRepository.get(A_SPORT, A_DATE);

		Assert.assertSame(existingGameMock1, gameReturned);
	}

	@Test
	public void game_returned_by_getGame_is_committed_correctly() throws Exception {
		gameRepository.get(A_SPORT, A_DATE);

		gameRepository.commit();

		verify(gameDaoMock).update(gameDto1);
		verify(gameDaoMock).commit();
	}

	@Test
	public void instantiateNewGame_return_game_instantiated_by_factory() {
		when(gameFactoryMock.instantiateGame(AN_OPPONENT, A_LOCATION)).thenReturn(existingGameMock1);

		Game gameReturned = gameRepository.create(AN_OPPONENT, A_LOCATION);

		Assert.assertSame(gameReturned, existingGameMock1);
	}

	@Test
	public void commit_sends_data_of_every_existing_active_object_to_Dao() throws Exception {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithTwoGameDtos);

		gameRepository.getAll(A_SPORT);
		gameRepository.commit();

		verify(gameDaoMock).update(gameDto1);
		verify(gameDaoMock).update(gameDto2);
		verify(gameDaoMock).commit();
	}

	@Test
	public void commit_sends_data_of_every_new_active_object_to_dao() throws Exception {
		gameRepository.create(AN_OPPONENT, A_LOCATION);
		gameRepository.create(ANOTHER_OPPONENT, ANOTHER_LOCATION);

		gameRepository.commit();

		verify(gameDaoMock).add(gameDto1);
		verify(gameDaoMock).add(gameDto2);
	}

	@Test
	public void after_a_commit_new_objects_are_considered_as_existing() throws Exception {
		gameRepository.create(AN_OPPONENT, A_LOCATION);
		gameRepository.create(ANOTHER_OPPONENT, ANOTHER_LOCATION);

		gameRepository.commit();
		gameRepository.commit();

		verify(gameDaoMock, times(1)).add(gameDto1);
		verify(gameDaoMock, times(1)).add(gameDto2);
		verify(gameDaoMock, times(2)).commit();
	}

	@Test
	public void if_one_object_is_new_and_one_is_not_committing_twice_works_fine() throws Exception {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithOneGameDto);
		gameRepository.getAll(A_SPORT);
		gameRepository.create(ANOTHER_OPPONENT, ANOTHER_LOCATION);

		gameRepository.commit();
		gameRepository.commit();

		verify(gameDaoMock, times(2)).commit();
		verify(gameDaoMock, times(1)).add(gameDto2);
		verify(gameDaoMock, times(1)).add(gameDto2);

	}

	@Test
	public void commit_asks_ticketRepository_to_commit() throws Exception {
		gameRepository.commit();

		verify(ticketRepository).commit();
	}

	private void setUpListsOfDtos() {
		gameDto1 = new GameDto(AN_OPPONENT, A_DATE, A_SPORT, A_LOCATION);
		gameDto2 = new GameDto(ANOTHER_OPPONENT, ANOTHER_DATE, A_SPORT, A_LOCATION);
		listWithOneGameDto = new ArrayList<>();
		listWithOneGameDto.add(gameDto1);
		listWithTwoGameDtos = new ArrayList<>();
		listWithTwoGameDtos.add(gameDto1);
		listWithTwoGameDtos.add(gameDto2);
	}
}
