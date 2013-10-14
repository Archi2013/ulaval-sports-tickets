package ca.ulaval.glo4003.session.beta;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

	@Autowired
	public User currentUser;
	
	@Inject
	private UserDao userDao;
	
	private void setCurrentUser(UserDto user){
		currentUser.setUsername(user.getName());	
		currentUser.setPassword(user.getPassword());
		currentUser.setIsLogged(true);
	}
	
	public void signUp(String username, String password) {
		
		//TODO verifier si username existe
		//TODO Persister
	
	}
	
	public UserDto signIn(String username, String password) throws UserDoesntExistException, UsernameAndPasswordDoesntMatchException{
		
		UserDto user = userDao.getUser(username);
		if( user.getPassword().equals(password)){
			setCurrentUser(user);
			return user;
		}
		else
			throw new UsernameAndPasswordDoesntMatchException();
	}

	
}
