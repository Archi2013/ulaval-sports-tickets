package ca.ulaval.glo4003.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.data_access.TicketDao;
import ca.ulaval.glo4003.dtos.TicketDto;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	public static final int UN_ID = 123;

	@Mock
	private TicketDao ticketDao;

	@Mock
	private Model model;

	@InjectMocks
	private GameController gameController;

	@Before
	public void setUp() {
	}

	@Test
	public void getTicketsForGame_add_tickets_of_the_specified_game_to_model() {
		List<TicketDto> tickets = new LinkedList<TicketDto>();
		when(ticketDao.getTicketsForGame(UN_ID)).thenReturn(tickets);

		gameController.getTicketsForGame(UN_ID, model);

		verify(model).addAttribute("tickets", tickets);
	}

	@Test
	public void getTicketsForGame_return_correct_path_to_view() {
		// String path = gameController.getTicketsForGame(UN_ID, model);

		// Assert.assertEquals("games/tickets", path);
	}
}
