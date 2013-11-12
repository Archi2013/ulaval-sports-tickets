package ca.ulaval.glo4003.persistence.daos.concrete;

import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionForSearchDto;
import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.SectionForSearchDao;
SectionForSearchDao {
	
	@Inject
	GameDao gameDao;
	
	@Override
	public List<SectionForSearchDto> getSections(TicketSearchPreferenceDto ticketSearchPreferenceDto) {
		List<String> sportNames = ticketSearchPreferenceDto.getSelectedSports();
		
		for (String sportName : sportNames) {
			List<GameDto> gameDtos = gameDao.getGamesForSport(sportName);
		}
		
		return null;
	}

}
