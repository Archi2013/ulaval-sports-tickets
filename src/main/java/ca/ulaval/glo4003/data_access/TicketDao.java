package ca.ulaval.glo4003.data_access;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.dtos.TicketDto;

@Repository
public interface TicketDao {

	List<TicketDto> getTicketsForGame(int gameID) throws GameDoesntExistException;

	TicketDto getTicket(int ticketId) throws TicketDoesntExistException;
}
