package ca.ulaval.glo4003.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.domain.services.UserService;
import ca.ulaval.glo4003.domain.utilities.User;
import ca.ulaval.glo4003.domain.utilities.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.utilities.UserDoesntExistException;
import ca.ulaval.glo4003.domain.utilities.UsernameAndPasswordDoesntMatchException;
import ca.ulaval.glo4003.web.viewmodels.UserViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SessionControllerTest {

	private static final String VALID_USER = "Martin";
	private static final String INVALID_USER = "Roger";
	private static final String VALID_PASSWORD = "ValidPassword";
	private static final String INVALID_PASSWORD = "InvalidPassword";
	
	@Mock
	private Model model;
	
	@Mock
	private UserService userService;
	
	@Mock
	private User currentUser;
	
	@InjectMocks
	private SessionController controller;
	
	@Test
	public void signIn_should_return_logged_when_currentUser_is_logged() {
		when(currentUser.isLogged()).thenReturn(true);
		
		String path = controller.signIn(model);
		
		assertEquals("session/logged", path);
	}
	
	@Test
	public void signIn_should_return_signin_when_currentUser_is_not_logged() {
		when(currentUser.isLogged()).thenReturn(false);
		
		String path = controller.signIn(model);
		
		assertEquals("session/signin", path);
	}
	
	@Test
	public void signIn_should_add_the_currentUser_to_model() {

		controller.signIn(model);

		verify(model).addAttribute("user", currentUser);
	}
	
	@Test
	public void signUp_should_return_logged_when_currentUser_is_logged() {
		when(currentUser.isLogged()).thenReturn(true);
		
		String path = controller.signUp(model);
		
		assertEquals("session/logged", path);
	}
	
	@Test
	public void signUp_should_return_signup_when_currentUser_is_not_logged() {
		when(currentUser.isLogged()).thenReturn(false);
		
		String path = controller.signUp(model);
		
		assertEquals("session/signup", path);
	}
	
	@Test
	public void signUp_should_add_the_currentUser_to_model() {

		controller.signUp(model);

		verify(model).addAttribute("user", currentUser);
	}
	
	@Test
	public void submitSignIn_should_return_success_when_user_exists_and_credentials_match() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		UserViewModel userViewModel = new UserViewModel(VALID_USER,VALID_PASSWORD);
		when(userService.signIn(VALID_USER, VALID_PASSWORD)).thenReturn(userViewModel);
		
		String path = controller.submitSignIn(VALID_USER,VALID_PASSWORD, model);

		assertEquals("session/success", path);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void submitSignIn_should_return_retry_when_user_dont_exists() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		when(userService.signIn(INVALID_USER, INVALID_PASSWORD)).thenThrow(UserDoesntExistException.class);
		
		String path = controller.submitSignIn(INVALID_USER, INVALID_PASSWORD, model);

		assertEquals("session/retry", path);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void submitSignIn_should_return_retry_when_user_exists_and_credentials_dont_match() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		when(userService.signIn(VALID_USER, INVALID_PASSWORD)).thenThrow(UsernameAndPasswordDoesntMatchException.class);
		
		String path = controller.submitSignIn(VALID_USER, INVALID_PASSWORD, model);

		assertEquals("session/retry", path);
	}
	
	@Test
	public void submitSignIn_should_add_userViewModel_to_model_when_success() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		UserViewModel userViewModel = new UserViewModel(VALID_USER,VALID_PASSWORD);
		when(userService.signIn(VALID_USER, VALID_PASSWORD)).thenReturn(userViewModel);
		
		controller.submitSignIn(VALID_USER,VALID_PASSWORD, model);

		verify(model).addAttribute("user", userViewModel);
	}
	
	@Test
	public void registerUser_should_return_signin_when_user_dont_already_exist() throws UserAlreadyExistException{
		String path = controller.registerUser(VALID_USER,VALID_PASSWORD, model);

		assertEquals("session/signin", path);
	}
	
	@Test
	public void registerUser_should_signUp_in_userService() throws UserAlreadyExistException{
		controller.registerUser(VALID_USER,VALID_PASSWORD, model);

		verify(userService).signUp(VALID_USER,VALID_PASSWORD);
	}
	
	@Test
	public void registerUser_should_return_exist_when_user_already_exist() throws UserAlreadyExistException{
		doThrow(UserAlreadyExistException.class).when(userService).signUp(INVALID_USER,VALID_PASSWORD);
		
		String path = controller.registerUser(INVALID_USER,VALID_PASSWORD, model);

		assertEquals("session/exist", path);
	}
	
	@Test
	public void registerUser_should_add_currentUser_to_model_when_success() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		controller.registerUser(VALID_USER,VALID_PASSWORD, model);

		verify(model).addAttribute("user", currentUser);
	}
	
	@Test
	public void logoutUser_should_return_logout_when_success() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		String path = controller.logoutUser(model);

		assertEquals("session/logout", path);
	}
	
	@Test
	public void logoutUser_should_logOutCurrentUser_in_userService() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		controller.logoutUser(model);

		verify(userService).logOutCurrentUser();
	}
	
	@Test
	public void showCurrentUser_should_return_success_when_currentUser_is_logged() {
		when(currentUser.isLogged()).thenReturn(true);
		
		String path = controller.showCurrentUser(model);
		
		assertEquals("session/success", path);
	}
	
	@Test
	public void showCurrentUser_should_return_logout_when_currentUser_is_not_logged() {
		when(currentUser.isLogged()).thenReturn(false);
		
		String path = controller.showCurrentUser(model);
		
		assertEquals("session/logout", path);
	}
	
	@Test
	public void showCurrentUser_should_add_the_currentUser_to_model() {

		controller.signIn(model);

		verify(model).addAttribute("user", currentUser);
	}
	
}
