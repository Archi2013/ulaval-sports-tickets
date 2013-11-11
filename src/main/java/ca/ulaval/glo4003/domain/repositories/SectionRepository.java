package ca.ulaval.glo4003.domain.repositories;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.pojos.Section;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;

@Repository
public class SectionRepository implements ISectionRepository {

	@Inject
	private SectionDao sectionDao;
	
	@Override
	public Section get(Long gameId, String sectionName) throws SectionDoesntExistException {
		SectionDto sectionDto = sectionDao.get(gameId, sectionName);
		return new Section(sectionDto);
	}

}
