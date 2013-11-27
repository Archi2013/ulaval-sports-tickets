package ca.ulaval.glo4003.domain.tickets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.TicketFactory;

@RunWith(MockitoJUnitRunner.class)
public class TicketFactoryTest {
	private static final boolean AVAILABLE = true;
	private static final String A_SEAT = "seat";
	private static final String A_SECTION = "section";
	private static final double A_PRICE = 12;

	@InjectMocks
	private TicketFactory factory;

	@Before
	public void setup() {

	}

	@Test
	public void instantiation_without_parameter_returns_a_general_ticket() {
		Ticket ticket = factory.createGeneralTicket(A_PRICE, AVAILABLE);

		Assert.assertSame(GeneralTicket.class, ticket.getClass());
	}

	@Test
	public void instantiation_with_seat_and_section_parameters_returns_a_seated_ticket() {
		Ticket ticket = factory.createSeatedTicket(A_SECTION, A_SEAT, A_PRICE, AVAILABLE);

		Assert.assertSame(SeatedTicket.class, ticket.getClass());
	}

	@Test
	public void instantiation_with_section_parameter_to_general_returns_a_general_ticket() {
		Ticket ticket = factory.createGeneralTicket(A_PRICE, AVAILABLE);

		Assert.assertSame(GeneralTicket.class, ticket.getClass());

	}
}
