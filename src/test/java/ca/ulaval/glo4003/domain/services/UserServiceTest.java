package ca.ulaval.glo4003.domain.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.users.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.users.UserDoesntExistException;
import ca.ulaval.glo4003.domain.users.UsernameAndPasswordDoesntMatchException;
import ca.ulaval.glo4003.domain.utilities.users.Encryption;
import ca.ulaval.glo4003.persistence.daos.UserDao;
import ca.ulaval.glo4003.presentation.viewmodels.UserViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.UserViewModelFactory;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	private static final String VALID_USER = "Martin";
	private static final String INVALID_USER = "Roger";
	private static final String VALID_PASSWORD = "admin";
	private static final String VALID_ENCRYPTED_PASSWORD = "8omCTTvQa3k0hbJ2WfwTNA1ZguhcFIB0sTm2C3hDuP4BPCihnzJXXVna06uUf01A";
	private static final String INVALID_PASSWORD = "mot-de-passe-invalide";
	
	@Mock
	private User currentUser;
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private UserViewModelFactory userViewModelFactory;
	
	@Mock
	private Encryption encryption;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	public void signUp_should_addUser_in_userDao_when_user_dont_exist() throws UserAlreadyExistException {
		when(userDao.doesUserExist(VALID_USER)).thenReturn(false);
		
		userService.signUp(VALID_USER, VALID_PASSWORD);
		
		verify(userDao).add(any(UserDto.class));
	}
	
	@Test(expected=UserAlreadyExistException.class)
	public void signUp_should_throw_exception_when_user_already_exist() throws UserAlreadyExistException {
		when(userDao.doesUserExist(INVALID_USER)).thenReturn(true);
		
		userService.signUp(INVALID_USER, VALID_PASSWORD);
	}
	
	@Test
	public void signIn_should_return_userViewModel_when_user_exist_and_credentials_match() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		UserDto userDto = new UserDto(VALID_USER,VALID_ENCRYPTED_PASSWORD);
		UserViewModel userViewModel = new UserViewModel(VALID_USER,VALID_ENCRYPTED_PASSWORD);
		when(userDao.get(VALID_USER)).thenReturn(userDto);
		when(userViewModelFactory.createViewModel(userDto)).thenReturn(userViewModel);
		when(encryption.isSamePassword(VALID_PASSWORD, VALID_ENCRYPTED_PASSWORD)).thenReturn(true);
		
		UserViewModel userViewModelCompared = userService.signIn(VALID_USER,VALID_PASSWORD);
		
		assertEquals(userViewModelCompared,userViewModel);
	}
	
	@Test(expected=UsernameAndPasswordDoesntMatchException.class)
	public void signIn_should_return_UsernameAndPasswordDoesntMatchException_when_user_exist_and_credentials_dont_match() 
			throws UserDoesntExistException,UsernameAndPasswordDoesntMatchException {
		UserDto userDto = new UserDto(VALID_USER,VALID_ENCRYPTED_PASSWORD);
		UserViewModel userViewModel = new UserViewModel(VALID_USER,VALID_ENCRYPTED_PASSWORD);
		when(userDao.get(VALID_USER)).thenReturn(userDto);
		when(userViewModelFactory.createViewModel(userDto)).thenReturn(userViewModel);
		when(encryption.isSamePassword(INVALID_PASSWORD, VALID_ENCRYPTED_PASSWORD)).thenReturn(false);
		
		userService.signIn(VALID_USER,INVALID_PASSWORD);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=UserDoesntExistException.class)
	public void signIn_should_get_UserDoesntExistException_error_when_user_dont_exist() throws UserDoesntExistException,
	UsernameAndPasswordDoesntMatchException {
		when(userDao.get(INVALID_USER)).thenThrow(UserDoesntExistException.class);
		
		userService.signIn(INVALID_USER,INVALID_PASSWORD);
	}
	
	@Test
	public void logOutCurrentUser_should_modify_data_on_currentUser() {
		userService.logOutCurrentUser();
		
		verify(currentUser, times(1)).setUsername("");
		verify(currentUser, times(1)).setPassword("");
		verify(currentUser, times(1)).setLogged(false);
	}
}
