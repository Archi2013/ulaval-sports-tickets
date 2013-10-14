package ca.ulaval.glo4003.session.beta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ca.ulaval.glo4003.session.beta.UserDto;
import ca.ulaval.glo4003.session.beta.UserDao;
import ca.ulaval.glo4003.session.beta.UserDoesntExistException;

@Repository
public class FakeDataUserDao implements UserDao {

	private List<UserDto> userList;
	

	@Override
	public List<UserDto> getAllUser() {
		return this.userList;
	}
	@Override
	public UserDto getUser(String name) throws  UserDoesntExistException{
		for (UserDto user : this.userList)
		{
			if(user.getName() == name)
				return user;
		}
		throw new UserDoesntExistException();
	}
	@Override
	public void AddUser(String name, String password){
		this.userList.add(new UserDto(name,password));
	}
	
}
