package ca.ulaval.glo4003.presentation.controllers.administration;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GameSelectionViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.GeneralTicketsToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SeatedTicketsToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SelectSportViewModel;
import ca.ulaval.glo4003.services.AdministrationViewService;
import ca.ulaval.glo4003.services.CommandTicketService;
import ca.ulaval.glo4003.services.SportViewService;
import ca.ulaval.glo4003.utilities.time.DisplayDate;

@RunWith(MockitoJUnitRunner.class)
public class AddTicketsControllerTest {

	private static final String A_SPORT = "Sport";
	private static final DisplayDate A_DATE = new DisplayDate(new DateTime(100));
	private static final int A_NUMBER_OF_TICKETS = 15;
	private static final String A_PRICE = "48.5";
	private static final String A_SECTION = "Section";
	private static final String A_SEAT = "Seat";

	@Mock
	private SportViewService sportViewService;
	@Mock
	private CommandTicketService ticketService;
	@Mock
	private AdministrationViewService adminViewService;
	@Mock
	private User user;
	@Mock
	private Model model;
	@Mock
	private HttpSession session;

	@InjectMocks
	private AddTicketsController controller;

	private List<GameSelectionViewModel> gameSelectionVMs = new ArrayList<>();

	private SelectSportViewModel generalTicketVM;
	private SelectSportViewModel seatedTicketVM;
	private GeneralTicketsToAddViewModel generalTicketsToAddVM;
	private SeatedTicketsToAddViewModel seatedTicketsToAddVM;

	@Before
	public void setUp() throws SportDoesntExistException {
		when(user.isLogged()).thenReturn(true);
		when(user.isAdmin()).thenReturn(true);
		initiateVM();
		when(session.getAttribute("currentUser")).thenReturn(user);
		when(adminViewService.getGameSelectionForSport(A_SPORT)).thenReturn(gameSelectionVMs);

	}

	@Test
	public void chooseSportAndTicketType_returns_correct_view() {
		ModelAndView mav = controller.chooseSportAndTicketType(session);

		Assert.assertEquals("admin/addTickets-chooseSport", mav.getViewName());
	}

	@Test
	public void chooseSportAndTicketType_asks_sportService_for_a_list_of_sports() {
		controller.chooseSportAndTicketType(session);

		verify(sportViewService).getSports();
	}

	@Test
	public void getAddTicketForm_return_correct_form_if_ticketKind_is_general() throws SportDoesntExistException,
			GameDoesntExistException {
		ModelAndView mav = controller.getAddTicketForm(session, generalTicketVM, model);

		Assert.assertEquals("admin/addTickets-General", mav.getViewName());
	}

	@Test
	public void getAddTicketForm_return_correct_form_if_ticketKind_is_seated() throws SportDoesntExistException,
			GameDoesntExistException {
		ModelAndView mav = controller.getAddTicketForm(session, seatedTicketVM, model);

		Assert.assertEquals("admin/addTickets-Seated", mav.getViewName());
	}

	@Test
	public void getAddTicketForm_return_a_model_with_sportName_and_the_choice_of_games()
			throws SportDoesntExistException, GameDoesntExistException {
		ModelAndView mav = controller.getAddTicketForm(session, generalTicketVM, model);

		Assert.assertEquals(A_SPORT, mav.getModel().get("sportName"));
		Assert.assertSame(gameSelectionVMs, mav.getModel().get("gameSelectionVM"));
	}

	@Test
	public void addTickets_general_call_ticketService() throws Exception {
		controller.addTickets_general(session, generalTicketsToAddVM, model);

		verify(ticketService).addGeneralTickets(anyString(), any(DateTime.class), anyInt(), anyDouble());
	}

	@Test
	public void if_service_succeeds_addTickets_general_return_success_view() throws SportDoesntExistException,
			GameDoesntExistException, NoSportForUrlException {
		ModelAndView mav = controller.addTickets_general(session, generalTicketsToAddVM, model);

		Assert.assertEquals("/admin/tickets-added", mav.getViewName());
	}

	@Test
	public void if_service_fails_addTickets_general_return_error_view() throws Exception {
		doThrow(new GameAlreadyExistException()).when(ticketService).addGeneralTickets(anyString(),
				any(DateTime.class), anyInt(), anyDouble());

		ModelAndView mav = controller.addTickets_general(session, generalTicketsToAddVM, model);

		Assert.assertEquals("/admin/tickets-added-date-error", mav.getViewName());
	}

	@Test
	public void addTickets_seated_call_ticketService() throws Exception {
		controller.addTickets_seated(session, seatedTicketsToAddVM, model);

		verify(ticketService).addSeatedTicket(anyString(), any(DateTime.class), anyString(), anyString(), anyDouble());
	}

	@Test
	public void if_service_succeeds_addTickets_return_success_view() throws Exception {
		ModelAndView mav = controller.addTickets_seated(session, seatedTicketsToAddVM, model);

		Assert.assertEquals("/admin/tickets-added", mav.getViewName());
	}

	@Test
	public void if_service_fails_addTickets_return_error_view() throws Exception {
		doThrow(new GameAlreadyExistException()).when(ticketService).addSeatedTicket(anyString(), any(DateTime.class),
				anyString(), anyString(), anyDouble());

		ModelAndView mav = controller.addTickets_seated(session, seatedTicketsToAddVM, model);

		Assert.assertEquals("/admin/tickets-added-date-error", mav.getViewName());
	}

	private void initiateVM() {
		generalTicketVM = new SelectSportViewModel();
		seatedTicketVM = new SelectSportViewModel();
		generalTicketVM.setTypeBillet(TicketKind.GENERAL_ADMISSION);
		generalTicketVM.setSport(A_SPORT);
		seatedTicketVM.setTypeBillet(TicketKind.WITH_SEAT);
		generalTicketsToAddVM = new GeneralTicketsToAddViewModel();
		generalTicketsToAddVM.setSportName(A_SPORT);
		generalTicketsToAddVM.setGameDate(A_DATE);
		generalTicketsToAddVM.setNumberOfTickets(A_NUMBER_OF_TICKETS);
		generalTicketsToAddVM.setPrice(A_PRICE);
		seatedTicketsToAddVM = new SeatedTicketsToAddViewModel();
		seatedTicketsToAddVM.setSportName(A_SPORT);
		seatedTicketsToAddVM.setGameDate(A_DATE);
		seatedTicketsToAddVM.setPrice(A_PRICE);
		seatedTicketsToAddVM.setSeat(A_SEAT);
		seatedTicketsToAddVM.setSection(A_SECTION);
	}
}
