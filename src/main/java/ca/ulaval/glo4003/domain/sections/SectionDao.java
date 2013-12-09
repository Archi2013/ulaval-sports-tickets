package ca.ulaval.glo4003.domain.sections;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.sections.dto.SectionDto;

public interface SectionDao {

	public SectionDto get(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException;

	public SectionDto getAvailable(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException;

	public List<SectionDto> getAll(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public List<SectionDto> getAllAvailable(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public Set<String> getAllSections();

	public List<SectionDto> getAllSectionsForTicketKind(String sportName, DateTime gameDate, List<String> ticketKinds);
}
