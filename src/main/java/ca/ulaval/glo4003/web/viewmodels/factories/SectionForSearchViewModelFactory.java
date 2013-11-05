package ca.ulaval.glo4003.web.viewmodels.factories;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SectionForSearchDto;
import ca.ulaval.glo4003.web.viewmodels.SectionForSearchViewModel;

import com.google.common.base.Function;

@Component
public class SectionForSearchViewModelFactory {

	public List<SectionForSearchViewModel> createViewModels(List<SectionForSearchDto> ticketForSearchDtos) {
		List<SectionForSearchViewModel> list = transform(ticketForSearchDtos, new Function<SectionForSearchDto, SectionForSearchViewModel>() {
			public SectionForSearchViewModel apply(SectionForSearchDto ticket) {
				return new SectionForSearchViewModel(ticket.sport, ticket.opponents, ticket.date.toString("dd/MM/yyyy à HH'h'mm z"),
						ticket.admissionType, ticket.section, ticket.numberOfTicket, ticket.price.toString().replace(".", ","), ticket.url);
			}
		});
		return list;
	}

}
