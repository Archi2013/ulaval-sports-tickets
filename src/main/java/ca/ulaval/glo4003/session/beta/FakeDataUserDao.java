package ca.ulaval.glo4003.session.beta;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ca.ulaval.glo4003.session.beta.UserDto;
import ca.ulaval.glo4003.session.beta.UserDao;
import ca.ulaval.glo4003.session.beta.UserDoesntExistException;

@Repository
public class FakeDataUserDao implements UserDao {

	private List<UserDto> userList;
	
	public FakeDataUserDao(){
		this.userList = new ArrayList<UserDto>();
		
		this.userList.add(new UserDto("marco","1234"));
		this.userList.add(new UserDto("math","4321"));
		
		System.out.println("this.userList="+userList);
	}

	@Override
	public List<UserDto> getAllUser() {
		return this.userList;
	}
	@Override
	public UserDto getUser(String name) throws  UserDoesntExistException{
		
		for (UserDto user : this.userList)
		{
			if(name.equals(user.getName())){
				return user;
			}
		}
		throw new UserDoesntExistException();
	}
	
	
	@Override
	public void AddUser(String name, String password){
		this.userList.add(new UserDto(name,password));
	}
	
}
