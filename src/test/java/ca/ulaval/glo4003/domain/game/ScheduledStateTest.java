package ca.ulaval.glo4003.domain.game;

import static org.mockito.Mockito.verify;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.Ticket;

@RunWith(MockitoJUnitRunner.class)
public class ScheduledStateTest {
	private static final String A_SPORT = "sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final int A_TICKET_NUMBER = 120;

	@Mock
	private GameDto dto;

	@Mock
	private Ticket ticket;

	ScheduledState state;

	@Before
	public void setUp() {
		state = new ScheduledState(A_SPORT, A_DATE);
	}

	@Test
	public void a_scheduled_game_is_not_schedulable() {
		Assert.assertFalse(state.isSchedulable());
	}

	@Test
	public void if_a_scheduled_game_gets_scheduled_the_state_does_not_change() {
		GameScheduleState newState = state.assign(A_SPORT, A_DATE);

		Assert.assertSame(state, newState);
	}

	@Test
	public void a_scheduled_game_assigns_its_data_to_a_assigned_ticket() {
		state.assignThisTicketToSchedule(ticket, A_TICKET_NUMBER);

		verify(ticket).assign(A_SPORT, A_DATE, A_TICKET_NUMBER);
	}

	@Test
	public void a_scheduled_game_save_its_data() {
		state.saveTheScheduleInThisDto(dto);

		verify(dto).setSportName(A_SPORT);
		verify(dto).setGameDate(A_DATE);
	}
}
