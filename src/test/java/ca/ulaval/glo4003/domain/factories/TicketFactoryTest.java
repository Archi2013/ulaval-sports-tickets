package ca.ulaval.glo4003.domain.factories;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.GeneralTicket;
import ca.ulaval.glo4003.domain.tickets.PersistableTicket;
import ca.ulaval.glo4003.domain.tickets.SeatedTicket;

@RunWith(MockitoJUnitRunner.class)
public class TicketFactoryTest {
	private static final String A_SEAT = "seat";
	private static final String A_SECTION = "section";

	@InjectMocks
	private TicketFactory factory;

	@Before
	public void setup() {

	}

	@Test
	public void instantiation_without_parameter_returns_a_general_ticket() {
		PersistableTicket ticket = factory.instantiateTicket();

		Assert.assertSame(GeneralTicket.class, ticket.getClass());
	}

	@Test
	public void instantiation_with_seat_and_section_parameters_returns_a_seated_ticket() {
		PersistableTicket ticket = factory.instantiateTicket(A_SEAT, A_SECTION);

		Assert.assertSame(SeatedTicket.class, ticket.getClass());
	}

	@Test
	public void instantiation_with_section_parameter_to_general_returns_a_general_ticket() {
		PersistableTicket ticket = factory.instantiateTicket();

		Assert.assertSame(GeneralTicket.class, ticket.getClass());

	}
}
