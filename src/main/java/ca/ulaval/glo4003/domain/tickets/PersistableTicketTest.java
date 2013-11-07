package ca.ulaval.glo4003.domain.tickets;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.state.TicketAssociationState;

@RunWith(MockitoJUnitRunner.class)
public class PersistableTicketTest {

	public static final String A_SEAT = "seat";
	public static final String ANOTHER_SEAT = "Another seat";
	public static final String A_SECTION = "section";
	public static final String ANOTHER_SECTION = "Another section";

	@Mock
	TicketAssociationState associationState;

	@Mock
	Ticket otherTicket;

	PersistableTicket ticket;

	@Before
	public void setup() {

		ticket = new PersistableTicket(A_SEAT, A_SECTION, associationState);
	}

	@Test
	public void if_other_ticket_has_same_seat_and_section_isSame_returns_true() {
		when(otherTicket.isSeat(A_SEAT)).thenReturn(true);
		when(otherTicket.isSection(A_SECTION)).thenReturn(true);

		boolean result = ticket.isSame(otherTicket);

		Assert.assertTrue(result);
	}

	@Test
	public void if_other_ticket_has_not_same_seat_and_section_isForSameSeat_returns_false() {
		when(otherTicket.isSeat(A_SEAT)).thenReturn(false);
		when(otherTicket.isSection(A_SECTION)).thenReturn(true);

		boolean result = ticket.isSame(otherTicket);

		Assert.assertFalse(result);
	}

	@Test
	public void isSeat_return_true_if_equals_to_own_seat_and_false_otherwise() {
		Assert.assertTrue(ticket.isSeat(A_SEAT));
		Assert.assertFalse(ticket.isSeat(ANOTHER_SEAT));
	}

	@Test
	public void isSection_returns_true_if_equals_to_own_section_and_false_otherwise() {
		Assert.assertTrue(ticket.isSection(A_SECTION));
		Assert.assertFalse(ticket.isSection(ANOTHER_SECTION));
	}

	@Test
	public void isAssociable_returns_what_the_associationState_returns() {
		when(associationState.isAssociable()).thenReturn(true);

		Assert.assertTrue(ticket.isAssociable());
	}

	@Test
	public void associate_calls_the_associationState() {
		// ticket.associate(sport, date, ticketNumber);
	}
}
