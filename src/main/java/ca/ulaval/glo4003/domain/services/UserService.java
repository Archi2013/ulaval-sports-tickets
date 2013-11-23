package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.users.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.users.UserDoesntExistException;
import ca.ulaval.glo4003.domain.users.UsernameAndPasswordDoesntMatchException;
import ca.ulaval.glo4003.domain.utilities.users.Encryption;
import ca.ulaval.glo4003.persistence.daos.UserDao;
import ca.ulaval.glo4003.presentation.viewmodels.UserViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.UserViewModelFactory;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

	@Autowired
	public User currentUser;

	@Inject
	private UserDao userDao;
	
	@Inject
	private UserViewModelFactory userViewModelFactory;
	
	@Inject
	private Encryption encryption;

	private void setCurrentUser(UserDto user) {
		currentUser.setUsername(user.getUsername());
		currentUser.setPassword(user.getPassword());
		currentUser.setLogged(true);
		currentUser.setAdmin(user.isAdmin());
	}

	public void signUp(String username, String password) throws UserAlreadyExistException {
		
		if (!(userDao.doesUserExist(username))) {
			userDao.add(makeUser(username, password));
			userDao.commit();
		} else {
			throw new UserAlreadyExistException();
		}
	}
	
	private UserDto makeUser(String username, String password) {
		return new UserDto(username, encryption.encrypt(password));
	}

	public UserViewModel signIn(String username, String password) throws UserDoesntExistException,
			UsernameAndPasswordDoesntMatchException {

		UserDto user = userDao.get(username);
		if (encryption.isSamePassword(password, user.getPassword())) {
			setCurrentUser(user);
			UserViewModel userViewModel = userViewModelFactory.createViewModel(user);
			return userViewModel;
		} else
			throw new UsernameAndPasswordDoesntMatchException();
	}

	public void logOutCurrentUser() {
		currentUser.setPassword("");
		currentUser.setUsername("");
		currentUser.setLogged(false);
	}

}
