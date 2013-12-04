package ca.ulaval.glo4003.presentation.controllers.administration;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
	
	@Mock
	private User currentUser;
	
	@Mock
	private HttpSession session;
	
	@InjectMocks
	private AdminController controller;
	
	@Before
	public void setUp()
	{
		when(session.getAttribute("currentUser")).thenReturn(currentUser);
	}
	
	@Test
	public void home_returns_the_home_administration_view() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.home(session);

		Assert.assertEquals("admin/home", mav.getViewName());
	}
	
	@Test
	public void home_returns_the_home_administration_view_when_not_logged() {
		when(currentUser.isAdmin()).thenReturn(false);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home(session);

		assertTrue(! mav.getViewName().equals("admin/home"));
	}
	
	@Test
	public void home_returns_the_home_administration_view_when_not_admin() {
		when(currentUser.isAdmin()).thenReturn(false);
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.home(session);

		assertTrue(! mav.getViewName().equals("admin/home"));
	}
}
