package ca.ulaval.glo4003.domain.repositories;

import ca.ulaval.glo4003.domain.pojos.Section;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;

public interface ISectionRepository {

	Section get(Long gameId, String sectionName) throws SectionDoesntExistException;

}
