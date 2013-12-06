package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.presentation.controllers.errorhandler.CartErrorHandler;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenGeneralTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenWithSeatTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.services.CartViewService;
import ca.ulaval.glo4003.services.CommandCartService;
import ca.ulaval.glo4003.services.QueryCartService;
import ca.ulaval.glo4003.services.exceptions.InvalidTicketsException;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;
import ca.ulaval.glo4003.services.exceptions.TicketsNotFoundException;
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
	
	@Test
	public void addGeneralTicketsToCart_should_return_the_good_view() {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(false);
		
		ModelAndView mav = cartController.addGeneralTicketsToCart(chosenGeneralTicketsVM, result);
		
		assertEquals(CART_DETAIL_PAGE, mav.getViewName());
	}
	
	@Test
	public void when_currentUser_isntLogged_addGeneralTicketsToCart_should_use_cartErrorManager_to_return_modelAndView() {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(false);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = cartController.addGeneralTicketsToCart(chosenGeneralTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPageToShowNotConnectedUserMessage(mav);
	}
	
	@Test
	public void when_BindingResult_hasErrors_addGeneralTicketsToCart_should_use_cartErrorManager_to_return_modelAndView() {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(true);
		
		ModelAndView mav = cartController.addGeneralTicketsToCart(chosenGeneralTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPageToShowTraffickedPageMessage(mav);
	}
	
	@Test
	public void addGeneralTicketsToCart_should_add_payableItems_to_modelAndView() throws NoTicketsInCartException {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		PayableItemsViewModel payableItemsVM = mock(PayableItemsViewModel.class);
		
		when(result.hasErrors()).thenReturn(false);
		when(cartViewService.getPayableItemsViewModel()).thenReturn(payableItemsVM);
		
		ModelAndView mav = cartController.addGeneralTicketsToCart(chosenGeneralTicketsVM, result);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("payableItems"));
		assertEquals(payableItemsVM, modelMap.get("payableItems"));
	}
	
	@Test
	public void when_NoTicketsInCartException_addGeneralTicketsToCart_should_use_cartErrorHandler() throws NoTicketsInCartException {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		NoTicketsInCartException exception = new NoTicketsInCartException();
		
		when(result.hasErrors()).thenReturn(false);
		when(cartViewService.getPayableItemsViewModel()).thenThrow(exception);
		
		ModelAndView mav = cartController.addGeneralTicketsToCart(chosenGeneralTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPage(mav, exception);
	}
	
	@Test
	public void addGeneralTicketsToCart_should_add_currency_to_modelAndView() {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(false);
		
		ModelAndView mav = cartController.addGeneralTicketsToCart(chosenGeneralTicketsVM, result);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("currency"));
		assertEquals(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void addGeneralTicketsToCart_should_add_informations_for_general_tickets_to_cart() throws InvalidTicketsException, TicketsNotFoundException {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(false);
		
		cartController.addGeneralTicketsToCart(chosenGeneralTicketsVM, result);

		verify(commmandCartService).addGeneralTicketsToCart(anyString(),
				any(DateTime.class), anyString(),
				anyInt());
	}
	
	@Test
	public void when_InvalidTicketsException_addGeneralTicketsToCart_should_use_cartErrorHandler_to_return_modelAndView() throws InvalidTicketsException, TicketsNotFoundException {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		InvalidTicketsException exception = new InvalidTicketsException();
		
		when(result.hasErrors()).thenReturn(false);
		doThrow(exception).when(commmandCartService).addGeneralTicketsToCart(anyString(),
				any(DateTime.class), anyString(),
				anyInt());
		
		ModelAndView mav = cartController.addGeneralTicketsToCart(chosenGeneralTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPage(mav, exception);
	}
	
	@Test
	public void when_TicketsNotFoundException_addGeneralTicketsToCart_should_use_cartErrorHandler_to_return_modelAndView() throws InvalidTicketsException, TicketsNotFoundException {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		TicketsNotFoundException exception = new TicketsNotFoundException();
		
		when(result.hasErrors()).thenReturn(false);
		doThrow(exception).when(commmandCartService).addGeneralTicketsToCart(anyString(),
				any(DateTime.class), anyString(),
				anyInt());
		
		ModelAndView mav = cartController.addGeneralTicketsToCart(chosenGeneralTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPage(mav, exception);
	}
	
	@Test
	public void addWithSeatTicketsToCart_should_return_the_good_view() {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(false);
		
		ModelAndView mav = cartController.addWithSeatTicketsToCart(chosenWithSeatTicketsVM, result);
		
		assertEquals(CART_DETAIL_PAGE, mav.getViewName());
	}
	
	@Test
	public void when_currentUser_isntLogged_addWithSeatTicketsToCart_should_use_cartErrorManager_to_return_modelAndView() {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(false);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = cartController.addWithSeatTicketsToCart(chosenWithSeatTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPageToShowNotConnectedUserMessage(mav);
	}
	
	@Test
	public void when_BindingResult_hasErrors_addWithSeatTicketsToCart_should_use_cartErrorManager_to_return_modelAndView() {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(true);
		
		ModelAndView mav = cartController.addWithSeatTicketsToCart(chosenWithSeatTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPageToShowTraffickedPageMessage(mav);
	}
	
	@Test
	public void addWithSeatTicketsToCart_should_add_payableItems_to_modelAndView() throws NoTicketsInCartException {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		PayableItemsViewModel payableItemsVM = mock(PayableItemsViewModel.class);
		
		when(result.hasErrors()).thenReturn(false);
		when(cartViewService.getPayableItemsViewModel()).thenReturn(payableItemsVM);
		
		ModelAndView mav = cartController.addWithSeatTicketsToCart(chosenWithSeatTicketsVM, result);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("payableItems"));
		assertEquals(payableItemsVM, modelMap.get("payableItems"));
	}
	
	@Test
	public void when_NoTicketsInCartException_addWithSeatTicketsToCart_should_use_cartErrorHandler() throws NoTicketsInCartException {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		NoTicketsInCartException exception = new NoTicketsInCartException();
		
		when(result.hasErrors()).thenReturn(false);
		when(cartViewService.getPayableItemsViewModel()).thenThrow(exception);
		
		ModelAndView mav = cartController.addWithSeatTicketsToCart(chosenWithSeatTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPage(mav, exception);
	}
	
	@Test
	public void addWithSeatTicketsToCart_should_add_currency_to_modelAndView() {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(false);
		
		ModelAndView mav = cartController.addWithSeatTicketsToCart(chosenWithSeatTicketsVM, result);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("currency"));
		assertEquals(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void addWithSeatTicketsToCart_should_add_informations_for_with_seat_tickets_to_cart() throws InvalidTicketsException, TicketsNotFoundException {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		when(result.hasErrors()).thenReturn(false);
		
		cartController.addWithSeatTicketsToCart(chosenWithSeatTicketsVM, result);

		verify(commmandCartService).addWithSeatTicketsToCart(anyString(),
				any(DateTime.class), anyString(),
				anySet());
	}
	
	@Test
	public void when_InvalidTicketsException_addWithSeatTicketsToCart_should_use_cartErrorHandler_to_return_modelAndView() throws InvalidTicketsException, TicketsNotFoundException {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		InvalidTicketsException exception = new InvalidTicketsException();
		
		when(result.hasErrors()).thenReturn(false);
		doThrow(exception).when(commmandCartService).addWithSeatTicketsToCart(anyString(),
				any(DateTime.class), anyString(),
				anySet());
		
		ModelAndView mav = cartController.addWithSeatTicketsToCart(chosenWithSeatTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPage(mav, exception);
	}
	
	@Test
	public void when_TicketsNotFoundException_addWithSeatTicketsToCart_should_use_cartErrorHandler_to_return_modelAndView() throws InvalidTicketsException, TicketsNotFoundException {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		BindingResult result = mock(BindingResult.class);
		
		TicketsNotFoundException exception = new TicketsNotFoundException();
		
		when(result.hasErrors()).thenReturn(false);
		doThrow(exception).when(commmandCartService).addWithSeatTicketsToCart(anyString(),
				any(DateTime.class), anyString(),
				anySet());
		
		ModelAndView mav = cartController.addWithSeatTicketsToCart(chosenWithSeatTicketsVM, result);
		
		verify(cartErrorHandler).prepareErrorPage(mav, exception);
	}
}
