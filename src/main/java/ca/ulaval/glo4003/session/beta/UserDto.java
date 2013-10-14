package ca.ulaval.glo4003.session.beta;

import java.io.Serializable;

public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String password;
	private Boolean logged;

	public UserDto(String name, String password){
		this.name = name;
		this.password = password;
		this.logged = false;
	}
	
	public String getName(){
		return this.name;
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
}
