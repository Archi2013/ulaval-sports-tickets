package ca.ulaval.glo4003.domain.tickets;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;

@RunWith(MockitoJUnitRunner.class)
public class PersistableTicketTest {
	public static final String A_SPORT = "Sport";
	public static final DateTime A_DATE = new DateTime(100);
	public static final int A_TICKET_NUMBER = 120;
	public static final String A_SEAT = "seat";
	public static final String ANOTHER_SEAT = "Another seat";
	public static final String A_SECTION = "section";
	public static final String ANOTHER_SECTION = "Another section";

	@Mock
	TicketAssignationState firstAssociationState;

	@Mock
	TicketAssignationState secondAssociationState;

	@Mock
	Ticket otherTicket;

	PersistableTicket ticket;

	@Before
	public void setup() {

		ticket = new PersistableTicketImpl(firstAssociationState);
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

	private class PersistableTicketImpl extends PersistableTicket {

		public PersistableTicketImpl(TicketAssignationState associationState) {
			super(associationState);

		}

		@Override
		public boolean isSame(Ticket ticketToAdd) {
			return false;
		}

		@Override
		public boolean isSeat(String seat) {
			return false;
		}

		@Override
		public boolean isSection(String section) {
			return false;
		}
	}
}