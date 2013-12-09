package ca.ulaval.glo4003.utilities.search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.game.dto.GameDto;
import ca.ulaval.glo4003.sections.dto.SectionDto;
import ca.ulaval.glo4003.utilities.search.dto.SectionForSearchDto;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;
import ca.ulaval.glo4003.utilities.time.UrlDateTime;
import ca.ulaval.glo4003.utilities.time.UrlDateTimeFactory;
import ca.ulaval.glo4003.utilities.urlmapper.SportUrlMapper;
import ca.ulaval.glo4003.utilities.urlmapper.TicketTypeUrlMapper;

@Repository
class UserSearchPreferenceSectionForSearchDao implements SectionForSearchDao {

	@Inject
	GameDao gameDao;

	@Inject
	SectionDao sectionDao;

	@Inject
	TicketTypeUrlMapper ticketTypeUrlMapper;

	@Inject
	SportUrlMapper sportUrlMapper;

	@Inject
	UrlDateTimeFactory urlDateTimeFactory;

	@Override
	public List<SectionForSearchDto> getSections(UserSearchPreferenceDto ticketSearchPreferenceDto) {
		List<SectionForSearchDto> sectionsForSearches = new ArrayList<>();
		
		List<String> ticketKinds = ticketSearchPreferenceDto.getSelectedTicketKinds();
		List<GameDto> games = gameDao.getFromUserSearchPreference(ticketSearchPreferenceDto);
		for (GameDto game : games) {
			sectionsForSearches.addAll(findInSectionsForTicketKinds(ticketKinds, game));
		}

		return sectionsForSearches;
	}

	private List<SectionForSearchDto> findInSectionsForTicketKinds(List<String> ticketKinds, GameDto game) {
		List<SectionForSearchDto> sectionsForSearches = new ArrayList<>();
		
		List<SectionDto> sections = sectionDao.getAllSectionsForTicketKind(game.getSportName(), game.getGameDate(), ticketKinds);
		for (SectionDto section : sections) {
			String url = createUrl(game.getSportName(), game.getGameDate(), section.getSectionName());
			SectionForSearchDto sectionForSearch = new SectionForSearchDto(section, game, game.getSportName(), url);
			sectionsForSearches.add(sectionForSearch);
		}
		
		return sectionsForSearches;
	}

	private String createUrl(String sportName, DateTime gameDate, String sectionName) {
		UrlDateTime urlDateTime = urlDateTimeFactory.create(gameDate);
		return String.format("/sport/%s/match/%s/billets/%s", sportUrlMapper.getUrl(sportName), urlDateTime.toString(),
				ticketTypeUrlMapper.getUrl(sectionName));
	}
}
