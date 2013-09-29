package ca.ulaval.glo4003.web.converter;

import static com.google.common.collect.Lists.*;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.dto.TicketDto;
import ca.ulaval.glo4003.web.viewmodel.TicketViewModel;

import com.google.common.base.Function;

@Component
public class TicketConverter {
	
	public List<TicketViewModel> convert(List<TicketDto> ticketDtos) {
		return transform(ticketDtos, new Function<TicketDto, TicketViewModel>() {
			public TicketViewModel apply(TicketDto ticketDto) {
				return convert(ticketDto);
			}
		});
	}
	
	public TicketViewModel convert(TicketDto ticketDto) {
		String price = new Double(ticketDto.getPrice()).toString();
		price = price.replace('.', ',');
		return new TicketViewModel(new Long(ticketDto.getTicketId()), price,
				ticketDto.getAdmissionType(), ticketDto.getSection(),
				ticketDto.getGame().getOpponents(),
				ticketDto.getGame().getGameDate().toString("dd MMMM yyyy à HH'h'mm z"));
	}
}