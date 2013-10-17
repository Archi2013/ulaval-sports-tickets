package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.utilities.UserDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.UserDao;

//@Repository
public class FakeDataUserDao implements UserDao {

	private List<UserDto> userList;

	public FakeDataUserDao() {
		this.userList = new ArrayList<UserDto>();
		addUser("marco", "1234");
		addUser("math", "4321");
	}

	@Override
	public List<UserDto> getAllUser() {
		return this.userList;
	}

	@Override
	public UserDto getUser(String name) throws UserDoesntExistException {

		for (UserDto user : this.userList) {
			if (name.equals(user.getUsername())) {
				return user;
			}
		}
		throw new UserDoesntExistException();
	}

	@Override
	public void addUser(String name, String password) {
		if (!(doesUserExist(name)))
			this.userList.add(new UserDto(name, password));
	}

	@Override
	public boolean doesUserExist(String name) {

		for (UserDto user : this.userList) {
			if (name.equals(user.getUsername())) {
				return true;
			}
		}
		return false;
	}
}
