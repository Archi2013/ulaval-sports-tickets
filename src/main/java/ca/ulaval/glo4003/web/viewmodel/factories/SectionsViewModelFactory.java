package ca.ulaval.glo4003.web.viewmodel.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.web.converter.TicketConverter;
import ca.ulaval.glo4003.web.viewmodel.SectionViewModel;
import ca.ulaval.glo4003.web.viewmodel.SectionsViewModel;
import ca.ulaval.glo4003.web.viewmodel.TicketViewModel;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;

@Component
public class SectionsViewModelFactory {

	@Inject
	private TicketConverter ticketConverter;

	public SectionsViewModel createViewModel(GameDto gameDto) {
		return new SectionsViewModel(gameDto.getOpponents(), gameDto.getGameDate().toString("dd MMMM yyyy à HH'h'mm z"),
				convertListTicketDtoToListOfSections(gameDto.getTickets()));
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
