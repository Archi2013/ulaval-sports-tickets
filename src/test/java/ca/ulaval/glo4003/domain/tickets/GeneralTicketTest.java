package ca.ulaval.glo4003.domain.tickets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.constants.TicketKind;

@RunWith(MockitoJUnitRunner.class)
public class GeneralTicketTest {
	private static final String A_SEAT = "seat";
	private static final double A_PRICE = 1478;
	@Mock
	private TicketAssignationState assignationState;

	@Mock
	private Ticket otherTicket;

	private GeneralTicket ticket;

	@Before
	public void setup() {
		ticket = new GeneralTicket(A_PRICE, assignationState);
	}

	@Test
	public void a_general_ticket_is_never_the_same_of_another() {
		Assert.assertFalse(ticket.isSame(otherTicket));
	}

	@Test
	public void a_general_ticket_section_is_always_the_same() {
		Assert.assertTrue(ticket.hasSection(TicketKind.GENERAL_ADMISSION.toString()));
	}

	@Test
	public void a_general_ticket_never_has_the_same_seat() {
		Assert.assertFalse(ticket.hasSeat(A_SEAT));
	}

	@Test
	public void saveDataInDto_adds_seats_and_section_to_dto_filled_by_superclass() {
		TicketDto data = ticket.saveDataInDTO();

		Assert.assertEquals(A_PRICE, data.price, 1);
	}

}
