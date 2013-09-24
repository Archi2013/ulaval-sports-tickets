package ca.ulaval.glo4003.data_access.fake;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.data_access.SportDao;
import ca.ulaval.glo4003.dtos.SportDto;

@Repository
public class FakeDataSportDao implements SportDao {

	@Inject
	private FakeDatabase database;

	@Override
	public List<SportDto> getAll() {
		return database.getSports();
	}

	@Override
	public SportDto get(String sportName) {
		return database.getSport(sportName);
	}

}
