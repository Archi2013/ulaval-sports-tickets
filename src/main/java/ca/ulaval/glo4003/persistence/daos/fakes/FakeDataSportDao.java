package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.persistence.daos.SportAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

//@Repository
public class FakeDataSportDao implements SportDao {

	@Inject
	private FakeDatabase database;

	@Override
	public List<SportDto> getAll() {
		return database.getSports();
	}

	@Override
	public SportDto get(String sportName) throws SportDoesntExistException {
		return database.getSport(sportName);
	}

	@Override
	public void add(SportDto sport) throws SportAlreadyExistException {
		throw new RuntimeException("Cannot add element to fake data");
	}

	@Override
	public void saveChanges(SportDto sport) throws SportDoesntExistException {
		// TODO Auto-generated method stub

	}

}
