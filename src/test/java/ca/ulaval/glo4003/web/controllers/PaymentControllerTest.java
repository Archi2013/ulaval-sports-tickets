package ca.ulaval.glo4003.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.NoTicketsInCartException;
import ca.ulaval.glo4003.domain.services.PaymentService;
import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.CreditCardType;
import ca.ulaval.glo4003.domain.utilities.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.controllers.PaymentController;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PaymentViewModel;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

	private static final String ERROR_PAGE = "payment/error-page";

	private static final String PRICE_FR = "76,78";

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
	
	@Mock
	private MessageSource messageSource;
	
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
	public void home_should_return_error_page_when_user_isnt_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(false);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		
		assertEquals(ERROR_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_add_errorMessage_in_model_when_user_isnt_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(false);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("errorMessage"));
	}
	
	@Test
	public void home_should_return_error_page_when_errors_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(true);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		
		assertEquals(ERROR_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_add_errorMessage_in_model_when_errors_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(true);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("errorMessage"));
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
	public void home_should_add_payableItems_when_user_is_connected_and_no_error_in_BindingResult_and_is_valid_ChooseTicketsViewModel()
			throws GameDoesntExistException, SectionDoesntExistException {
		BindingResult bindingResult = mock(BindingResult.class);
		PayableItemsViewModel payableItemsVM = new PayableItemsViewModel();
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(paymentService.isValidChooseTicketsViewModel(chooseTicketsVM)).thenReturn(true);
		when(paymentService.getPayableItemsViewModel(chooseTicketsVM)).thenReturn(payableItemsVM);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("payableItems"));
		assertEquals(payableItemsVM, modelMap.get("payableItems"));
	}
	
	@Test
	public void home_should_save_to_cart_items_when_user_is_connected_and_no_error_in_BindingResult_and_is_valid_ChooseTicketsViewModel()
			throws GameDoesntExistException, SectionDoesntExistException {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(paymentService.isValidChooseTicketsViewModel(chooseTicketsVM)).thenReturn(true);
		
		controller.home(chooseTicketsVM, bindingResult);
		
		verify(paymentService).saveToCart(chooseTicketsVM);
	}
	
	@Test
	public void home_should_add_errorMessage_when_user_is_connected_and_no_error_in_BindingResult_and_isnt_valid_ChooseTicketsViewModel()
			throws GameDoesntExistException, SectionDoesntExistException {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(paymentService.isValidChooseTicketsViewModel(chooseTicketsVM)).thenReturn(false);
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("errorMessage"));
	}
	
	@Test
	public void home_should_add_errorMessage_when_user_is_connected_and_no_error_in_BindingResult_and_is_valid_ChooseTicketsViewModel_and_paymentService_getPayableItemsViewModel_throw_GameDoesntExistException()
			throws GameDoesntExistException, SectionDoesntExistException {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(paymentService.isValidChooseTicketsViewModel(chooseTicketsVM)).thenReturn(true);
		when(paymentService.getPayableItemsViewModel(chooseTicketsVM)).thenThrow(new GameDoesntExistException());
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("errorMessage"));
	}
	
	@Test
	public void home_should_add_errorMessage_when_user_is_connected_and_no_error_in_BindingResult_and_is_valid_ChooseTicketsViewModel_and_paymentService_getPayableItemsViewModel_throw_SectionDoesntExistException()
			throws GameDoesntExistException, SectionDoesntExistException {
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(paymentService.isValidChooseTicketsViewModel(chooseTicketsVM)).thenReturn(true);
		when(paymentService.getPayableItemsViewModel(chooseTicketsVM)).thenThrow(new SectionDoesntExistException());
		
		ModelAndView mav = controller.home(chooseTicketsVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("errorMessage"));
	}
	
	@Test
	public void modeOfPayment_should_return_error_page_when_user_isnt_connected() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.modeOfPayment();
		
		assertEquals(ERROR_PAGE, mav.getViewName());
	}
	
	@Test
	public void modeOfPayment_should_add_errorMessage_in_model_when_user_isnt_connected() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("errorMessage"));
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
	public void modeOfPayment_should_add_paymentForm_in_model_when_user_is_connected() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("paymentForm"));
	}
	
	@Test
	public void modeOfPayment_should_add_creditCardTypes_in_model_when_user_is_connected() {
		List<CreditCardType> creditCardTypes = new ArrayList<>();
		
		when(currentUser.isLogged()).thenReturn(true);
		when(paymentService.getCreditCardTypes()).thenReturn(creditCardTypes);
		
		ModelAndView mav = controller.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		verify(paymentService).getCreditCardTypes();
		assertTrue(modelMap.containsAttribute("creditCardTypes"));
		assertSame(creditCardTypes, modelMap.get("creditCardTypes"));
	}
	
	@Test
	public void modeOfPayment_should_add_cumulativePrice_in_model_when_user_is_connected() throws NoTicketsInCartException {
		when(currentUser.isLogged()).thenReturn(true);
		when(paymentService.getCumulativePriceFR()).thenReturn(PRICE_FR);
		
		ModelAndView mav = controller.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		verify(paymentService).getCumulativePriceFR();
		assertTrue(modelMap.containsAttribute("cumulativePrice"));
		assertSame(PRICE_FR, modelMap.get("cumulativePrice"));
	}
	
	@Test
	public void modeOfPayment_should_return_error_page_when_user_is_connected_and_getCumulativePriceFR_raise_NoTicketsInCartException() throws NoTicketsInCartException {
		when(currentUser.isLogged()).thenReturn(true);
		when(paymentService.getCumulativePriceFR()).thenThrow(new NoTicketsInCartException());
		
		ModelAndView mav = controller.modeOfPayment();
		
		assertEquals(ERROR_PAGE, mav.getViewName());
	}
	
	@Test
	public void modeOfPayment_should_add_errorMessage_in_model_when_user_is_connected_and_getCumulativePriceFR_raise_NoTicketsInCartException() throws NoTicketsInCartException {
		when(currentUser.isLogged()).thenReturn(true);
		when(paymentService.getCumulativePriceFR()).thenThrow(new NoTicketsInCartException());
		
		ModelAndView mav = controller.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("errorMessage"));
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
	public void validate_should_return_error_page_when_user_isnt_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(false);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		
		assertEquals(ERROR_PAGE, mav.getViewName());
	}
	
	@Test
	public void validate_should_add_errorMessage_when_user_isnt_connected() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(false);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("errorMessage"));
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
	public void validate_should_add_paymentForm_when_errors_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(true);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("paymentForm"));
		assertSame(paymentVM, modelMap.get("paymentForm"));
	}
	
	@Test
	public void validate_should_add_creditCardTypes_when_errors_in_BindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		List<CreditCardType> creditCardTypes = new ArrayList<>();
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(true);
		when(paymentService.getCreditCardTypes()).thenReturn(creditCardTypes);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("creditCardTypes"));
		assertSame(creditCardTypes, modelMap.get("creditCardTypes"));
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
	
	@Test
	public void validate_should_add_cumulativePrice_in_model_when_user_is_connected_and_no_error_in_BindingResult() throws NoTicketsInCartException {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(paymentService.getCumulativePriceFR()).thenReturn(PRICE_FR);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("cumulativePrice"));
		assertEquals(PRICE_FR, modelMap.get("cumulativePrice"));
	}
	
	@Test
	public void validate_should_return_error_page_when_user_is_connected_and_no_error_in_BindingResult_and_getCumulativePriceFR_raise_NoTicketsInCartException() throws NoTicketsInCartException {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(paymentService.getCumulativePriceFR()).thenThrow(new NoTicketsInCartException());
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		
		assertEquals(ERROR_PAGE, mav.getViewName());
	}
	
	@Test
	public void validate_should_add_errorMessage_in_model_when_user_is_connected_and_no_error_in_BindingResult_and_getCumulativePriceFR_raise_NoTicketsInCartException() throws NoTicketsInCartException {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(paymentService.getCumulativePriceFR()).thenThrow(new NoTicketsInCartException());
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("errorMessage"));
	}
	
	@Test
	public void validate_should_pay_amount_when_user_is_connected_and_no_error_in_BindingResult() throws InvalidCreditCardException {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		controller.validate(paymentVM, bindingResult);
		
		verify(paymentService).buyTicketsInCart(paymentVM);
	}
	
	@Test
	public void validate_should_return_mode_of_ayment_page_when_user_is_connected_and_no_error_in_BindingResult_and_payAmount_raise_InvalidCardException() throws InvalidCreditCardException {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		doThrow(new InvalidCreditCardException()).when(paymentService).buyTicketsInCart(paymentVM);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		
		assertEquals(MODE_OF_PAYMENT_PAGE, mav.getViewName());
	}
	
	@Test
	public void validate_should_add_paymentForm_in_model_when_user_is_connected_and_no_error_in_BindingResult_and_payAmount_raise_InvalidCardException() throws InvalidCreditCardException {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		doThrow(new InvalidCreditCardException()).when(paymentService).buyTicketsInCart(paymentVM);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("paymentForm"));
		assertSame(paymentVM, modelMap.get("paymentForm"));
	}
	
	@Test
	public void validate_should_add_creditCardTypes_in_model_when_user_is_connected_and_no_error_in_BindingResult_and_payAmount_raise_InvalidCardException() throws InvalidCreditCardException {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		List<CreditCardType> creditCardTypes = new ArrayList<>();
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		doThrow(new InvalidCreditCardException()).when(paymentService).buyTicketsInCart(paymentVM);
		when(paymentService.getCreditCardTypes()).thenReturn(creditCardTypes);
		
		ModelAndView mav = controller.validate(paymentVM, bindingResult);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("creditCardTypes"));
		assertSame(creditCardTypes, modelMap.get("creditCardTypes"));
	}
	
	@Test
	public void validate_should_empty_cart_when_user_is_connected_and_no_error_in_BindingResult() throws InvalidCreditCardException {
		BindingResult bindingResult = mock(BindingResult.class);
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		
		when(currentUser.isLogged()).thenReturn(true);
		when(bindingResult.hasErrors()).thenReturn(false);
		
		controller.validate(paymentVM, bindingResult);
		
		verify(paymentService).emptyCart();
	}
}
