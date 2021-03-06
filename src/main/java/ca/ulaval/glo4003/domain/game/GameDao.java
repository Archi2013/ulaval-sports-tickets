package ca.ulaval.glo4003.domain.game;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.game.dto.GameDto;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;

@Repository
public interface GameDao {

	public List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException;

	public GameDto get(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public void add(GameDto game) throws GameAlreadyExistException;

	public void commit();

	public void update(GameDto dto) throws GameDoesntExistException;

	public List<GameDto> getFromUserSearchPreference(UserSearchPreferenceDto userSearchPreference);
}
