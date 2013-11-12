package ca.ulaval.glo4003.persistence.daos.concrete;

import static com.google.common.collect.Lists.*;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.dtos.SectionForSearchDto;
import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionForSearchDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

@Repository
class SectionForSearchConcreteDao implements SectionForSearchDao {
	
	@Inject
	GameDao gameDao;
	
	@Inject
	SectionDao sectionDao;
	
	@Override
	public List<SectionForSearchDto> getSections(TicketSearchPreferenceDto ticketSearchPreferenceDto) {
		List<String> sportNames = ticketSearchPreferenceDto.getSelectedSports();
		List<SectionForSearchDto> sectionFSDtos = createFullListForSelectedSports(sportNames);
		
		return sectionFSDtos;
	}

	private List<SectionForSearchDto> createFullListForSelectedSports(List<String> sportNames) {
		List<SectionForSearchDto> sectionFSDtos = newArrayList();
		
		try {
			/*for (String sportName : sportNames) {
					List<GameDto> gameDtos = gameDao.getGamesForSport(sportName);
					
					for (GameDto gameDto : gameDtos) {
						List<SectionDto> sectionDtos = sectionDao.getAll(gameDto.getId());
						
						for (SectionDto sectionDto : sectionDtos) {
							String url = "/";
							SectionForSearchDto sectionFSDto = new SectionForSearchDto(sectionDto, gameDto, sportName, url);
							sectionFSDtos.add(sectionFSDto);
						}
					}
			}*/
			return sectionFSDtos;
		/*} catch (SportDoesntExistException | GameDoesntExistException e) {
			System.out.println("### ==> Plantage durant la recherche de billets");
			e.printStackTrace();
			return sectionFSDtos;
		}*/
	}

}
