package ca.ulaval.glo4003.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.PaymentService;
import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.web.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

	private static final String TRAFFICKED_PAGE = "payment/trafficked";

	private static final String NOT_CONNECTED_USER_PAGE = "payment/not-connected-user";

	private static final String SUCCES_PAGE = "payment/succes";

	private static final String MODE_OF_PAYMENT_PAGE = "payment/mode-of-payment";

	private static final String PAYMENT_HOME_PAGE = "payment/home";

	private static final int NUMBER_OF_TICKETS_TO_BUY = 4;

	private static final String SECTION_NAME = "Turquoise";

	private static final Long GAME_ID = 23L;
	
	@Mock
	SearchService searchService;
	
	@Mock
	PaymentService paymentService;
	
	@Mock
	private User currentUser;
	
	@InjectMocks
	private PaymentController controller;

	private ChooseTicketsViewModel chooseTicketsVM;
	
	private List<String> selectedSeats;
	
	@Before
	public void setUp() {
		chooseTicketsVM = mock(ChooseTicketsViewModel.class);
		selectedSeats = new ArrayList<>();
		selectedSeats.add("X3");
		selectedSeats.add("X7");
		when(chooseTicketsVM.getGameId()).thenReturn(GAME_ID);
		when(chooseTicketsVM.getSectionName()).thenReturn(SECTION_NAME);
		when(chooseTicketsVM.getNumberOfTicketsToBuy()).thenReturn(NUMBER_OF_TICKETS_TO_BUY);
		when(chooseTicketsVM.getSelectedSeats()).thenReturn(selectedSeats);
	}

	@Test
	public void home_should_return_home_page_when_user_is_connected_and_no_error_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		
		assertEquals(PAYMENT_HOME_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_return_not_connected_user_page_when_user_isnt_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(false);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		
		assertEquals(NOT_CONNECTED_USER_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_return_trafficked_page_when_errors_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(true);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		
		assertEquals(TRAFFICKED_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_add_connected_user_at_true_in_model_when_user_is_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertTrue((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void home_should_add_connected_user_at_false_when_user_isnt_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertFalse((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void home_should_add_currency_in_model_when_user_is_connected_and_no_error_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("currency"));
		assertEquals(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void modeOfPayment_should_return_mode_of_payment_page_when_user_is_connected() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.modeOfPayment();
		
		assertEquals(MODE_OF_PAYMENT_PAGE, mav.getViewName());
	}
	
	@Test
	public void modeOfPayment_should_return_not_connected_user_page_when_user_isnt_connected() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.modeOfPayment();
		
		assertEquals(NOT_CONNECTED_USER_PAGE, mav.getViewName());
	}
	
	@Test
	public void modeOfPayment_should_add_connected_user_at_true_when_user_is_connected() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertTrue((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void modeOfPayment_should_add_connected_user_at_false_in_model_when_user_isnt_connected() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertFalse((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void modeOfPayment_should_add_currency_in_model_when_user_is_connected() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("currency"));
		assertEquals(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void validate_should_return_succes_page_when_user_is_connected_and_no_error_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		
		assertEquals(SUCCES_PAGE, mav.getViewName());
	}
	
	@Test
	public void validate_should_return_not_connected_user_page_when_user_isnt_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(false);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		
		assertEquals(NOT_CONNECTED_USER_PAGE, mav.getViewName());
	}
	
	@Test
	public void validate_should_return_mode_of_payment_page_when_errors_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(true);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		
		assertEquals(MODE_OF_PAYMENT_PAGE, mav.getViewName());
	}
	
	@Test
	public void validate_should_add_connected_user_at_true_when_user_is_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertTrue((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void validate_should_add_connected_user_at_false_when_user_isnt_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertFalse((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void validate_should_add_currency_in_model_when_user_is_connected_and_no_error_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("currency"));
		assertEquals(Constants.CURRENCY, modelMap.get("currency"));
	}
}
