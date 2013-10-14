package ca.ulaval.glo4003.session.beta;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.session.beta.UserDto;

@Repository
public interface UserDao {
	public List<UserDto> getAllUser();
	public UserDto getUser(String name) throws UserDoesntExistException;
	public void AddUser(String name, String password);
}

