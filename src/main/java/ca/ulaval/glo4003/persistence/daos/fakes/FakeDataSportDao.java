package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

@Repository
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

}
