package ca.ulaval.glo4003.persistence.daos;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.SectionDto;

public interface SectionDao {

	public SectionDto get(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException;

	public SectionDto getAvailable(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException;

	public List<SectionDto> getAll(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public List<SectionDto> getAllAvailable(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public Set<String> getAllSections();

	@Deprecated
	public SectionDto get(Long gameId, String sectionName) throws SectionDoesntExistException;

	@Deprecated
	public SectionDto getAvailable(Long gameId, String sectionName) throws SectionDoesntExistException;

	@Deprecated
	public List<SectionDto> getAll(Long gameId) throws GameDoesntExistException;

	@Deprecated
	public List<SectionDto> getAllAvailable(Long gameId) throws GameDoesntExistException;

}
