package ca.ulaval.glo4003.domain.services;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.repositories.IGameRepository;
import ca.ulaval.glo4003.domain.repositories.ITicketRepository;
import ca.ulaval.glo4003.domain.tickets.Ticket;

@RunWith(MockitoJUnitRunner.class)
public class CommandTicketServiceTest {

	private static final boolean AVAILABLE = true;
	public static final String A_SPORT = "Sport";
	public static final String A_SECTION = "Section";
	public static final String A_SEAT = "Seat";
	public static DateTime A_DATE = new DateTime(100);
	public static final int A_NUMBER_OF_TICKETS = 100;

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
	public void setup() {
		when(ticketRepository.instantiateNewTicket()).thenReturn(ticketToAdd);
		when(ticketRepository.instantiateNewTicket(A_SECTION, A_SEAT, AVAILABLE)).thenReturn(ticketToAdd);
		when(gameRepository.recoverGame(A_SPORT, A_DATE)).thenReturn(game);
	}

	@Test
	public void addGeneralTickets_adds_n_tickets_to_a_sport() throws Exception {
		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS);

		verify(game, times(A_NUMBER_OF_TICKETS)).addTicket(any(Ticket.class));
	}

	@Test
	public void tickets_added_by_addGeneralTickets_are_instantiated_by_a_repository() throws Exception {

		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS);

		verify(game, atLeastOnce()).addTicket(ticketToAdd);
	}

	@Test
	public void repository_in_addGeneralTickets_intantiates_default_tickets() throws Exception {
		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS);

		verify(ticketRepository, atLeastOnce()).instantiateNewTicket();
	}

	@Test
	public void game_used_by_addGeneralTickets_is_obtained_from_a_repository() throws Exception {
		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS);

		verify(gameRepository).recoverGame(A_SPORT, A_DATE);
	}

	@Test
	public void addGeneralTickets_commits_on_the_game_repository() throws Exception {
		ticketService.addGeneralTickets(A_SPORT, A_DATE, A_NUMBER_OF_TICKETS);

		verify(gameRepository).commit();
	}

	@Test
	public void addSeatedTicket_add_a_single_ticket_to_the_game() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT);

		verify(game, times(1)).addTicket(any(Ticket.class));
	}

	@Test
	public void ticket_added_by_addSeatedTicket_is_instantiated_by_a_repository() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT);

		verify(game, atLeastOnce()).addTicket(ticketToAdd);
	}

	@Test
	public void repository_in_addGeneralTickets_intantiates_ticket_with_section_and_seat() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT);

		verify(ticketRepository).instantiateNewTicket(A_SECTION, A_SEAT, AVAILABLE);
	}

	@Test
	public void game_used_by_addSeatedTicket_is_obtained_from_repository() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT);

		verify(gameRepository).recoverGame(A_SPORT, A_DATE);
	}

	@Test
	public void addSeatedTicket_commits_on_gameRepository() throws Exception {
		ticketService.addSeatedTicket(A_SPORT, A_DATE, A_SECTION, A_SEAT);

		verify(gameRepository).commit();
	}
}
