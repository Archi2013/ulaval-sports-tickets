package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.constants.CreditCardType;
import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.presentation.controllers.errorhandler.PaymentErrorHandler;
import ca.ulaval.glo4003.presentation.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.services.CartService;
import ca.ulaval.glo4003.services.CommandPaymentService;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;
import ca.ulaval.glo4003.utilities.Calculator;
import ca.ulaval.glo4003.utilities.Constants;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {
	
	private static final int EXPIRATION_YEAR = 2015;

	private static final int EXPIRATION_MONTH = 7;

	private static final String CREDIT_CARD_USER_NAME = "Jean Dupont";

	private static final int SECURITY_CODE = 1234;

	private static final String CREDIT_CARD_NUMBER = "12345678901234";

	private static final Double CUMULATIVE_PRICE = 88.87;

	private static final String MODE_OF_PAYMENT_PAGE = "payment/mode-of-payment";
	
	private static final String VALIDATION_SUCCES_PAGE = "payment/succes";
	
	@Mock
	CommandPaymentService paymentService;
	
	@Mock
	CartService cartService;
	
	@Mock
	private PaymentErrorHandler paymentErrorManager;
	
	@Mock
	private User currentUser;
	
	@InjectMocks
	private PaymentController paymentController;
	
	@Before
	public void setUp() {
		when(currentUser.isLogged()).thenReturn(true);
	}

	@Test
	public void modeOfPayment_should_return_the_good_page() {
		ModelAndView mav = paymentController.modeOfPayment();
		
		assertEquals(MODE_OF_PAYMENT_PAGE, mav.getViewName());
	}

	@Test
	public void when_currentUser_isntLogged_modeOfPayment_should_use_paymentErrorManager_to_return_modelAndView() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = paymentController.modeOfPayment();
		
		verify(paymentErrorManager).prepareErrorPageToShowNotConnectedUserMessage(mav);
	}
	
	@Test
	public void modeOfPayment_should_add_cumulativePrice_to_modelAndView() throws NoTicketsInCartException {
		when(cartService.getCumulativePrice()).thenReturn(CUMULATIVE_PRICE);
		
		ModelAndView mav = paymentController.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("cumulativePrice"));
		assertEquals(Calculator.toPriceFR(CUMULATIVE_PRICE), modelMap.get("cumulativePrice"));
	}
	
	@Test
	public void when_NoTicketsInCartException_modeOfPayment_should_use_paymentErrorManager_to_return_modelAndView() throws NoTicketsInCartException {
		NoTicketsInCartException exception = new NoTicketsInCartException();
		
		when(cartService.getCumulativePrice()).thenThrow(exception);
		
		ModelAndView mav = paymentController.modeOfPayment();
		
		verify(paymentErrorManager).prepareErrorPage(mav, exception);
	}
	
	@Test
	public void modeOfPayment_should_add_currency_to_modelAndView() {
		ModelAndView mav = paymentController.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("currency"));
		assertEquals(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void modeOfPayment_should_add_paymentForm_to_modelAndView() {
		ModelAndView mav = paymentController.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("paymentForm"));
		assertTrue(modelMap.get("paymentForm") instanceof PaymentViewModel);
	}
	
	@Test
	public void modeOfPayment_should_add_creditCardTypes_to_modelAndView() {
		ModelAndView mav = paymentController.modeOfPayment();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("creditCardTypes"));
		assertEquals(CreditCardType.getCreditCardTypes(), modelMap.get("creditCardTypes"));
	}
	
	@Test
	public void validate_should_return_the_good_page() {
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		
		assertEquals(VALIDATION_SUCCES_PAGE, mav.getViewName());
	}
	
	@Test
	public void when_currentUser_isntLogged_validate_should_use_paymentErrorManager_to_return_modelAndView() {
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		
		verify(paymentErrorManager).prepareErrorPageToShowNotConnectedUserMessage(mav);
	}
	
	@Test
	public void when_result_hasErrors_validate_should_add_the_same_paymentForm_to_modelAndView() {
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(true);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("paymentForm"));
		assertSame(paymentVM, modelMap.get("paymentForm"));
	}
	
	@Test
	public void when_result_hasErrors_validate_should_return_mode_of_payment_page() {
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(true);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		
		assertEquals(MODE_OF_PAYMENT_PAGE, mav.getViewName());
	}
	
	@Test
	public void validate_should_add_cumulativePrice_to_modelAndView() throws NoTicketsInCartException {
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(cartService.getCumulativePrice()).thenReturn(CUMULATIVE_PRICE);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("cumulativePrice"));
		assertEquals(Calculator.toPriceFR(CUMULATIVE_PRICE), modelMap.get("cumulativePrice"));
	}
	
	@Test
	public void when_NoTicketsInCartException_validate_should_use_paymentErrorManager_to_return_modelAndView() throws NoTicketsInCartException {
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		NoTicketsInCartException exception = new NoTicketsInCartException();
		
		when(cartService.getCumulativePrice()).thenThrow(exception);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		
		verify(paymentErrorManager).prepareErrorPage(mav, exception);
	}
	
	@Test
	public void validate_should_add_currency_to_modelAndView() {
		PaymentViewModel paymentVM = mock(PaymentViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("currency"));
		assertEquals(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void validate_should_buy_tickets_in_cart() throws InvalidCreditCardException, NoTicketsInCartException {
		PaymentViewModel paymentVM = getInitializedPaymentViewModel();
		BindingResult result = mock(BindingResult.class);
		
		paymentController.validate(paymentVM, result);
		
		verify(paymentService).buyTicketsInCart(CreditCardType.MISTERCARD, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USER_NAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);
	}
	
	@Test
	public void when_buyTicketsInCart_and_NoTicketsInCartException_validate_should_use_paymentErrorManager_to_return_modelAndView() throws InvalidCreditCardException, NoTicketsInCartException {
		PaymentViewModel paymentVM = getInitializedPaymentViewModel();
		BindingResult result = mock(BindingResult.class);
		
		NoTicketsInCartException exception = new NoTicketsInCartException();
		doThrow(exception).when(paymentService).buyTicketsInCart(CreditCardType.MISTERCARD, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USER_NAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		
		verify(paymentErrorManager).prepareErrorPage(mav, exception);
	}
	
	@Test
	public void when_InvalidCreditCardException_validate_should_return_mode_of_payment_page() throws InvalidCreditCardException, NoTicketsInCartException {
		PaymentViewModel paymentVM = getInitializedPaymentViewModel();
		BindingResult result = mock(BindingResult.class);

		doThrow(new InvalidCreditCardException()).when(paymentService).buyTicketsInCart(CreditCardType.MISTERCARD, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USER_NAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		
		assertEquals(MODE_OF_PAYMENT_PAGE, mav.getViewName());
	}
	
	@Test
	public void when_InvalidCreditCardException_validate_should_add_an_error_message_to_modelAndView() throws InvalidCreditCardException, NoTicketsInCartException {
		PaymentViewModel paymentVM = getInitializedPaymentViewModel();
		BindingResult result = mock(BindingResult.class);

		doThrow(new InvalidCreditCardException()).when(paymentService).buyTicketsInCart(CreditCardType.MISTERCARD, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USER_NAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);
		
		ModelAndView mav = paymentController.validate(paymentVM, result);
		
		verify(paymentErrorManager).addErrorMessageInvalidCreditCardToModel(mav);
	}
	
	private PaymentViewModel getInitializedPaymentViewModel() {
		PaymentViewModel paymentVM = new PaymentViewModel();
		paymentVM.setCreditCardType(CreditCardType.MISTERCARD);
		paymentVM.setCreditCardNumber(CREDIT_CARD_NUMBER);
		paymentVM.setSecurityCode(SECURITY_CODE);
		paymentVM.setCreditCardUserName(CREDIT_CARD_USER_NAME);
		paymentVM.setExpirationMonth(EXPIRATION_MONTH);
		paymentVM.setExpirationYear(EXPIRATION_YEAR);
		return paymentVM;
	}
}
