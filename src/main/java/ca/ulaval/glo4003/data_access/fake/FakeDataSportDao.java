package ca.ulaval.glo4003.data_access.fake;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.data_access.SportDao;
import ca.ulaval.glo4003.dtos.SportDto;

@Repository
public class FakeDataSportDao implements SportDao {

	@Override
	public List<SportDto> getAll() {
		List<SportDto> sports = new ArrayList<SportDto>();
		SportDto hockey = new SportDto("Hockey");
		SportDto baseball = new SportDto("Baseball");
		sports.add(hockey);
		sports.add(baseball);
		return sports;
	}

	@Override
	public SportDto get(String sportName) {
		return new SportDto("Baseball");
	}

}
