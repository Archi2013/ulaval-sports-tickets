package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.utilities.user.UserDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.UserDao;

//@Repository
public class FakeDataUserDao implements UserDao {

	private List<UserDto> userList;

	public FakeDataUserDao() {
		this.userList = new ArrayList<UserDto>();
		add(new UserDto("marco", "1234"));
		add(new UserDto("math", "4321"));
	}

	@Override
	public List<UserDto> getAll() {
		return this.userList;
	}

	@Override
	public UserDto get(String name) throws UserDoesntExistException {

		for (UserDto user : this.userList) {
			if (name.equals(user.getUsername())) {
				return user;
			}
		}
		throw new UserDoesntExistException();
	}

	@Override
	public void add(UserDto user) {
		if (!(doesUserExist(user.getUsername())))
			this.userList.add(user);
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

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}
}
