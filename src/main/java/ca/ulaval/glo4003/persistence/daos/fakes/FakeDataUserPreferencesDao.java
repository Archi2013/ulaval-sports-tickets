package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.dtos.UserPreferencesDto;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.UserPreferencesDao;

@Repository
public class FakeDataUserPreferencesDao implements UserPreferencesDao {

	private List<UserPreferencesDto> userPrefList;


	public FakeDataUserPreferencesDao() {
		this.userPrefList = new ArrayList<UserPreferencesDto>();

		List<String> listTicket = new ArrayList<String>();
		List<String> sportsName = new ArrayList<String>();
		sportsName.add("Football");

		add(new UserPreferencesDto("mo", sportsName,
				DisplayedPeriod.ALL, true, listTicket));
		
		add(new UserPreferencesDto("test", sportsName,
				DisplayedPeriod.ONE_DAY, false, listTicket));

	}

	private void add(UserPreferencesDto userPreferencesDto) {
		this.userPrefList.add(userPreferencesDto);

	}

	@Override
	public UserPreferencesDto get(String username) {
		for(int i=0; i < userPrefList.size(); i++)
		{
			if(userPrefList.get(i).username.equals(username))
			{
				return userPrefList.get(i);
			}
		}
		
		return userPrefList.get(0);
	}

	@Override
	public void save(User username, TicketSearchPreferenceDto userPreferences) {
		// TODO Auto-generated method stub

	}

}
