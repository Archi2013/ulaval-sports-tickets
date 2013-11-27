package ca.ulaval.glo4003.domain.users;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
	
	public List<UserDto> getAll();
	public UserDto get(String name) throws UserDoesntExistException;
	public void add(UserDto user) throws UserAlreadyExistException;
	public boolean doesUserExist(String name);
	public void commit();
}

