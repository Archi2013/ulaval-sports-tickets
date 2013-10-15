package ca.ulaval.glo4003.domain.utilities;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;


@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class User {
		
	  private String username;
	  private String password;
	  private Boolean isLogged;
	  
	public Boolean isLogged() {
		return isLogged;
	}

	public void setIsLogged(Boolean isLogged) {
		this.isLogged = isLogged;
	}

	public User(){
		this.isLogged=false;
	}
	 
	  public User(String username, String password) {
		this.username=username;
		this.password=password;
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
	}


