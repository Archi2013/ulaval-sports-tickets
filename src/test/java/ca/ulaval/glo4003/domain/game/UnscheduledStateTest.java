package ca.ulaval.glo4003.domain.game;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.tickets.Ticket;

@RunWith(MockitoJUnitRunner.class)
public class UnscheduledStateTest {
	private static final String A_SPORT = "sport";
	private static final DateTime A_DATE = new DateTime(100);

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
	}

	public void a_unscheduled_game_cannot_receive_tickets() {
	}

	public void a_unscheduled_game_cannot_be_saved() {
		state.saveTheScheduleInThisDto(dto);
	}
}
