package ca.ulaval.glo4003.data_access;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.Sport;

@Repository
public class FakeDataSportDao implements SportDao {

	@Override
	public List<Sport> getAll() {
		List<Sport> sports = new ArrayList<Sport>();
		Sport hockey = new Sport("Hockey");
		Sport baseball = new Sport("Baseball");
		sports.add(hockey);
		sports.add(baseball);
		return sports;
	}

}
