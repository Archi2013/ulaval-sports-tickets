package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.users.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.users.UserDoesntExistException;

@Repository
public interface UserDao {
	
	public List<UserDto> getAll();
	public UserDto get(String name) throws UserDoesntExistException;
	public void add(UserDto user) throws UserAlreadyExistException;
	public boolean doesUserExist(String name);
	public void commit();
}

