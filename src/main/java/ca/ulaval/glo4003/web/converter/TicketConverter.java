package ca.ulaval.glo4003.web.converter;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.dto.TicketDto;
import ca.ulaval.glo4003.web.viewmodel.TicketViewModel;

@Component
public class TicketConverter extends AbstractConverter<TicketDto, TicketViewModel> {

	public TicketViewModel convert(TicketDto ticketDto) {
		String price = new Double(ticketDto.getPrice()).toString();
		price = price.replace('.', ',');
		return new TicketViewModel(new Long(ticketDto.getTicketId()), price, ticketDto.getAdmissionType(),
				ticketDto.getSection(), ticketDto.getGame().getOpponents(), ticketDto.getGame().getGameDate()
						.toString("dd MMMM yyyy à HH'h'mm z"));
	}
}
