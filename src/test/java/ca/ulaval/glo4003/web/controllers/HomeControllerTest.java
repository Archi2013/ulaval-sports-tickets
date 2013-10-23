package ca.ulaval.glo4003.web.controllers;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	private static final String A_LOCALE = "Locale";
	@Mock
	private Model model;

	@InjectMocks
	private HomeController controller;

	@Test
	public void homeController_returns_home_view() {
		String viewReturned = controller.home(new Locale(A_LOCALE), model);

		Assert.assertEquals("home", viewReturned);
	}
}
