package ca.ulaval.glo4003.services;

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
import ca.ulaval.glo4003.domain.cart.SectionForCartFactory;
import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.sections.ISectionRepository;
import ca.ulaval.glo4003.domain.sections.Section;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.services.exceptions.InvalidTicketsException;
import ca.ulaval.glo4003.services.exceptions.TicketsNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

	private static final String SPORT_NAME = "Soccer masculin";
	
	private static final DateTime GAME_DATE = DateTime.now();

	private static final String SECTION_NAME = "nom de section";
	
	private static final Integer NUMBER_OF_TICKETS_TO_BUY = 3;
	
	@Mock
	private GameDao gameDao;

	@Mock
	private SectionDao sectionDao;
	
	@Mock
	private ISectionRepository sectionRepository;
	
	@Mock
	private CommandTicketService ticketService;
	
	@Mock
	private Cart currentCart;
	
	@Mock
	private SectionForCartFactory sectionForCartFactory;
	
	@InjectMocks
	private CartService cartService;
	
	@Test(expected=InvalidTicketsException.class)
	public void when_isntValidGeneralTickets_addGeneralTicketsToCart_should_raise_InvalidTicketsException() throws SectionDoesntExistException, InvalidTicketsException, TicketsNotFoundException {
		Section section = mock(Section.class);
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(false);
		
		cartService.addGeneralTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, NUMBER_OF_TICKETS_TO_BUY);
	}
	
	@Test(expected=InvalidTicketsException.class)
	public void when_isntValidWithSeatTickets_addWithSeatTicketsToCart_should_raise_InvalidTicketsException() throws SectionDoesntExistException, InvalidTicketsException, TicketsNotFoundException {
		Section section = mock(Section.class);
		Set<String> selectedSeats = new HashSet<>();
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(true);
		
		cartService.addWithSeatTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, selectedSeats);
	}
}
