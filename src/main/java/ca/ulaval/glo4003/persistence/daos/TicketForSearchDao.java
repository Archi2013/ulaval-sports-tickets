package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.TicketForSearchDto;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

public interface TicketForSearchDao {

	public List<TicketForSearchDto> getTickets(TicketSearchViewModel ticketSearchVM);

}
