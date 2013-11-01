package ca.ulaval.glo4003.web.viewmodels.factories;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.TicketForSearchDto;
import ca.ulaval.glo4003.web.viewmodels.TicketForSearchViewModel;

import com.google.common.base.Function;

@Component
public class TicketForSearchViewModelFactory {

	public List<TicketForSearchViewModel> createViewModels(List<TicketForSearchDto> ticketForSearchDtos) {
		List<TicketForSearchViewModel> list = transform(ticketForSearchDtos, new Function<TicketForSearchDto, TicketForSearchViewModel>() {
			public TicketForSearchViewModel apply(TicketForSearchDto ticket) {
				return new TicketForSearchViewModel(ticket.sport, ticket.opponents, ticket.date.toString("dd/MM/yyyy à HH'h'mm z"),
						ticket.admissionType, ticket.section, ticket.numberOfTicket, ticket.price.toString().replace(".", ","), ticket.url);
			}
		});
		return list;
	}

}
