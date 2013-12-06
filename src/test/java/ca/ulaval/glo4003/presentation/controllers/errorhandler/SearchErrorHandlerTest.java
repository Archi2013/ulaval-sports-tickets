package ca.ulaval.glo4003.presentation.controllers.errorhandler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

@RunWith(MockitoJUnitRunner.class)
public class SearchErrorHandlerTest {
	
	private static final String AN_ERROR_MESSAGE = "Some Error Messages";
	
	@Mock
	private MessageSource messageSource;
	
	@Mock
	private ModelAndView modelAndView;
	
	@InjectMocks
	private SearchErrorHandler searchErrorHandler;

	@Before
	public void setUp() throws Exception {
		when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class))).thenReturn(AN_ERROR_MESSAGE);
	}

	@Test
	public void testAddErrorMessageUserPreferencesNotSaved() throws Exception {
		searchErrorHandler.addErrorMessageUserPreferencesNotSaved(modelAndView);
		verify(modelAndView, times(1)).addObject("errorMessage", AN_ERROR_MESSAGE);
	}

}
