package ca.ulaval.glo4003.domain.tickets;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.domain.tickets.TicketAssignationState;
import ca.ulaval.glo4003.domain.tickets.TicketDto;

@Ignore
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
	public static final boolean AN_AVAILABILITY = false;

	@Mock
	TicketAssignationState firstAssociationState;

	@Mock
	TicketAssignationState secondAssociationState;

	@Mock
	Ticket otherTicket;

	Ticket ticket;

	@Before
	public void setup() {

		ticket = new TicketImpl(firstAssociationState, A_PRICE, AN_AVAILABILITY);
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
	public void isAvailable_returns_current_state_of_availability() {
		boolean availability = ticket.isAvailable();
		
		Assert.assertEquals(AN_AVAILABILITY, availability);
	}
	public void makeAvailable_should_make_ticket_Available() {
		ticket.makeAvailable();
		Assert.assertTrue(ticket.isAvailable());
	}

	@Test
	public void makeAvailable_makes_the_ticket_available() {
		ticket.makeUnavailable();
		ticket.makeAvailable();
		
		Assert.assertTrue(ticket.isAvailable());
	}

	public void makeUnavailable_should_make_ticket_Available() {
		ticket.makeAvailable();
		ticket.makeUnavailable();

		Assert.assertFalse(ticket.isAvailable());
	}

	@Test
	public void makeUnavailable_makes_the_ticket_unavailable() {
		ticket.makeAvailable();
		ticket.makeUnavailable();
		boolean availability = ticket.isAvailable();

		Assert.assertFalse(availability);
	}

	private class TicketImpl extends Ticket {

		public TicketImpl(TicketAssignationState associationState, double price, boolean available) {
			super(associationState, price, available);

		}

		@Override
		public boolean isSame(Ticket ticketToAdd) {
			return false;
		}

		@Override
		public boolean hasSeat(String seat) {
			return false;
		}

		@Override
		public boolean hasSection(String section) {
			return false;
		}

		@Override
		public TicketDto saveDataInDTO() {
			return null;
		}
	}
}
