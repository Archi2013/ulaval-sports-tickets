package ca.ulaval.glo4003.domain.pojos.persistable;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.pojos.Ticket;

@RunWith(MockitoJUnitRunner.class)
public class PersistableGameTest {
	private static final Long AN_ID = 0L;
	private static final String AN_OPPONENT = "Opponent";
	private static final DateTime A_DATE = new DateTime(100);
	private static final String A_SPORT = "sport";
	private static final String ANOTHER_SPORT = "Another sport";

	private List<Ticket> tickets;

	@Mock
	private Ticket ticket1;
	@Mock
	private Ticket ticket2;

	PersistableGame fullyInitiatedGame;
	PersistableGame partlyInitiatedGame;
	PersistableGame gameWithTickets;

	@Before
	public void setUp() {
		initializeTickets();

		fullyInitiatedGame = new PersistableGame(AN_ID, AN_OPPONENT, A_DATE, A_SPORT);
		partlyInitiatedGame = new PersistableGame(AN_OPPONENT, A_DATE);
		gameWithTickets = new PersistableGame(AN_ID, AN_OPPONENT, A_DATE, A_SPORT, tickets);

	}

	private void initializeTickets() {
		when(ticket1.isTheSame(ticket1)).thenReturn(true);
		when(ticket1.isTheSame(ticket2)).thenReturn(false);
		when(ticket2.isTheSame(ticket1)).thenReturn(false);
		when(ticket2.isTheSame(ticket2)).thenReturn(true);

		tickets = new ArrayList<>();
		tickets.add(ticket1);
	}

	@Test
	public void acceptsToBeScheduled_return_false_if_the_game_is_scheduled_and_true_if_not() {
		Assert.assertEquals(false, fullyInitiatedGame.acceptsToBeScheduled());
		Assert.assertEquals(true, partlyInitiatedGame.acceptsToBeScheduled());
	}

	@Test
	public void beSecheduledToThisSport_schedules_the_game_if_it_has_not_been_scheduled_before() {
		partlyInitiatedGame.beScheduledToThisSport(A_SPORT);

		Assert.assertEquals(A_SPORT, partlyInitiatedGame.saveDataInDTO().getSportName());
	}

	@Test
	public void beScheduledToThisSport_does_nothing_if_the_game_already_has_a_sport_set() {
		fullyInitiatedGame.beScheduledToThisSport(ANOTHER_SPORT);

		Assert.assertEquals(A_SPORT, fullyInitiatedGame.saveDataInDTO().getSportName());
	}

	@Test
	public void addTickets_adds_the_passed_ticket_to_the_ticket_list() {
		gameWithTickets.addTicket(ticket2);

		Assert.assertEquals(2, tickets.size());
		Assert.assertSame(ticket2, tickets.get(1));
	}

	@Test
	public void addTickets_does_not_add_ticket_if_ticket_is_already_in_ticket_list() {
		gameWithTickets.addTicket(ticket1);

		Assert.assertEquals(1, tickets.size());
	}

	@Test
	public void saveDataInDto_return_a_valid_Dto_if_game_is_fully_initiated() {
		GameDto dto = fullyInitiatedGame.saveDataInDTO();

		Assert.assertEquals(AN_ID, dto.getId());
		Assert.assertEquals(AN_OPPONENT, dto.getOpponents());
		Assert.assertEquals(A_DATE, dto.getGameDate());
		Assert.assertEquals(A_SPORT, dto.getSportName());
	}

	@Test
	public void saveDataInDto_return_a_valid_Dto_if_game_is_partially_initiated() {
		GameDto dto = partlyInitiatedGame.saveDataInDTO();

		Assert.assertEquals(PersistableGame.DEFAULT_ID, dto.getId());
		Assert.assertEquals(AN_OPPONENT, dto.getOpponents());
		Assert.assertEquals(A_DATE, dto.getGameDate());
		Assert.assertEquals(PersistableGame.NO_SPORT_SET, dto.getSportName());
	}
}
