package ca.ulaval.glo4003.presentation.controllers.errorhandler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.services.exceptions.InvalidTicketsException;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;
import ca.ulaval.glo4003.services.exceptions.TicketsNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class CartErrorHandlerTest {

	private static final String AN_ERROR_MESSAGE = "Some Error Messages";

	@Mock
	ModelAndView modelAndView;

	@Mock
	MessageSource messageSource = mock(MessageSource.class);
	
	@InjectMocks
	CartErrorHandler cartErrorHandler;

	@Before
	public void setUp() throws Exception {
		when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class))).thenReturn(AN_ERROR_MESSAGE);
	}

	@Test
	public void testPrepareErrorPageInvalidTicketsException() throws Exception {
		Exception exception = new InvalidTicketsException();

		cartErrorHandler.prepareErrorPage(modelAndView, exception);
		verify(modelAndView, times(1)).setViewName("cart/error-page");
		verify(modelAndView, times(1)).addObject("errorMessage", AN_ERROR_MESSAGE);
	}
	
	@Test
	public void testPrepareErrorPageTicketsNotFoundException() throws Exception {
		Exception exception = new TicketsNotFoundException(); 

		cartErrorHandler.prepareErrorPage(modelAndView, exception);
		verify(modelAndView, times(1)).setViewName("cart/error-page");
		verify(modelAndView, times(1)).addObject("errorMessage", AN_ERROR_MESSAGE);
	}
	
	@Test
	public void testPrepareErrorPageNoTicketsInCartException() throws Exception {
		Exception exception = new NoTicketsInCartException(); 

		cartErrorHandler.prepareErrorPage(modelAndView, exception);
		verify(modelAndView, times(1)).setViewName("cart/error-page");
		verify(modelAndView, times(1)).addObject("errorMessage", AN_ERROR_MESSAGE);
	}
	
	@Test
	public void testPrepareErrorPageException() throws Exception {
		Exception exception = new Exception();

		cartErrorHandler.prepareErrorPage(modelAndView, exception);
		verify(modelAndView, times(1)).setViewName("cart/error-page");
		verify(modelAndView, times(1)).addObject("errorMessage", AN_ERROR_MESSAGE);
	}

	@Test
	public void testPrepareErrorPageToShowNotConnectedUserMessage() throws Exception {
		cartErrorHandler.prepareErrorPageToShowNotConnectedUserMessage(modelAndView);
		verify(modelAndView, times(1)).setViewName("cart/error-page");
		verify(modelAndView, times(1)).addObject("errorMessage", AN_ERROR_MESSAGE);
	}

	@Test
	public void testPrepareErrorPageToShowTraffickedPageMessage() throws Exception {
		cartErrorHandler.prepareErrorPageToShowTraffickedPageMessage(modelAndView);
		verify(modelAndView, times(1)).setViewName("cart/error-page");
		verify(modelAndView, times(1)).addObject("errorMessage", AN_ERROR_MESSAGE);
	}

}
