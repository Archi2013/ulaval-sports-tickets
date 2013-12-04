package ca.ulaval.glo4003.presentation.controllers;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	private static final String A_LOCALE = "Locale";
	
	@InjectMocks
	private HomeController controller;

	@Test
	public void homeController_returns_home_view() {
		ModelAndView mav = controller.home(new Locale(A_LOCALE));

		Assert.assertEquals("home", mav.getViewName());
	}
}
