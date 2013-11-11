package ca.ulaval.glo4003.persistence.daos;

import java.util.List;
import java.util.Set;

import ca.ulaval.glo4003.domain.dtos.SectionDto;

public interface SectionDao {

	public SectionDto get(Long gameId, String sectionName) throws SectionDoesntExistException;
	public List<SectionDto> getAll(Long gameId) throws GameDoesntExistException;
	public Set<String> getAllSections();
}
