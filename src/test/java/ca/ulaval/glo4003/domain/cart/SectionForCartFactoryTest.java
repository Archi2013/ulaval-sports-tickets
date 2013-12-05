package ca.ulaval.glo4003.domain.cart;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SectionForCartFactoryTest {

	private static final String A_SPORT = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final String A_SECTION = "Section";
	private static final String AN_OPPONENT = "opponents";
	private static final String A_LOCATION = "location";
	private static final Integer A_NUMBER_OF_TICKETS = 10;
	private static final Double A_PRICE = new Double(20);
	private Set<String> selectedSeats = new HashSet<>();

	@InjectMocks
	private SectionForCartFactory factory;

	@Test
	public void factory_can_instantiate_a_general_section() {
		SectionForCart section = factory.createSectionForGeneralTickets(A_SPORT, A_DATE, A_SECTION, AN_OPPONENT,
				A_LOCATION, A_NUMBER_OF_TICKETS, A_PRICE);
		Assert.assertSame(SectionForCart.class, section.getClass());
	}

	@Test
	public void factory_can_instantiate_a_section_with_seats() {
		SectionForCart section = factory.createSectionForWithSeatTickets(A_SPORT, A_DATE, A_SECTION, AN_OPPONENT,
				A_LOCATION, selectedSeats, A_PRICE);
		Assert.assertSame(SectionForCart.class, section.getClass());
	}

}
