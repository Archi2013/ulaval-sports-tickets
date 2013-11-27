package ca.ulaval.glo4003.domain.sections;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;

@Repository
public class SectionRepository implements ISectionRepository {

	@Inject
	private SectionDao sectionDao;

	@Inject
	private SectionFactory sectionFactory;

	@Override
	public Section get(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException {
		SectionDto sectionDto = sectionDao.get(sportName, gameDate, sectionName);
		return sectionFactory.createSection(sectionDto);
	}

	@Override
	public Section getAvailable(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException {
		SectionDto sectionDto = sectionDao.getAvailable(sportName, gameDate, sectionName);
		return sectionFactory.createSection(sectionDto);
	}
}
