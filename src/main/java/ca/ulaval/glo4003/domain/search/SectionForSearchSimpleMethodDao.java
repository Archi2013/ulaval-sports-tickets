package ca.ulaval.glo4003.domain.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.constants.DisplayedPeriod;
import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.sports.SportUrlMapper;
import ca.ulaval.glo4003.domain.tickets.TicketTypeUrlMapper;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.utilities.persistence.XmlIntegrityException;

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
		final DateTime endDateTime = calculateEndDateTime(DisplayedPeriod.valueOf(ticketSearchPreferenceDto
				.getDisplayedPeriod()));

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
						List<SectionDto> sectionDtos = sectionDao.getAllAvailable(gameDto.getSportName(),
								gameDto.getGameDate());

						for (SectionDto sectionDto : sectionDtos) {
							String url = createUrl(sportName, gameDto.getGameDate(), sectionDto.getSectionName());
							SectionForSearchDto sectionFSDto = new SectionForSearchDto(sectionDto, gameDto, sportName,
									url);
							sectionFSDtos.add(sectionFSDto);
						}
					} catch (XmlIntegrityException e) {
						// TODO s'occuper de la cause de cette exception
						System.out.println("Exception : " + e.getCause().getClass().getSimpleName());
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

	private String createUrl(String sportName, DateTime gameDate, String sectionName) {
		return String.format("/sport/%s/match/%s/billets/%s", sportUrlMapper.getUrl(sportName), gameDate.toString("yyyyMMddHHmmz"),
				ticketTypeUrlMapper.getUrl(sectionName));
	}
}
