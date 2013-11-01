package ca.ulaval.glo4003.domain.utilities;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.persistence.daos.SportDao;

@Component
public class SportUrlMapperWithGeneration implements SportUrlMapper {
	
	@Inject
	SportDao sportDao;

	//@Override
	public String getSportUrl(String sportName) {
		String nfdNormalizedString = Normalizer.normalize(sportName, Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    String result = pattern.matcher(nfdNormalizedString).replaceAll("");
	    result = result.toLowerCase(Locale.ENGLISH);
	    result = result.replace("œ", "oe");
	    result = result.replaceAll("[^a-zA-Z0-9-]", "-").replaceAll("-{2,}", "-");
	    return result;
	}

	//@Override
	public String getSportName(String sportUrl) throws NoSportForUrlException {
		List<String> sports = sportDao.getAllSportNames();
		for (String sport : sports) {
			if (getSportUrl(sport).equals(sportUrl)) {
				return sport;
			}
		}
		throw new NoSportForUrlException();
	}
}
