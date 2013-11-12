package ca.ulaval.glo4003.presentation.viewmodels.factories;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.presentation.viewmodels.UserViewModel;

@Component
public class UserViewModelFactory {

	public UserViewModel createViewModel(UserDto userDto) {
		return new UserViewModel(userDto.getUsername(),userDto.getPassword());
	}

}
