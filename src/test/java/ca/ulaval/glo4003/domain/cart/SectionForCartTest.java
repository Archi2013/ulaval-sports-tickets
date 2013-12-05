package ca.ulaval.glo4003.domain.cart;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SectionForCartTest {
	private static final String A_SPORT = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final String A_SECTION = "Section";
	private static final String AN_OPPONENT = "opponents";
	private static final String A_LOCATION = "location";
	private static final Integer A_NUMBER_OF_TICKETS = 10;
	private static final Double A_PRICE = new Double(20);
	private static final String A_SEAT = "Seat";
	private static final String ANOTHER_SEAT = "AnotherSeat";

	private Set<String> emptySelectedSeats;
	private Set<String> selectedSeats;
	private SectionForCart generalSection;
	private SectionForCart emptySeatedSection;
	private SectionForCart seatedSection;

	@Before
	public void setUp() {
		emptySelectedSeats = new HashSet<String>();
		selectedSeats = new HashSet<String>();
		selectedSeats.add(A_SEAT);

		generalSection = new SectionForCart(A_SPORT, A_DATE, A_SECTION, AN_OPPONENT, A_LOCATION, A_NUMBER_OF_TICKETS,
				A_PRICE);
		emptySeatedSection = new SectionForCart(A_SPORT, A_DATE, A_SECTION, AN_OPPONENT, A_LOCATION,
				emptySelectedSeats, A_PRICE);
		seatedSection = new SectionForCart(A_SPORT, A_DATE, A_SECTION, AN_OPPONENT, A_LOCATION, selectedSeats, A_PRICE);
	}

	@Test
	public void when_two_general_sections_are_added_up_they_sum_their_number_of_tickets() {
		generalSection.addElements(generalSection);

		Assert.assertEquals(2 * A_NUMBER_OF_TICKETS, (int) generalSection.getNumberOfTicketsToBuy());
	}

	@Test
	public void when_two_seated_sections_are_added_up_they_sum_the_length_of_their_ticket_list() {
		int expectedSize = emptySelectedSeats.size() + selectedSeats.size();
		emptySeatedSection.addElements(seatedSection);

		Assert.assertEquals(expectedSize, (int) emptySeatedSection.getNumberOfTicketsToBuy());
	}

	@Test
	public void a_section_is_not_equal_to_null() {
		Assert.assertFalse(emptySeatedSection.equals(null));
	}

	@Test
	public void a_section_is_equal_to_itself() {
		Assert.assertTrue(emptySeatedSection.equals(emptySeatedSection));
	}

	@Test
	public void a_section_is_not_equal_to_an_object_of_another_type() {
		Assert.assertFalse(emptySeatedSection.equals(A_LOCATION));
	}

	@Test
	public void two_sections_are_equal_if_they_share_sportName_gameDate_and_sectionName() {
		Assert.assertTrue(emptySeatedSection.equals(seatedSection));
	}

	@Test
	public void two_equal_sections_shares_the_same_hashCode() {
		Assert.assertEquals(emptySeatedSection.hashCode(), seatedSection.hashCode());
	}

	@Test
	public void getSubTotal_multiplies_price_by_number_of_tickets() {
		Assert.assertEquals(selectedSeats.size() * A_PRICE, seatedSection.getSubtotal());
		Assert.assertEquals(A_NUMBER_OF_TICKETS * A_PRICE, generalSection.getSubtotal());
	}
}
