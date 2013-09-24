package ca.ulaval.glo4003.data_access.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.dtos.SportDto;

@Service
@Singleton
public class FakeDatabase {

	private Map<String, SportDto> sports;

	public FakeDatabase() {
		this.sports = createSports();
	}

	public List<SportDto> getSports() {
		return new ArrayList<SportDto>(sports.values());
	}

	public SportDto getSport(String sportName) {
		return sports.get(sportName);
	}

	private Map<String, SportDto> createSports() {
		Map<String, SportDto> sports = new HashMap<String, SportDto>();
		SportDto hockey = new SportDto("Hockey");
		SportDto baseball = new SportDto("Baseball");
		sports.put("Hocket", hockey);
		sports.put("Baseball", baseball);
		return sports;
	}
}
