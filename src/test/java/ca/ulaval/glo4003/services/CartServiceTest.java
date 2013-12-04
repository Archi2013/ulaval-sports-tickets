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
public class CartServiceTest {

	private static final Double CUMULATIVE_PRICE = 65.78;

	private static final int NUMBER_OF_TICKETS = 99;

	private static final double PRICE = 77.7;

	private static final String LOCATION = "Mer des caraïbes";

	private static final String OPPONENTS = "Hippocampe";

	private static final String SPORT_NAME = "Soccer masculin";
	
	private static final DateTime GAME_DATE = DateTime.now();

	private static final String SECTION_NAME = "nom de section";
	
	private static final Integer NUMBER_OF_TICKETS_TO_BUY = 3;
	
	@Mock
	private GameDao gameDao;

	@Mock
	private SectionDao sectionDao;
	
	@Mock
	private SectionRepository sectionRepository;
	
	@Mock
	private CommandTicketService ticketService;
	
	@Mock
	private Cart currentCart;
	
	@Mock
	private SectionForCartFactory sectionForCartFactory;
	
	@InjectMocks
	private CartService cartService;
	
	@Test
	public void given_general_tickets_informations_addGeneralTicketsToCart_should_add_a_sectionForCart_to_cart() throws InvalidTicketsException, TicketsNotFoundException, SectionDoesntExistException, GameDoesntExistException {
		Section section = mock(Section.class);
		GameDto gameDto = new GameDto(OPPONENTS, GAME_DATE, SPORT_NAME, LOCATION);
		SectionDto sectionDto = new SectionDto(NUMBER_OF_TICKETS, PRICE);
		SectionForCart sectionFC = mock(SectionForCart.class);
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(true);
		when(section.isValidNumberOfTicketsForGeneralTickets(NUMBER_OF_TICKETS_TO_BUY)).thenReturn(true);
		when(gameDao.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);
		when(sectionDao.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		when(sectionForCartFactory.createSectionForGeneralTickets(SPORT_NAME, GAME_DATE, SECTION_NAME,
							OPPONENTS, LOCATION,
							NUMBER_OF_TICKETS_TO_BUY, PRICE)).thenReturn(sectionFC);
		
		cartService.addGeneralTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, NUMBER_OF_TICKETS_TO_BUY);
		
		verify(currentCart).addSection(sectionFC);
	}
	
