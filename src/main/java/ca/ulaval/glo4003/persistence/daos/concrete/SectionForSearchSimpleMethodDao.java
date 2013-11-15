package ca.ulaval.glo4003.persistence.daos.concrete;

import static com.google.common.collect.Lists.*;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.dtos.SectionForSearchDto;
import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.domain.utilities.TicketTypeUrlMapper;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionForSearchDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.xml.XmlIntegrityException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Repository
class SectionForSearchSimpleMethodDao implements SectionForSearchDao {

	@Inject
	GameDao gameDao;

	@Inject
	SectionDao sectionDao;

	@Inject
	TicketTypeUrlMapper ticketTypeUrlMapper;

	@Inject
	SportUrlMapper sportUrlMapper;

	@Override
	public List<SectionForSearchDto> getSections(TicketSearchPreferenceDto ticketSearchPreferenceDto) {
		List<String> sportNames = ticketSearchPreferenceDto.getSelectedSports();
		List<SectionForSearchDto> sectionFSDtos = createFullListForSelectedSports(sportNames);

		final Boolean localGameOnly = ticketSearchPreferenceDto.isLocalGameOnly();
		final List<TicketKind> ticketKinds = transform(ticketSearchPreferenceDto.getSelectedTicketKinds(),
				new Function<String, TicketKind>() {
					@Override
					public TicketKind apply(String ticketKind) {
						return TicketKind.valueOf(ticketKind);
					}
				});
		final DateTime endDateTime = calculateEndDateTime(DisplayedPeriod.valueOf(ticketSearchPreferenceDto.getDisplayedPeriod()));

		sectionFSDtos = newArrayList(Iterables.filter(sectionFSDtos, new Predicate<SectionForSearchDto>() {

			@Override
			public boolean apply(SectionForSearchDto sectionFSDto) {
				Boolean localGameCriterion = (sectionFSDto.isLocalGame() || !localGameOnly);
				Boolean ticketKindCriterion = false;
				if (sectionFSDto.isGeneralAdmission()) {
					ticketKindCriterion = ticketKinds.contains(TicketKind.GENERAL_ADMISSION);
				} else {
					ticketKindCriterion = ticketKinds.contains(TicketKind.WITH_SEAT);
				}
				Boolean displayedPeriodCriterion = sectionFSDto.getDate().isBefore(endDateTime);
				return localGameCriterion && ticketKindCriterion && displayedPeriodCriterion;
			}
		}));
		return sectionFSDtos;
	}

	private DateTime calculateEndDateTime(DisplayedPeriod displayedPeriod) {
		DateTime result = DateTime.now();

		switch (displayedPeriod) {
		case ONE_DAY:
			return result.plusDays(1);
		case ONE_WEEK:
			return result.plusDays(7);
		case ONE_MONTH:
			return result.plusDays(30);
		case THREE_MONTH:
			return result.plusDays(91);
		case SIX_MONTH:
			return result.plusDays(183);
		case ALL:
			return result.plusYears(2);
		default:
			return result.plusYears(2);
		}
	}

	private List<SectionForSearchDto> createFullListForSelectedSports(List<String> sportNames) {
		List<SectionForSearchDto> sectionFSDtos = newArrayList();

		try {
			for (String sportName : sportNames) {
				List<GameDto> gameDtos = gameDao.getGamesForSport(sportName);

				for (GameDto gameDto : gameDtos) {
					try {
						List<SectionDto> sectionDtos = sectionDao.getAllAvailable(gameDto.getId());
	
						for (SectionDto sectionDto : sectionDtos) {
							String url = createUrl(sportName, gameDto.getId(), sectionDto.getSectionName());
							SectionForSearchDto sectionFSDto = new SectionForSearchDto(sectionDto, gameDto, sportName, url);
							sectionFSDtos.add(sectionFSDto);
						}
					} catch (XmlIntegrityException e) {
						// TODO s'occuper de la cause de cette exception
						System.out.println("Exception : " + e.getCause().getClass().getSimpleName());
						e.getCause().printStackTrace();
					}
				}
			}
			return sectionFSDtos;
		} catch (SportDoesntExistException | GameDoesntExistException e) {
			System.out.println("### ==> Plantage durant la recherche de billets");
			e.printStackTrace();
			return sectionFSDtos;
		}
	}

	private String createUrl(String sportName, Long gameId, String sectionName) {
		return String.format("/sport/%s/match/%s/billets/%s", sportUrlMapper.getUrl(sportName), gameId,
				ticketTypeUrlMapper.getUrl(sectionName));
	}
}
