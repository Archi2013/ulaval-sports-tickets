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
import ca.ulaval.glo4003.domain.tickets.Ticket;

@RunWith(MockitoJUnitRunner.class)
public class PersistableGameTest {
	private static final Long AN_ID = 0L;
	private static final String AN_OPPONENT = "Opponent";
	private static final DateTime A_DATE = new DateTime(100);
	private static final String A_SPORT = "sport";
	private static final String ANOTHER_SPORT = "Another sport";

	private List<Ticket> tickets;

	@Mock
	private Ticket baseTicket;
	@Mock
	private Ticket sameTicket;
	@Mock
	private Ticket unassociableTicket;
	@Mock
	private Ticket okayTicket;

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
		when(baseTicket.isSame(sameTicket)).thenReturn(true);
		when(baseTicket.isSame(unassociableTicket)).thenReturn(false);
		when(baseTicket.isSame(okayTicket)).thenReturn(false);
		when(sameTicket.isAssociable()).thenReturn(false);
		when(unassociableTicket.isAssociable()).thenReturn(false);
		when(okayTicket.isAssociable()).thenReturn(true);

		tickets = new ArrayList<>();
		tickets.add(baseTicket);
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
	public void if_ticket_is_not_in_ticket_list_and_can_be_associated_addTickets_adds_the_ticket_to_the_list() {
		gameWithTickets.addTicket(okayTicket);

		Assert.assertEquals(2, tickets.size());
		Assert.assertSame(okayTicket, tickets.get(1));
	}

	@Test
	public void if_ticket_is_already_in_list_addTickets_does_nothing() {
		gameWithTickets.addTicket(sameTicket);

		Assert.assertEquals(1, tickets.size());
	}

	@Test
	public void if_ticket_is_unassociateable_addTickets_does_nothing() {
		gameWithTickets.addTicket(unassociableTicket);

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
