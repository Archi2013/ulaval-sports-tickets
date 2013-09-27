package ca.ulaval.glo4003.dao.fake;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.dao.GameDao;
import ca.ulaval.glo4003.dao.SportDoesntExistException;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;

@Repository
public class FakeDataGameDao implements GameDao {

	@Inject
	private FakeDatabase database;

	@Override
	public List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException {
		SportDto sport = database.getSport(sportName);
		if (sport == null) {
			throw new SportDoesntExistException();
		} else {
			return sport.getGames();
		}
	}
}
