package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

import ca.ulaval.glo4003.constants.CreditCardType;
import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.presentation.controllers.errorhandler.CartErrorHandler;
import ca.ulaval.glo4003.presentation.controllers.errorhandler.PaymentErrorHandler;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.PayableItemsViewModelFactory;
import ca.ulaval.glo4003.services.CartService;
import ca.ulaval.glo4003.services.CommandPaymentService;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;
import ca.ulaval.glo4003.utilities.Calculator;
import ca.ulaval.glo4003.utilities.Constants;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
	
	private static final String CART_DETAIL_PAGE = "cart/details";
	
	@Mock
	CartService cartService;
	
	@Mock
	private PayableItemsViewModelFactory payableItemsViewModelFactory;
	
	@Mock
	private CartErrorHandler cartErrorManager;
	
	@Mock
	private User currentUser;
	
	@InjectMocks
	private CartController cartController;
	
	@Before
	public void setUp() {
		when(currentUser.isLogged()).thenReturn(true);
	}
	
	@Test
	public void showDetails_should_return_the_good_view() {
		ModelAndView mav = cartController.showDetails();
		
		assertEquals(CART_DETAIL_PAGE, mav.getViewName());
	}
	
	@Test
	public void when_currentUser_isntLogged_showDetails_should_use_cartErrorManager_to_return_viewModel() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = cartController.showDetails();
		
		verify(cartErrorManager).prepareErrorPageToShowNotConnectedUserMessage(mav);
	}
	
	@Test
	public void showDetails_should_add_currency_to_modelAndView() {
		ModelAndView mav = cartController.showDetails();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("currency"));
		assertEquals(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void showDetails_should_add_payableItems_to_modelAndView() {
		PayableItemsViewModel payableItemsVM = mock(PayableItemsViewModel.class);
		
		when(payableItemsViewModelFactory.createViewModel(anySet(), anyDouble())).thenReturn(payableItemsVM);
		
		ModelAndView mav = cartController.showDetails();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("payableItems"));
		assertEquals(payableItemsVM, modelMap.get("payableItems"));
	}
	
	@Test
	public void when_NoTicketsInCartException_showDetails_should_use_cartErrorManager_to_return_viewModel() throws NoTicketsInCartException {
		NoTicketsInCartException exception = new NoTicketsInCartException();
		
		when(cartService.getSectionsInCart()).thenThrow(exception);
		
		ModelAndView mav = cartController.showDetails();
		
		verify(cartErrorManager).prepareErrorPage(mav, exception);
	}
}
