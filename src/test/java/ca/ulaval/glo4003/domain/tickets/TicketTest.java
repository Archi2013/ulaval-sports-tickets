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

	@Mock
	TicketAssignationState firstAssociationState;

	@Mock
	TicketAssignationState secondAssociationState;

	@Mock
	Ticket otherTicket;

	Ticket ticket;

	@Before
	public void setup() {

		ticket = new TicketImpl(firstAssociationState, A_PRICE);
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
	public void saveDataInDto_stores_own_data_in_dto() {
		TicketDto data = ticket.saveDataInDTO();

		Assert.assertTrue(data.available);
		Assert.assertEquals(A_PRICE, data.price, 1);
	}

	@Test
	public void saveDataInDto_asks_assignationState_to_fill_its_share() {
		ticket.saveDataInDTO();

		verify(firstAssociationState).fillDataInDto(any(TicketDto.class));
	}

	private class TicketImpl extends Ticket {

		public TicketImpl(TicketAssignationState associationState, double price) {
			super(associationState, price);

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