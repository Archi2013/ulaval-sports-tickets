package ca.ulaval.glo4003.domain.tickets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;

@RunWith(MockitoJUnitRunner.class)
public class GeneralTicketTest {
	private static final String A_SEAT = "seat";
	@Mock
	private TicketAssignationState assignationState;

	@Mock
	private Ticket otherTicket;

	@InjectMocks
	private GeneralTicket ticket;

	@Before
	public void setup() {
	}

	@Test
	public void a_general_ticket_is_never_the_same_of_another() {
		Assert.assertFalse(ticket.isSame(otherTicket));
	}

	@Test
	public void a_general_ticket_section_is_always_the_same() {
		Assert.assertTrue(ticket.isSection(TicketKind.GENERAL_ADMISSION.toString()));
	}

	@Test
	public void a_general_ticket_never_has_the_same_seat() {
		Assert.assertFalse(ticket.isSeat(A_SEAT));
	}

}