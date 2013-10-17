package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

//@Repository
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
	public GameDto get(int id) throws GameDoesntExistException {
		return database.getGame(id);
	}

	@Override
	public void add(GameDto game) {
		System.out.println("Le nom du sport: " + game.getSportName());
		SportDto sport = database.getSport(game.getSportName());
		sport.getGames().add(game);
	}

	@Override
	public void saveChanges(GameDto game) throws GameDoesntExistException {
		// TODO Auto-generated method stub

	}
}
