package ca.ulaval.glo4003.session.beta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

	@Autowired
	public User currentUser;
	
	public void signUp(String username, String password) {
		
		//TODO verifier si username existe
		//TODO Persister
		
	}
	
	public void signIn(String username, String password){
		currentUser.setUsername(username);	
		currentUser.setPassword(password);
	}

	public void getCurrentUser() {

	}
}
