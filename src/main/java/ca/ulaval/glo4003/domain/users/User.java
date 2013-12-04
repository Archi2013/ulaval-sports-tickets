package ca.ulaval.glo4003.domain.users;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@SessionAttributes({ "currentUser" })
public class User {

	private String username;
	private String password;
	private Boolean logged;
	private Boolean admin;


	public User() {
		this.logged = false;
		this.setAdmin(false);
		
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Boolean isLogged() {
		return logged;
	}

	public void setLogged(Boolean logged) {
		this.logged = logged;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;

	}

	public Boolean isAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
}
