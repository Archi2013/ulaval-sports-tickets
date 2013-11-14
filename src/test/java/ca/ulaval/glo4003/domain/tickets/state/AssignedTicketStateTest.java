package ca.ulaval.glo4003.domain.tickets.state;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.TicketDto;

@RunWith(MockitoJUnitRunner.class)
public class AssignedTicketStateTest {
	public static final String A_SPORT = "Sport";
	public static final DateTime A_DATE = new DateTime(100);
	public static final int A_TICKET_NUMBER = 86;
	AssignedTicketState state;

	@Before
	public void setup() {
		state = new AssignedTicketState(A_SPORT, A_DATE, A_TICKET_NUMBER);
	}

	@Test
	public void An_assigned_ticket_cannot_be_assigned_again() {
		Assert.assertFalse(state.isAssignable());
	}

	@Test
	public void assign_returns_the_current_state() {
		TicketAssignationState stateReturned = state.assign(A_SPORT, A_DATE, A_TICKET_NUMBER);

		Assert.assertSame(stateReturned, state);
	}

	@Test
	public void fillDataInDto_places_data_in_dto() {
		TicketDto data = new TicketDto(null, null, 0, 0, null, null, false);
		state.fillDataInDto(data);

		Assert.assertSame(A_SPORT, data.sportName);
		Assert.assertSame(A_DATE, data.gameDate);
		Assert.assertEquals(A_TICKET_NUMBER, data.ticketNumber);
	}
}
