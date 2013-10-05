package ca.ulaval.glo4003.persistence.daos;

import ca.ulaval.glo4003.domain.dtos.SectionDto;

public interface SectionDao {

	public SectionDto get(int gameId, String admissionType, String sectionName) throws SectionDoesntExistException;

}
