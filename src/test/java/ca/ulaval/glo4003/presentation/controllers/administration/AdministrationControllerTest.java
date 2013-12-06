package ca.ulaval.glo4003.presentation.controllers.administration;

import static org.junit.Assert.*;
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
public class AdministrationControllerTest {
	
	private static final String ADMIN_UNAUTHORIZED_PAGE = "admin/unauthorized";
	
	private static final String REDIRECT_ADMIN_UNAUTHORIZED_PAGE = "redirect:/admin/sans-autorisation";

	@Mock
	private User currentUser;
	
	@Mock
	private HttpSession session;
	
	@InjectMocks
	private AdministrationController controller;
	
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
	public void home_should_redirect_to_unauthorized_page_when_not_logged() {
		when(currentUser.isAdmin()).thenReturn(false);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home(session);

		assertEquals(REDIRECT_ADMIN_UNAUTHORIZED_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_redirect_to_unauthorized_page_when_not_admin() {
		when(currentUser.isAdmin()).thenReturn(false);
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.home(session);

		assertEquals(REDIRECT_ADMIN_UNAUTHORIZED_PAGE, mav.getViewName());
	}
	
	@Test
	public void unauthorized_should_return_the_good_page() {
		String actual = controller.unauthorized();
		
		assertEquals(ADMIN_UNAUTHORIZED_PAGE, actual);
	}
}
