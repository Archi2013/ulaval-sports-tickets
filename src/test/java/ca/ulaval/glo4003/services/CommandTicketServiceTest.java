package ca.ulaval.glo4003.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.cart.SectionForCart;
import ca.ulaval.glo4003.domain.game.Game;
import ca.ulaval.glo4003.domain.game.IGameRepository;
import ca.ulaval.glo4003.domain.tickets.ITicketRepository;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.services.CommandTicketService;

@RunWith(MockitoJUnitRunner.class)
public class CommandTicketServiceTest {

	private static final boolean AVAILABLE = true;
	public static final String A_SPORT = "Sport";
	public static final String A_SECTION = "Section";
	public static final String A_SEAT = "Seat";
	public static DateTime A_DATE = new DateTime(100);
	public static final int A_NUMBER_OF_TICKETS = 100;
	public static final double A_PRICE = 12;

	@Mock
	private Game game;

	@Mock
	private Ticket ticketToAdd;

	@Mock
	private ITicketRepository ticketRepository;

	@Mock
	private IGameRepository gameRepository;

	@InjectMocks
	private CommandTicketService ticketService;

	@Before
	public void setup() throws GameDoesntExistException, TicketAlreadyExistsException {
		when(ticketRepository.createGeneralTicket(A_PRICE, AVAILABLE)).thenReturn(ticketToAdd);
		when(ticketRepository.createSeatedTicket(A_SEAT, A_SECTION, A_PRICE, AVAILABLE)).thenReturn(ticketToAdd);
		when(gameRepository.get(A_SPORT, A_DATE)).thenReturn(game);
	}

	@Test
	public void addGeneralTickets_adds_n_tickets_to_a_sport() throws Exception {
		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS, A_PRICE);

		verify(game, times(A_NUMBER_OF_TICKETS)).addTicket(any(Ticket.class));
	}

	@Test
	public void tickets_added_by_addGeneralTickets_are_instantiated_by_a_repository() throws Exception {

		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS, A_PRICE);

		verify(game, atLeastOnce()).addTicket(ticketToAdd);
	}

	@Test
	public void repository_in_addGeneralTickets_intantiates_default_tickets() throws Exception {
		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS, A_PRICE);

		verify(ticketRepository, atLeastOnce()).createGeneralTicket(A_PRICE, AVAILABLE);
	}

	@Test
	public void game_used_by_addGeneralTickets_is_obtained_from_a_repository() throws Exception {
		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS, A_PRICE);

		verify(gameRepository).get(A_SPORT, A_DATE);
	}

	@Test
	public void addGeneralTickets_commits_on_the_game_repository() throws Exception {
		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS, A_PRICE);

		verify(gameRepository).commit();
	}

	@Test
	public void addSeatedTicket_add_a_single_ticket_to_the_game() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT, A_PRICE);

		verify(game, times(1)).addTicket(any(Ticket.class));
	}

	@Test
	public void ticket_added_by_addSeatedTicket_is_instantiated_by_a_repository() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT, A_PRICE);

		verify(game, atLeastOnce()).addTicket(ticketToAdd);
	}

	@Test
	public void repository_in_addGeneralTickets_intantiates_ticket_with_section_and_seat() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT, A_PRICE);

		verify(ticketRepository).createSeatedTicket(A_SEAT, A_SECTION, A_PRICE, AVAILABLE);
	}

	@Test
	public void game_used_by_addSeatedTicket_is_obtained_from_repository() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT, A_PRICE);

		verify(gameRepository).get(A_SPORT, A_DATE);
	}

	@Test
	public void addSeatedTicket_commits_on_gameRepository() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT, A_PRICE);

		verify(gameRepository).commit();
	}

	@Test
	public void testMakeTicketsUnavailable() throws Exception {
		Set<SectionForCart> sections = new HashSet<SectionForCart>();
		ticketService.makeTicketsUnavailable(sections);
		
		verify(ticketRepository).commit();
	}
	
	@Test
	public void testMakeTicketsUnavailable_withOneSectionForCart() throws Exception {
		Set<SectionForCart> sections = new HashSet<SectionForCart>();
		sections.add(mock(SectionForCart.class));
		
		ticketService.makeTicketsUnavailable(sections);
		
		verify(ticketRepository).commit();
	}
	
	@Test
	public void testMakeTicketsUnavailable_withTicketsForGeneralAdmission() throws Exception {
		SectionForCart sectionWithGeneralAdmission = mock(SectionForCart.class);
		when(sectionWithGeneralAdmission.isGeneralAdmission()).thenReturn(true);
		
		Set<SectionForCart> sections = new HashSet<SectionForCart>();
		sections.add(sectionWithGeneralAdmission);
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(mock(Ticket.class));
		when(ticketRepository.getMultipleGeneralTickets(anyString(), any(DateTime.class), anyInt())).thenReturn(tickets);

		
		ticketService.makeTicketsUnavailable(sections);
		
		verify(ticketRepository).commit();
	}
	
	@Test
	public void testMakeTicketsUnavailable_withTicketsSelectedSeat() throws Exception {
		SectionForCart sectionWithSelectedSeat = mock(SectionForCart.class);
		when(sectionWithSelectedSeat.isGeneralAdmission()).thenReturn(false);
		
		Set<SectionForCart> sections = new HashSet<SectionForCart>();
		sections.add(sectionWithSelectedSeat);
		
		Set<String> seats = new HashSet<String>();
		String aSeat = "aSeat";
		seats.add(aSeat);
		when(sectionWithSelectedSeat.getSelectedSeats()).thenReturn(seats);
		
		Ticket ticket = mock(Ticket.class);
		when(ticketRepository.get(anyString(), any(DateTime.class), anyString(), anyString())).thenReturn(ticket);
		
		ticketService.makeTicketsUnavailable(sections);
		
		verify(ticketRepository).commit();
	}
}
