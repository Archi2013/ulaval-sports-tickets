package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.presentation.controllers.HomeController;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	private static final String A_LOCALE = "Locale";

	@Mock
	private User currentUser;
	
	@InjectMocks
	private HomeController controller;

	@Test
	public void homeController_returns_home_view() {
		ModelAndView mav = controller.home(new Locale(A_LOCALE));

		Assert.assertEquals("home", mav.getViewName());
	}
	
	@Test
	public void when_user_is_logged_home_should_add_connectedUser_at_true() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.home(new Locale(A_LOCALE));
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertTrue((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void when_user_isnt_logged_home_should_add_connectedUser_at_false() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home(new Locale(A_LOCALE));
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertFalse((Boolean) modelMap.get("connectedUser"));
	}
}
