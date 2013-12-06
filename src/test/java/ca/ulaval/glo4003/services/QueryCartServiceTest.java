package ca.ulaval.glo4003.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.cart.Cart;
import ca.ulaval.glo4003.domain.cart.SectionForCart;
import ca.ulaval.glo4003.domain.cart.SectionForCartFactory;
import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.Section;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.sections.SectionRepository;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.services.exceptions.CartException;
import ca.ulaval.glo4003.services.exceptions.InvalidTicketsException;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;
import ca.ulaval.glo4003.services.exceptions.TicketsNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class QueryCartServiceTest {

	private static final Double CUMULATIVE_PRICE = 65.78;
	
	@Mock
	private Cart currentCart;
	
	@InjectMocks
	private QueryCartService queryCartService;
	
	@Test
	public void getCumulativePrice_should_return_the_cumulativePrice_of_cart() throws NoTicketsInCartException {
		when(currentCart.getCumulativePrice()).thenReturn(CUMULATIVE_PRICE);
		
		Double actual = queryCartService.getCumulativePrice();
		
		assertEquals(CUMULATIVE_PRICE, actual);
	}
	
	@Test
	public void cartContainsTickets_should_return_true_if_there_are_tickets_in_cart() {
		boolean expected = true;
		
		when(currentCart.containTickets()).thenReturn(expected);
		
		boolean actual = queryCartService.cartContainsTickets();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void cartContainsTickets_should_return_false_if_there_arent_tickets_in_cart() {
		boolean expected = false;
		
		when(currentCart.containTickets()).thenReturn(expected);
		
		boolean actual = queryCartService.cartContainsTickets();
		
		assertEquals(expected, actual);
	}
}
