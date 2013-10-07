package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

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

	@Override
	public GameDto get(long id) throws GameDoesntExistException {
		return database.getGame(id);
	}

	@Override
    public void add(GameDto game) {
	    // TODO Auto-generated method stub
	    
    }
}
