package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.AssignedTicketState;
import ca.ulaval.glo4003.domain.tickets.TicketAssignationState;
import ca.ulaval.glo4003.domain.tickets.UnassignedTicketState;
import ca.ulaval.glo4003.tickets.dto.SeatedTicketDto;
import ca.ulaval.glo4003.tickets.dto.TicketDto;

@RunWith(MockitoJUnitRunner.class)
public class UnassignedTicketStateTest {
	private static final String A_SPORT = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final int A_TICKET_NUMBER = 99;
	UnassignedTicketState state;

	@Before
	public void setup() {
		state = new UnassignedTicketState();
	}

	@Test
	public void An_unassigned_ticket_can_be_assigned() {
		Assert.assertTrue(state.isAssignable());
	}

	@Test
	public void assign_returns_an_AssignTicketState() {
		TicketAssignationState stateReturned = state.assign(A_SPORT, A_DATE, A_TICKET_NUMBER);

		Assert.assertSame(stateReturned.getClass(), AssignedTicketState.class);
	}

	@Test
	public void fillDataInDto_fills_no_data() {
		TicketDto data = new SeatedTicketDto(0L, null, null, null, null, 0, false);

		state.fillDataInDto(data);

		Assert.assertSame(null, data.sportName);
		Assert.assertSame(null, data.gameDate);
		Assert.assertEquals(Long.valueOf(0L), data.ticketId);
	}
}
