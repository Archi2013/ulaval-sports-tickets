package ca.ulaval.glo4003.domain.repositories;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.pojos.Section;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;

public interface ISectionRepository {

	Section get(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException;

	Section getAvailable(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException;
}
