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

import ca.ulaval.glo4003.dto.TicketDto;
import ca.ulaval.glo4003.persistence.dao.TicketDao;
import ca.ulaval.glo4003.persistence.dao.TicketDoesntExistException;
import ca.ulaval.glo4003.web.converter.TicketConverter;
import ca.ulaval.glo4003.web.viewmodel.TicketViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

	public static final int AN_ID = 123;

	@Mock
	private TicketDto ticketDto;

	@Mock
	private TicketDao ticketDao;

	@Mock
	private Model model;
	
	@Mock
	TicketConverter ticketConverter;

	@InjectMocks
	private TicketController ticketController;

	@Before
	public void setUp() throws TicketDoesntExistException {
		when(ticketDao.getTicket(AN_ID)).thenReturn(ticketDto);
	}

	@Test
	public void getTicket_should_add_ticket_to_model() {
		TicketViewModel ticketVM = addToConverter(ticketDto);
		
		ticketController.getTicket(AN_ID, model);

		verify(model).addAttribute("ticket", ticketVM);
	}

	@Test
	public void getTicket_should_return_right_path() {
		String path = ticketController.getTicket(AN_ID, model);

		assertEquals("ticket/detail", path);
	}

	@Test
	public void getTicket_should_redirect_to_home_path_when_ticket_id_doesnt_exist() throws TicketDoesntExistException {
		when(ticketDao.getTicket(AN_ID)).thenThrow(TicketDoesntExistException.class);

		String path = ticketController.getTicket(AN_ID, model);

		assertEquals("error/404", path);
	}
	
	private TicketViewModel addToConverter(TicketDto ticketDto) {
		TicketViewModel viewModel = new TicketViewModel(new Long(123), "45,67", "VIP", "section");
		when(ticketConverter.convert(ticketDto)).thenReturn(viewModel);
		return viewModel;
	}
}
