package ca.ulaval.glo4003.domain.sections;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;

public interface ISectionRepository {

	Section get(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException;

	Section getAvailable(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException;
}
