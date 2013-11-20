package ca.ulaval.glo4003.domain.utilities.user;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User {

	private String username;
	private String password;
	private Boolean logged;
	private Boolean admin;

	public User() {
		this.logged = false;
		this.admin = false;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Boolean getLogged() {
		return logged;
	}

	public Boolean getAdmin() {
		return admin;
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
