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
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.presentation.controllers.errorhandler.CartErrorHandler;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.services.CartViewService;
import ca.ulaval.glo4003.services.CommandCartService;
import ca.ulaval.glo4003.services.QueryCartService;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;
import ca.ulaval.glo4003.utilities.Constants;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
	
	private static final String CART_DETAIL_PAGE = "cart/details";
	
	@Mock
	CommandCartService commmandCartService;
	
	@Mock
	QueryCartService queryCartService;
	
	@Mock
	CartViewService cartViewService;
	
	@Mock
	private CartErrorHandler cartErrorHandler;
	
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
		
		verify(cartErrorHandler).prepareErrorPageToShowNotConnectedUserMessage(mav);
	}
	
	@Test
	public void showDetails_should_add_currency_to_modelAndView() {
		ModelAndView mav = cartController.showDetails();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("currency"));
		assertEquals(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void showDetails_should_add_payableItems_to_modelAndView() throws NoTicketsInCartException {
		PayableItemsViewModel payableItemsVM = mock(PayableItemsViewModel.class);
		
		when(cartViewService.getPayableItemsViewModel()).thenReturn(payableItemsVM);
		
		ModelAndView mav = cartController.showDetails();
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("payableItems"));
		assertEquals(payableItemsVM, modelMap.get("payableItems"));
	}
	
	@Test
	public void when_NoTicketsInCartException_showDetails_should_use_cartErrorManager_to_return_viewModel() throws NoTicketsInCartException {
		NoTicketsInCartException exception = new NoTicketsInCartException();
		
		when(cartViewService.getPayableItemsViewModel()).thenThrow(exception);
		
		ModelAndView mav = cartController.showDetails();
		
		verify(cartErrorHandler).prepareErrorPage(mav, exception);
	}
}
