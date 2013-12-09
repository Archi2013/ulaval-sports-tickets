package ca.ulaval.glo4003.domain.game;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.game.dto.GameDto;

@RunWith(MockitoJUnitRunner.class)
public class UnscheduledStateTest {
	private static final String A_SPORT = "sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final int A_NUMBER = 45;

	@Mock
	private GameDto dto;

	@Mock
	private Ticket ticket;

	@InjectMocks
	private UnscheduledState state;

	@Test
	public void UnscheduledState_is_assignable() {
		Assert.assertTrue(state.isSchedulable());
	}

	@Test
	public void once_assigned_UnscheduledState_become_ScheduledState() {
		GameScheduleState newState = state.assign(A_SPORT, A_DATE);

		Assert.assertSame(ScheduledState.class, newState.getClass());
	}

	@Test
	public void a_unscheduled_game_cannot_receive_tickets() {
		state.assignThisTicketToSchedule(ticket, A_NUMBER);

		verify(ticket, never()).assign(A_SPORT, A_DATE, A_NUMBER);
	}

	@Test
	public void a_unscheduled_game_cannot_be_saved() {
		state.saveTheScheduleInThisDto(dto);

		verify(dto, never()).setSportName(A_SPORT);
		verify(dto, never()).setGameDate(A_DATE);
		verify(dto, never()).setNumberOfTickets(A_NUMBER);
		;
	}
}
