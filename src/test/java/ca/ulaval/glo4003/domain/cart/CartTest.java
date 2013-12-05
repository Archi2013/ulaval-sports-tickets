package ca.ulaval.glo4003.domain.cart;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

@RunWith(MockitoJUnitRunner.class)
public class CartTest {

	private static final double A_SUBTOTAL = 35;
	private static final double ANOTHER_SUBTOTAL = 45;

	@Mock
	SectionForCart section;
	@Mock
	SectionForCart otherSection;

	private Cart cart;

	@Before
	public void setUp() {
		cart = new Cart();
		when(section.getSubtotal()).thenReturn(A_SUBTOTAL);
		when(otherSection.getSubtotal()).thenReturn(ANOTHER_SUBTOTAL);
	}

	@Test
	public void a_new_cart_contains_no_tickets() {
		Assert.assertFalse(cart.containTickets());
	}

	@Test(expected = NoTicketsInCartException.class)
	public void getting_sections_of_empty_cart_throws_exception() throws NoTicketsInCartException {
		cart.getSections();
	}

	@Test(expected = NoTicketsInCartException.class)
	public void getting_total_price_of_empty_cart_throws_exception() throws NoTicketsInCartException {
		cart.getCumulativePrice();
	}

	@Test
	public void addSection_adds_the_section_to_the_cart() throws NoTicketsInCartException {
		cart.addSection(section);
		Set<SectionForCart> sections = cart.getSections();
		sections.contains(section);
	}

	@Test
	public void if_a_section_is_added_a_second_time_the_new_tickets_are_added_to_the_section_already_in_cart() {
		cart.addSection(section);
		cart.addSection(section);

		verify(section).addElements(section);
	}

	@Test
	public void getCumulativePrice_returns_the_sum_of_the_subtotals() throws NoTicketsInCartException {
		cart.addSection(section);
		cart.addSection(otherSection);

		double result = cart.getCumulativePrice();

		Assert.assertEquals(A_SUBTOTAL + ANOTHER_SUBTOTAL, result, 1);
	}

	@Test(expected = NoTicketsInCartException.class)
	public void empty_clear_the_cart_of_all_stocked_sections() throws NoTicketsInCartException {
		cart.addSection(section);
		cart.addSection(otherSection);
		cart.empty();

		cart.getCumulativePrice();
	}
}
