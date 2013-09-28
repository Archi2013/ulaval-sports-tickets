package ca.ulaval.glo4003.web.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.dao.TicketDao;
import ca.ulaval.glo4003.dao.TicketDoesntExistException;
import ca.ulaval.glo4003.dto.TicketDto;
import ca.ulaval.glo4003.web.controller.TicketController;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

	public static final int UN_ID = 123;

	@Mock
	private TicketDto ticketDto;

	@Mock
	private TicketDao ticketDao;

	@Mock
	private Model model;

	@InjectMocks
	private TicketController ticketController;

	@Before
	public void setUp() throws TicketDoesntExistException {
		when(ticketDao.getTicket(UN_ID)).thenReturn(ticketDto);
	}

	@Test
	public void getTicket_should_add_ticket_to_model() {
		ticketController.getTicket(UN_ID, model);

		verify(model).addAttribute("ticket", ticketDto);
	}

	@Test
	public void getTicket_should_return_right_path() {
		String path = ticketController.getTicket(UN_ID, model);

		assertEquals("ticket/detail", path);
	}

	@Test
	public void getTicket_should_redirect_to_home_path_when_ticket_id_doesnt_exist() throws TicketDoesntExistException {
		when(ticketDao.getTicket(UN_ID)).thenThrow(TicketDoesntExistException.class);

		String path = ticketController.getTicket(UN_ID, model);

		assertEquals("error/404", path);
	}
}
