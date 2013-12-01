package ca.ulaval.glo4003.domain.tickets;

import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TicketTest {
	public static final String A_SPORT = "Sport";
	public static final DateTime A_DATE = new DateTime(100);
	public static final int A_TICKET_NUMBER = 120;
	public static final String A_SEAT = "seat";
	public static final String ANOTHER_SEAT = "Another seat";
	public static final String A_SECTION = "section";
	public static final String ANOTHER_SECTION = "Another section";
	public static final long A_PRICE = 25;

	@Mock
	TicketAssignationState firstAssociationState;

	@Mock
	TicketAssignationState secondAssociationState;

	@Mock
	Ticket otherTicket;

	Ticket ticket;

	@Before
	public void setup() {
		// ticket = new TicketImpl(firstAssociationState, A_PRICE);
		ticket = Mockito.mock(Ticket.class, Mockito.CALLS_REAL_METHODS);
		ticket.setAssignationState(firstAssociationState);
	}

	@Test
	public void isAssociable_returns_what_the_associationState_returns() {
		when(firstAssociationState.isAssignable()).thenReturn(true);

		Assert.assertTrue(ticket.isAssignable());
	}

	@Test
	public void associate_calls_the_associationState() {
		ticket.assign(A_SPORT, A_DATE, A_TICKET_NUMBER);

		verify(firstAssociationState).assign(A_SPORT, A_DATE, A_TICKET_NUMBER);
	}

	@Test
	public void new_state_is_stored_after_associate() {
		when(firstAssociationState.assign(A_SPORT, A_DATE, A_TICKET_NUMBER)).thenReturn(secondAssociationState);

		ticket.assign(A_SPORT, A_DATE, A_TICKET_NUMBER);
		ticket.assign(A_SPORT, A_DATE, A_TICKET_NUMBER);

		verify(secondAssociationState).assign(A_SPORT, A_DATE, A_TICKET_NUMBER);
	}

	@Test
	public void makeAvailable_should_make_ticket_Available() {
		ticket.makeAvailable();

		Assert.assertTrue(ticket.isAvailable());
	}

	@Test
	public void makeUnavailable_should_make_ticket_Available() {
		ticket.makeUnavailable();

		Assert.assertFalse(ticket.isAvailable());
	}
}