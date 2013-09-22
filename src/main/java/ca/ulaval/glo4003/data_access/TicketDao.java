package ca.ulaval.glo4003.data_access;

import java.util.List;

import ca.ulaval.glo4003.dtos.TicketDto;

public interface TicketDao {

	List<TicketDto> getTicketsForGame(int gameID);
}
