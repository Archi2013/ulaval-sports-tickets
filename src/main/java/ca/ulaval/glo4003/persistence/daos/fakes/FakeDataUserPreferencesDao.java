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
import ca.ulaval.glo4003.domain.utilities.user.UserDoesntExistException;
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
	public UserPreferencesDto get(String username) throws UserDoesntHaveSavedPreferences {
		
		
		for(UserPreferencesDto userPref: this.userPrefList)
		{
			if(userPref.username.equals(username))
			{
				return userPref;
			}
		}
		
		throw new UserDoesntHaveSavedPreferences();
	}

	@Override
	public void save(User currentUser, TicketSearchPreferenceDto userPreferences) {
		
		DisplayedPeriod DispPeriod=DisplayedPeriod.valueOf(userPreferences.getDisplayedPeriod());
		UserPreferencesDto userPrefDto = new UserPreferencesDto(currentUser.getUsername(), userPreferences.selectedSports, DispPeriod, userPreferences.localGameOnly, userPreferences.selectedTicketKinds);
		
		int index=indexOfUserPositionInUserPreferencesList(currentUser.getUsername());

		if(index>=0){
			//Overwrite current preferences
			userPrefList.remove(index);
			userPrefList.add(index, userPrefDto);
		}
		else{
			//Add preferences (never been saved before for this user)
			userPrefList.add(userPrefDto);

		}
	}
	
	private int indexOfUserPositionInUserPreferencesList(String username){
		for(int i=0;i<this.userPrefList.size();i++){
			if(userPrefList.get(i).username.equals(username))
			{
				return i;
			}
		}
		return -1;
	}

	public void commit(){
		
	}
}
