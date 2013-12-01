package ca.ulaval.glo4003.domain.tickets;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SeatedTicketTest {
	public static final String A_SPORT = "Sport";
	public static final DateTime A_DATE = new DateTime(100);
	public static final int A_TICKET_NUMBER = 120;
	public static final String A_SEAT = "seat";
	public static final String ANOTHER_SEAT = "Another seat";
	public static final String A_SECTION = "section";
	public static final String ANOTHER_SECTION = "Another section";
	public static final double A_PRICE = 145;
	public static final boolean AN_AVAILABILITY = false;

	@Mock
	TicketAssignationState firstAssignationState;

	@Mock
	Ticket otherTicket;

	SeatedTicket ticket;

	@Before
	public void setup() {

		ticket = new SeatedTicket(A_SEAT, A_SECTION, A_PRICE, AN_AVAILABILITY, firstAssignationState);
	}

	@Test
	public void if_other_ticket_has_same_seat_and_section_isSame_returns_true() {
		when(otherTicket.hasSeat(A_SEAT)).thenReturn(true);
		when(otherTicket.hasSection(A_SECTION)).thenReturn(true);

		boolean result = ticket.isSame(otherTicket);

		Assert.assertTrue(result);
	}

	@Test
	public void if_other_ticket_has_not_same_seat_and_section_isForSameSeat_returns_false() {
		when(otherTicket.hasSeat(A_SEAT)).thenReturn(false);
		when(otherTicket.hasSection(A_SECTION)).thenReturn(true);

		boolean result = ticket.isSame(otherTicket);

		Assert.assertFalse(result);
	}

	@Test
	public void if_other_ticket_has_same_seat_but_not_same_section_isForSameSeat_returns_false() {
		when(otherTicket.hasSeat(A_SEAT)).thenReturn(true);
		when(otherTicket.hasSection(A_SECTION)).thenReturn(false);

		boolean result = ticket.isSame(otherTicket);

		Assert.assertFalse(result);
	}

	@Test
	public void isSeat_return_true_if_equals_to_own_seat_and_false_otherwise() {
		Assert.assertTrue(ticket.hasSeat(A_SEAT));
		Assert.assertFalse(ticket.hasSeat(ANOTHER_SEAT));
	}

	@Test
	public void isSection_returns_true_if_equals_to_own_section_and_false_otherwise() {
		Assert.assertTrue(ticket.hasSection(A_SECTION));
		Assert.assertFalse(ticket.hasSection(ANOTHER_SECTION));
	}

	@Test
	public void saveDataInDto_adds_seats_and_section_to_dto_filled_by_superclass() {
		TicketDto data = ticket.saveDataInDTO();

		Assert.assertEquals(A_PRICE, data.price, 1);
		Assert.assertEquals(A_SEAT, data.seat);
		Assert.assertEquals(A_SECTION, data.section);
	}

	@Test
	public void saveDataInDto_returns_a_GeneralTicketDTO() {
		TicketDto data = ticket.saveDataInDTO();

		Assert.assertSame(SeatedTicketDto.class, data.getClass());
	}

	@Test
	public void data_returned_by_saveDataInDto_has_correct_price_and_availability() {
		TicketDto data = ticket.saveDataInDTO();

		Assert.assertEquals(A_PRICE, data.price, 1);
		Assert.assertEquals(AN_AVAILABILITY, data.available);
		Assert.assertEquals(A_SEAT, data.seat);
		Assert.assertEquals(A_SECTION, data.section);
	}

	@Test
	public void saveDataInDto_asks_assignation_state_to_fill_its_share() {
		ticket.saveDataInDTO();

		verify(firstAssignationState).fillDataInDto(any(TicketDto.class));
	}
}
