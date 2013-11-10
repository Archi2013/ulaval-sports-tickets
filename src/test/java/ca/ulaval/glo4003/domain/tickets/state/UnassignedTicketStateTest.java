package ca.ulaval.glo4003.domain.tickets.state;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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
}