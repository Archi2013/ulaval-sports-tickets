package ca.ulaval.glo4003.domain.pojos.persistable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
import ca.ulaval.glo4003.domain.game.GameScheduleState;
import ca.ulaval.glo4003.domain.tickets.Ticket;

@RunWith(MockitoJUnitRunner.class)
public class PersistableGameTest {
	private static final Long AN_ID = 0L;
	private static final String AN_OPPONENT = "Opponent";
	private static final String A_SPORT = "sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final String A_LOCATION = "Stade Telus";

	private List<Ticket> tickets;

	@Mock
	private Ticket baseTicket;
	@Mock
	private Ticket sameTicket;
	@Mock
	private Ticket unassignableTicket;
	@Mock
	private Ticket okayTicket;
	@Mock
	private GameScheduleState assignationState;

	PersistableGame gameWithTickets;

	@Before
	public void setUp() {
		initializeTickets();
		gameWithTickets = new PersistableGame(AN_ID, AN_OPPONENT, A_LOCATION, assignationState, tickets);

	}

	@Test
	public void acceptsToBeScheduled_return_the_value_returned_by_the_assignementState() {
		when(assignationState.isSchedulable()).thenReturn(true);
		Assert.assertEquals(true, gameWithTickets.acceptsToBeScheduled());
		when(assignationState.isSchedulable()).thenReturn(false);
		Assert.assertEquals(false, gameWithTickets.acceptsToBeScheduled());
	}

	@Test
	public void if_game_is_assignable_then_beSecheduledToThisSport_assigns_the_game_with_the_state() {
		when(assignationState.isSchedulable()).thenReturn(true);
		gameWithTickets.beScheduledToThisSport(A_SPORT, A_DATE);

		verify(assignationState).assign(A_SPORT, A_DATE);
	}

	@Test
	public void if_game_is_not_assignable_then_beScheduledToThisSport_does_nothing() {
		when(assignationState.isSchedulable()).thenReturn(false);
		gameWithTickets.beScheduledToThisSport(A_SPORT, A_DATE);

		verify(assignationState, never()).assign(A_SPORT, A_DATE);
	}

	@Test
	public void if_ticket_is_not_in_ticket_list_and_can_be_associated_addTickets_adds_the_ticket_to_the_list() {
		gameWithTickets.addTicket(okayTicket);

		Assert.assertEquals(2, tickets.size());
		Assert.assertSame(okayTicket, tickets.get(1));
	}

	@Test
	public void when_added_to_the_list_a_ticket_is_assigned_to_the_game_schedule() {
		gameWithTickets.addTicket(okayTicket);

		verify(assignationState).assignThisTicketToSchedule(okayTicket, tickets.size() - 1);
	}

	@Test
	public void if_ticket_is_already_in_list_addTickets_does_nothing() {
		gameWithTickets.addTicket(sameTicket);

		Assert.assertEquals(1, tickets.size());
	}

	@Test
	public void if_ticket_is_unassignable_addTickets_does_nothing() {
		gameWithTickets.addTicket(unassignableTicket);

		Assert.assertEquals(1, tickets.size());
	}

	@Test
	public void saveDataInDto_ask_schedule_to_fill_schedule_data() {
		GameDto dto = gameWithTickets.saveDataInDTO();

		verify(assignationState).saveTheScheduleInThisDto(dto);
	}

	@Test
	public void game_saves_own_data_in_dto_when_saveDataInDto_is_called() {
		GameDto dto = gameWithTickets.saveDataInDTO();

		Assert.assertEquals(AN_OPPONENT, dto.getOpponents());
		Assert.assertEquals(A_LOCATION, dto.getLocation());
	}

	private void initializeTickets() {
		when(baseTicket.isSame(sameTicket)).thenReturn(true);
		when(baseTicket.isSame(unassignableTicket)).thenReturn(false);
		when(baseTicket.isSame(okayTicket)).thenReturn(false);
		when(sameTicket.isAssignable()).thenReturn(false);
		when(unassignableTicket.isAssignable()).thenReturn(false);
		when(okayTicket.isAssignable()).thenReturn(true);

		tickets = new ArrayList<>();
		tickets.add(baseTicket);
	}

}
