package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.users.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.users.UserDoesntExistException;
import ca.ulaval.glo4003.domain.users.UsernameAndPasswordDoesntMatchException;
import ca.ulaval.glo4003.presentation.viewmodels.UserViewModel;
import ca.ulaval.glo4003.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class SessionControllerTest {

	private static final String VALID_USER = "Martin";
	private static final String INVALID_USER = "Roger";
	private static final String VALID_PASSWORD = "ValidPassword";
	private static final String INVALID_PASSWORD = "InvalidPassword";
	
	@Mock
	private UserService userService;
	
	@Mock
	private User currentUser;
	
	@Mock
	private Model model;
	
	@Mock
	private SessionStatus sessionStatus;
	
	@InjectMocks
	private SessionController controller;
	
	@Test
	public void signIn_should_return_logged_when_currentUser_is_logged() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.signIn();
		
		assertEquals("session/logged", mav.getViewName());
	}
	
	@Test
	public void signIn_should_return_signin_when_currentUser_is_not_logged() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.signIn();
		
		assertEquals("session/signin", mav.getViewName());
	}
	
	@Test
	public void signUp_should_return_logged_when_currentUser_is_logged() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.signUp();
		
		assertEquals("session/logged", mav.getViewName());
	}
	
	@Test
	public void signUp_should_return_signup_when_currentUser_is_not_logged() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.signUp();
		
		assertEquals("session/signup", mav.getViewName());
	}
	
	@Test
	public void submitSignIn_should_return_success_when_user_exists_and_credentials_match() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		UserViewModel userViewModel = new UserViewModel(VALID_USER,VALID_PASSWORD);
		when(userService.signIn(VALID_USER, VALID_PASSWORD)).thenReturn(userViewModel);
		
		ModelAndView mav = controller.submitSignIn(model, VALID_USER,VALID_PASSWORD);

		assertEquals("session/success", mav.getViewName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void submitSignIn_should_return_retry_when_user_dont_exists() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		when(userService.signIn(INVALID_USER, INVALID_PASSWORD)).thenThrow(UserDoesntExistException.class);
		
		ModelAndView mav = controller.submitSignIn(model, INVALID_USER, INVALID_PASSWORD);

		assertEquals("session/retry", mav.getViewName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void submitSignIn_should_return_retry_when_user_exists_and_credentials_dont_match() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		when(userService.signIn(VALID_USER, INVALID_PASSWORD)).thenThrow(UsernameAndPasswordDoesntMatchException.class);
		
		ModelAndView mav = controller.submitSignIn(model, VALID_USER, INVALID_PASSWORD);

		assertEquals("session/retry", mav.getViewName());
	}
	
	@Test
	public void submitSignIn_should_add_userViewModel_to_model_when_success() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		UserViewModel userViewModel = new UserViewModel(VALID_USER,VALID_PASSWORD);
		when(userService.signIn(VALID_USER, VALID_PASSWORD)).thenReturn(userViewModel);
		
		ModelAndView mav = controller.submitSignIn(model, VALID_USER,VALID_PASSWORD);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("user"));
		assertSame(userViewModel, modelMap.get("user"));
	}
	
	@Test
	public void submitSignIn_should_add_currentUser_to_model_as_attribute_for_session() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		controller.submitSignIn(model, VALID_USER,VALID_PASSWORD);
		
		verify(model).addAttribute("currentUser", currentUser);
	}
	
	@Test
	public void registerUser_should_return_signin_when_user_dont_already_exist() throws UserAlreadyExistException{
		ModelAndView mav = controller.registerUser(model, VALID_USER,VALID_PASSWORD);

		assertEquals("session/success", mav.getViewName());
	}
	
	@Test
	public void registerUser_should_signUp_in_userService() throws UserAlreadyExistException{
		controller.registerUser(model, VALID_USER,VALID_PASSWORD);

		verify(userService).signUp(VALID_USER,VALID_PASSWORD);
	}
	
	@Test
	public void registerUser_should_return_exist_when_user_already_exist() throws UserAlreadyExistException{
		doThrow(UserAlreadyExistException.class).when(userService).signUp(INVALID_USER,VALID_PASSWORD);
		
		ModelAndView mav = controller.registerUser(model, INVALID_USER,VALID_PASSWORD);

		assertEquals("session/exist", mav.getViewName());
	}
	
	@Test
	public void logoutUser_should_return_logout_when_success() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		ModelAndView mav = controller.logoutUser(sessionStatus);

		assertEquals("session/logout", mav.getViewName());
	}
	
	@Test
	public void logoutUser_should_logoutUser_in_userService() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		controller.logoutUser(sessionStatus);

		verify(userService).logOutCurrentUser();
	}
	
	@Test
	public void showCurrentUser_should_return_success_when_currentUser_is_logged() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.showCurrentUser();
		
		assertEquals("session/success", mav.getViewName());
	}
	
	@Test
	public void showCurrentUser_should_return_logout_when_currentUser_is_not_logged() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.showCurrentUser();
		
		assertEquals("session/logout", mav.getViewName());
	}
}
