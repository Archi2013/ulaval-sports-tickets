package ca.ulaval.glo4003.domain.repositories;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.tickets.Ticket;
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
	public void setUp() throws SportDoesntExistException {
		setUpListsOfDtos();

		when(gameDaoMock.get(A_SPORT, A_DATE)).thenReturn(gameDto1);
		when(ticketRepository.recoverAllTicketsForGame(A_SPORT, A_DATE)).thenReturn(ticketList1);
		when(ticketRepository.recoverAllTicketsForGame(A_SPORT, ANOTHER_DATE)).thenReturn(ticketList2);
		when(gameFactoryMock.instantiateGame(AN_OPPONENT, A_DATE, ticketList1)).thenReturn(existingGameMock1);
		when(gameFactoryMock.instantiateGame(ANOTHER_OPPONENT, ANOTHER_DATE, ticketList2))
				.thenReturn(existingGameMock2);
		when(gameFactoryMock.instantiateGame(AN_OPPONENT, A_DATE)).thenReturn(newGameMock1);
		when(gameFactoryMock.instantiateGame(ANOTHER_OPPONENT, ANOTHER_DATE)).thenReturn(newGameMock2);
		when(existingGameMock1.saveDataInDTO()).thenReturn(gameDto1);
		when(existingGameMock2.saveDataInDTO()).thenReturn(gameDto2);
		when(newGameMock1.saveDataInDTO()).thenReturn(gameDto1);
		when(newGameMock2.saveDataInDTO()).thenReturn(gameDto2);
	}

	@Test
	public void if_one_game_is_scheduled_getGamesScheduledForSport_return_one_game() throws SportDoesntExistException {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithOneGameDto);

		List<Game> gamesReturned = gameRepository.recoverAllGamesForSport(A_SPORT);

		Assert.assertEquals(1, gamesReturned.size());
		Assert.assertSame(existingGameMock1, gamesReturned.get(0));
	}

	@Test
	public void if_two_games_are_scheduled_getGamesScheduledForSport_return_two_games()
			throws SportDoesntExistException {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithTwoGameDtos);

		List<Game> gamesReturned = gameRepository.recoverAllGamesForSport(A_SPORT);

		Assert.assertEquals(2, gamesReturned.size());
		Assert.assertSame(existingGameMock1, gamesReturned.get(0));
		Assert.assertSame(existingGameMock2, gamesReturned.get(1));
	}

	@Test
	public void getGame_returns_the_game_with_correct_sport_name_and_date() {
		Game gameReturned = gameRepository.recoverGame(A_SPORT, A_DATE);

		Assert.assertSame(existingGameMock1, gameReturned);
	}

	@Test
	public void game_returned_by_getGame_is_committed_correctly() throws Exception {
		gameRepository.recoverGame(A_SPORT, A_DATE);

		gameRepository.commit();

		verify(gameDaoMock).saveChanges(gameDto1);
	}

	@Test
	public void instantiateNewGame_return_game_instantiated_by_factory() {
		when(gameFactoryMock.instantiateGame(AN_OPPONENT, A_DATE)).thenReturn(existingGameMock1);

		Game gameReturned = gameRepository.instantiateNewGame(AN_OPPONENT, A_DATE);

		Assert.assertSame(gameReturned, existingGameMock1);
	}

	@Test
	public void commit_sends_data_of_every_existing_active_object_to_Dao() throws Exception {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithTwoGameDtos);

		gameRepository.recoverAllGamesForSport(A_SPORT);
		gameRepository.commit();

		verify(gameDaoMock).saveChanges(gameDto1);
		verify(gameDaoMock).saveChanges(gameDto2);
	}

	@Test
	public void commit_sends_data_of_every_new_active_object_to_dao() throws Exception {
		gameRepository.instantiateNewGame(AN_OPPONENT, A_DATE);
		gameRepository.instantiateNewGame(ANOTHER_OPPONENT, ANOTHER_DATE);

		gameRepository.commit();

		verify(gameDaoMock).add(gameDto1);
		verify(gameDaoMock).add(gameDto2);
	}

	@Test
	public void after_a_commit_new_objects_are_considered_as_existing() throws Exception {
		gameRepository.instantiateNewGame(AN_OPPONENT, A_DATE);
		gameRepository.instantiateNewGame(ANOTHER_OPPONENT, ANOTHER_DATE);

		gameRepository.commit();
		gameRepository.commit();

		verify(gameDaoMock, times(1)).add(gameDto1);
		verify(gameDaoMock, times(1)).add(gameDto2);
		verify(gameDaoMock, times(1)).saveChanges(gameDto1);
		verify(gameDaoMock, times(1)).saveChanges(gameDto2);
	}

	@Test
	public void if_one_object_is_new_and_one_is_not_committing_twice_works_fine() throws Exception {
		when(gameDaoMock.getGamesForSport(A_SPORT)).thenReturn(listWithOneGameDto);
		gameRepository.recoverAllGamesForSport(A_SPORT);
		gameRepository.instantiateNewGame(ANOTHER_OPPONENT, ANOTHER_DATE);

		gameRepository.commit();
		gameRepository.commit();

		verify(gameDaoMock, times(2)).saveChanges(gameDto1);
		verify(gameDaoMock, times(1)).add(gameDto2);
		verify(gameDaoMock, times(1)).add(gameDto2);

	}

	@Test
	public void commit_asks_ticketRepository_to_commit() throws Exception {
		gameRepository.commit();

		verify(ticketRepository).commit();
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
