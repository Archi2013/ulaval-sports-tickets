package ca.ulaval.glo4003.domain.utilities;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.persistence.daos.SportDao;

@Component
public class SportUrlMapperWithGeneration extends UrlMapper implements SportUrlMapper {
	
	@Inject
	SportDao sportDao;

	@Override
	public String getUrl(String sportName) {
		return createUrl(sportName);
	}

	@Override
	public String getSportName(String sportUrl) throws NoSportForUrlException {
		List<String> sports = sportDao.getAllSportNames();
		for (String sport : sports) {
			if (getUrl(sport).equals(sportUrl)) {
				return sport;
			}
		}
		throw new NoSportForUrlException();
	}
}
