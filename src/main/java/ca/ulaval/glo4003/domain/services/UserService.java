package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.utilities.User;
import ca.ulaval.glo4003.domain.utilities.UserDoesntExistException;
import ca.ulaval.glo4003.domain.utilities.UsernameAndPasswordDoesntMatchException;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.UserDao;
import ca.ulaval.glo4003.web.viewmodels.UserViewModel;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

	@Autowired
	public User currentUser;
	
	@Inject
	private UserDao userDao;
	
	private void setCurrentUser(UserDto user){
		currentUser.setUsername(user.getUsername());	
		currentUser.setPassword(user.getPassword());
		currentUser.setIsLogged(true);
	}
	
	public void signUp(String username, String password) {
		
		//TODO verifier si username existe
		//TODO Persister
	
	}
	
	public UserViewModel signIn(String username, String password) throws UserDoesntExistException, UsernameAndPasswordDoesntMatchException{
		
		UserDto user = userDao.getUser(username);
		if( user.getPassword().equals(password)){
			setCurrentUser(user);
			UserViewModel userViewModel = new UserViewModel(username, password);
			return userViewModel;
		}
		else
			throw new UsernameAndPasswordDoesntMatchException();
	}
	
	public void logOutCurrentUser() {
		currentUser.setPassword("");
		currentUser.setUsername("");
		currentUser.setIsLogged(false);	
	}

	
}
