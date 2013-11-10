package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.TicketType;

public interface SectionDao {

	public SectionDto get(Long gameId, String sectionName) throws SectionDoesntExistException;
	public List<SectionDto> getAll(Long gameId) throws GameDoesntExistException;
	public List<TicketType> getAllTicketTypes();
}
