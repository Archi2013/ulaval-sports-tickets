package ca.ulaval.glo4003.web.viewmodels;

public class UserViewModel {
	public String username;
	public String password;
	
	public UserViewModel(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}