package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.utilities.UserDoesntExistException;

@Repository
public interface UserDao {
	public List<UserDto> getAllUser();
	public UserDto getUser(String name) throws UserDoesntExistException;
	public void addUser(String name, String password);
	boolean doesUserExist(String name);
}

