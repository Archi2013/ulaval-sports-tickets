package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.SectionDto;

public interface SectionDao {

	public SectionDto get(int gameId, String sectionName) throws SectionDoesntExistException;

	public List<SectionDto> getAll(int gameId) throws GameDoesntExistException;

}
