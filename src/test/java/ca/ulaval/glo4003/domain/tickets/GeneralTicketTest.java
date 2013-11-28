package ca.ulaval.glo4003.domain.tickets;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

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
	private static final boolean AN_AVAILABILITY = false;
	@Mock
	private TicketAssignationState assignationState;

	@Mock
	private Ticket otherTicket;

	private GeneralTicket ticket;

	@Before
	public void setup() {
		ticket = new GeneralTicket(A_PRICE, AN_AVAILABILITY, assignationState);
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
	public void saveDataInDto_returns_a_GeneralTicketDTO() {
		TicketDto data = ticket.saveDataInDTO();

		Assert.assertSame(GeneralTicketDto.class, data.getClass());
	}

	@Test
	public void data_returned_by_saveDataInDto_has_correct_price_and_availability() {
		TicketDto data = ticket.saveDataInDTO();

		Assert.assertEquals(A_PRICE, data.price, 1);
		Assert.assertEquals(AN_AVAILABILITY, data.available);
	}

	@Test
	public void saveDataInDto_asks_assignation_state_to_fill_its_share() {
		ticket.saveDataInDTO();

		verify(assignationState).fillDataInDto(any(TicketDto.class));
	}
}