	@Test(expected=InvalidTicketsException.class)
	public void when_isntValidGeneralTickets_addGeneralTicketsToCart_should_raise_InvalidTicketsException() throws InvalidTicketsException, TicketsNotFoundException, SectionDoesntExistException {
		Section section = mock(Section.class);
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(false);
		
		cartService.addGeneralTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, NUMBER_OF_TICKETS_TO_BUY);
	}
	
	@Test(expected=InvalidTicketsException.class)
	public void when_isntValidNumberOfTicketsForGeneralTickets_addGeneralTicketsToCart_should_raise_InvalidTicketsException() throws InvalidTicketsException, TicketsNotFoundException, GameDoesntExistException, SectionDoesntExistException {
		Section section = mock(Section.class);
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(true);
		when(section.isValidNumberOfTicketsForGeneralTickets(NUMBER_OF_TICKETS_TO_BUY)).thenReturn(false);
		
		cartService.addGeneralTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, NUMBER_OF_TICKETS_TO_BUY);
	}
	
	@Test(expected=TicketsNotFoundException.class)
	public void when_GameDoesntExistException_addGeneralTicketsToCart_should_raise_TicketsNotFoundException() throws InvalidTicketsException, TicketsNotFoundException, GameDoesntExistException, SectionDoesntExistException {
		Section section = mock(Section.class);
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(true);
		when(section.isValidNumberOfTicketsForGeneralTickets(NUMBER_OF_TICKETS_TO_BUY)).thenReturn(true);
		when(gameDao.get(SPORT_NAME, GAME_DATE)).thenThrow(new GameDoesntExistException());
		
		cartService.addGeneralTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, NUMBER_OF_TICKETS_TO_BUY);
	}
	
	@Test(expected=TicketsNotFoundException.class)
	public void when_SectionDoesntExistException_addGeneralTicketsToCart_should_raise_TicketsNotFoundException() throws InvalidTicketsException, TicketsNotFoundException, SectionDoesntExistException {
		Section section = mock(Section.class);
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(true);
		when(section.isValidNumberOfTicketsForGeneralTickets(NUMBER_OF_TICKETS_TO_BUY)).thenReturn(true);
		when(sectionDao.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenThrow(new SectionDoesntExistException());
		
		cartService.addGeneralTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, NUMBER_OF_TICKETS_TO_BUY);
	}
	
	@Test
	public void given_with_seat_tickets_informations_addWithSeatTicketsToCart_should_add_a_sectionForCart_to_cart() throws InvalidTicketsException, TicketsNotFoundException, SectionDoesntExistException, GameDoesntExistException {
		Section section = mock(Section.class);
		Set<String> seats = new HashSet<>();
		GameDto gameDto = new GameDto(OPPONENTS, GAME_DATE, SPORT_NAME, LOCATION);
		SectionDto sectionDto = new SectionDto(SECTION_NAME, NUMBER_OF_TICKETS, PRICE, seats);
		SectionForCart sectionFC = mock(SectionForCart.class);
		Set<String> selectedSeats = new HashSet<>();
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(false);
		when(section.isValidSelectedSeatsForWithSeatTickets(selectedSeats)).thenReturn(true);
		when(gameDao.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);
		when(sectionDao.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		when(sectionForCartFactory.createSectionForWithSeatTickets(SPORT_NAME, GAME_DATE, SECTION_NAME,
						OPPONENTS, LOCATION, selectedSeats, PRICE)).thenReturn(sectionFC);
		
		cartService.addWithSeatTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, selectedSeats);
		
		verify(currentCart).addSection(sectionFC);
	}
	
	@Test(expected=InvalidTicketsException.class)
	public void when_isntValidWithSeatTickets_addWithSeatTicketsToCart_should_raise_InvalidTicketsException() throws SectionDoesntExistException, InvalidTicketsException, TicketsNotFoundException {
		Section section = mock(Section.class);
		Set<String> selectedSeats = new HashSet<>();
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(true);
		
		cartService.addWithSeatTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, selectedSeats);
	}
	
	@Test(expected=InvalidTicketsException.class)
	public void when_isntValidSelectedSeatsForWithSeatTickets_addWithSeatTicketsToCart_should_raise_InvalidTicketsException() throws SectionDoesntExistException, InvalidTicketsException, TicketsNotFoundException {
		Section section = mock(Section.class);
		Set<String> selectedSeats = new HashSet<>();
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(false);
		when(section.isValidSelectedSeatsForWithSeatTickets(selectedSeats)).thenReturn(false);
		
		cartService.addWithSeatTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, selectedSeats);
	}
	
	@Test(expected=TicketsNotFoundException.class)
	public void when_GameDoesntExistException_addWithSeatTicketsToCart_should_raise_TicketsNotFoundException() throws SectionDoesntExistException, InvalidTicketsException, TicketsNotFoundException, GameDoesntExistException {
		Section section = mock(Section.class);
		Set<String> selectedSeats = new HashSet<>();
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(false);
		when(section.isValidSelectedSeatsForWithSeatTickets(selectedSeats)).thenReturn(true);
		when(gameDao.get(SPORT_NAME, GAME_DATE)).thenThrow(new GameDoesntExistException());
		
		cartService.addWithSeatTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, selectedSeats);
	}
	
	@Test(expected=TicketsNotFoundException.class)
	public void when_SectionDoesntExistException_addWithSeatTicketsToCart_should_raise_TicketsNotFoundException() throws SectionDoesntExistException, InvalidTicketsException, TicketsNotFoundException {
		Section section = mock(Section.class);
		Set<String> selectedSeats = new HashSet<>();
		
		when(sectionRepository.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(section);
		when(section.isGeneralAdmission()).thenReturn(false);
		when(section.isValidSelectedSeatsForWithSeatTickets(selectedSeats)).thenReturn(true);
		when(sectionDao.getAvailable(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenThrow(new SectionDoesntExistException());
		
		cartService.addWithSeatTicketsToCart(SPORT_NAME, GAME_DATE, SECTION_NAME, selectedSeats);
	}
	
	@Test
	public void makeTicketsUnavailableToOtherPeople_should_make_tickets_in_cart_unavailable() throws GameDoesntExistException, TicketDoesntExistException, SportDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, NoTicketsInCartException {
		Set<SectionForCart> sections = new HashSet<>();
		
		when(currentCart.getSections()).thenReturn(sections);
		
		cartService.makeTicketsUnavailableToOtherPeople();
		
		verify(ticketService).makeTicketsUnavailable(sections);
	}
	
	@Test(expected=CartException.class)
	public void when_GameDoesntExistException_makeTicketsUnavailableToOtherPeople_should_raise_CartException() throws GameDoesntExistException, TicketDoesntExistException, SportDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, NoTicketsInCartException {
		Set<SectionForCart> sections = new HashSet<>();
		
		when(currentCart.getSections()).thenReturn(sections);
		doThrow(new GameDoesntExistException()).when(ticketService).makeTicketsUnavailable(sections);
		
		cartService.makeTicketsUnavailableToOtherPeople();
	}
	
	@Test(expected=CartException.class)
	public void when_TicketDoesntExistException_makeTicketsUnavailableToOtherPeople_should_raise_CartException() throws GameDoesntExistException, TicketDoesntExistException, SportDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, NoTicketsInCartException {
		Set<SectionForCart> sections = new HashSet<>();
		
		when(currentCart.getSections()).thenReturn(sections);
		doThrow(new TicketDoesntExistException()).when(ticketService).makeTicketsUnavailable(sections);
		
		cartService.makeTicketsUnavailableToOtherPeople();
	}
	
	@Test(expected=CartException.class)
	public void when_SportDoesntExistException_makeTicketsUnavailableToOtherPeople_should_raise_CartException() throws GameDoesntExistException, TicketDoesntExistException, SportDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, NoTicketsInCartException {
		Set<SectionForCart> sections = new HashSet<>();
		
		when(currentCart.getSections()).thenReturn(sections);
		doThrow(new SportDoesntExistException()).when(ticketService).makeTicketsUnavailable(sections);
		
		cartService.makeTicketsUnavailableToOtherPeople();
	}
	
	@Test(expected=CartException.class)
	public void when_GameAlreadyExistException_makeTicketsUnavailableToOtherPeople_should_raise_CartException() throws GameDoesntExistException, TicketDoesntExistException, SportDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, NoTicketsInCartException {
		Set<SectionForCart> sections = new HashSet<>();
		
		when(currentCart.getSections()).thenReturn(sections);
		doThrow(new GameAlreadyExistException()).when(ticketService).makeTicketsUnavailable(sections);
		
		cartService.makeTicketsUnavailableToOtherPeople();
	}
	
	@Test(expected=CartException.class)
	public void when_TicketAlreadyExistsException_makeTicketsUnavailableToOtherPeople_should_raise_CartException() throws GameDoesntExistException, TicketDoesntExistException, SportDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, NoTicketsInCartException {
		Set<SectionForCart> sections = new HashSet<>();
		
		when(currentCart.getSections()).thenReturn(sections);
		doThrow(new TicketAlreadyExistsException()).when(ticketService).makeTicketsUnavailable(sections);
		
		cartService.makeTicketsUnavailableToOtherPeople();
	}
	
	@Test(expected=CartException.class)
	public void when_NoTicketsInCartException_makeTicketsUnavailableToOtherPeople_should_raise_CartException() throws GameDoesntExistException, TicketDoesntExistException, SportDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, NoTicketsInCartException {
		when(currentCart.getSections()).thenThrow(new NoTicketsInCartException());
		
		cartService.makeTicketsUnavailableToOtherPeople();
	}
	
	@Test
	public void getCumulativePrice_should_return_the_cumulativePrice_of_cart() throws NoTicketsInCartException {
		when(currentCart.getCumulativePrice()).thenReturn(CUMULATIVE_PRICE);
		
		Double actual = cartService.getCumulativePrice();
		
		assertEquals(CUMULATIVE_PRICE, actual);
	}
	
	@Test
	public void getSectionsInCart_should_return_the_sections_in_cart() throws NoTicketsInCartException {
		Set<SectionForCart> sections = new HashSet<>();
		
		when(currentCart.getSections()).thenReturn(sections);
		
		Set<SectionForCart> actual = cartService.getSectionsInCart();
		
		assertEquals(sections, actual);
	}
	
	@Test
	public void cartContainsTickets_should_return_true_if_there_are_tickets_in_cart() {
		boolean expected = true;
		
		when(currentCart.containTickets()).thenReturn(expected);
		
		boolean actual = cartService.cartContainsTickets();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void cartContainsTickets_should_return_false_if_there_arent_tickets_in_cart() {
		boolean expected = false;
		
		when(currentCart.containTickets()).thenReturn(expected);
		
		boolean actual = cartService.cartContainsTickets();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void emptyCart_should_empty_the_cart() {
		cartService.emptyCart();
		
		verify(currentCart).empty();
	}
}
