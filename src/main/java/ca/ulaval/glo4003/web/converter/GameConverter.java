package ca.ulaval.glo4003.web.converter;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.web.viewmodel.GameViewModel;
import ca.ulaval.glo4003.web.viewmodel.SectionViewModel;
import ca.ulaval.glo4003.web.viewmodel.TicketViewModel;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;

@Component
public class GameConverter extends AbstractConverter<GameDto, GameViewModel> {

	@Inject
	private TicketConverter ticketConverter;

	public GameViewModel convert(GameDto gameDto) {
		return new GameViewModel(gameDto.getId(), gameDto.getOpponents(), gameDto.getGameDate().toString(
				"dd MMMM yyyy à HH'h'mm z"), convertListTicketDtoToListOfSections(gameDto.getTickets()));
	}

	// TODO PUT THIS SOMEWHERE ELSE. CLEAN THIS UP.
	private List<SectionViewModel> convertListTicketDtoToListOfSections(List<TicketDto> ticketDtos) {
		ArrayListMultimap<String, TicketViewModel> map = ArrayListMultimap.create();
		for (TicketDto ticket : ticketDtos) {
			String section = ticket.admissionType + "," + ticket.section;
			map.put(section, ticketConverter.convert(ticket));
		}
		List<SectionViewModel> sections = Lists.newArrayList();
		for (String key : map.keySet()) {
			String admissionType = key.split(",")[0];
			String sectionName = key.split(",")[1];
			int numberOfTickets = map.get(key).size();
			SectionViewModel section = new SectionViewModel(admissionType, sectionName, numberOfTickets);
			sections.add(section);
		}
		return sections;
	}
}
