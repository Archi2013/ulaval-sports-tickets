package ca.ulaval.glo4003.domain.dtos;

import java.io.Serializable;

public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private Boolean logged;
	private Boolean admin;

	public UserDto(String name, String password){
		this.username = name;
		this.password = password;
		this.logged = false;
		this.setAdmin(false);
	}
	
	public UserDto(String name, String password, Boolean admin)
	{
		this(name, password);
		this.setAdmin(admin);
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public Boolean isLogged(){
		return this.logged;
	}
	
	public void setLogged(Boolean value){
		this.logged = value;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
}
