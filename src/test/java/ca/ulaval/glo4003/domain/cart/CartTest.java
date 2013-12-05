package ca.ulaval.glo4003.domain.cart;

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

	@Mock
	SectionForCart section;

	private Cart cart;

	@Before
	public void setUp() {
		cart = new Cart();
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
}
