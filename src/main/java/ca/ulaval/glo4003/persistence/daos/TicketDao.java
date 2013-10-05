package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketDto;

@Repository
public interface TicketDao {

	List<TicketDto> getTicketsForGame(int gameID) throws GameDoesntExistException;

	TicketDto getTicket(int ticketId) throws TicketDoesntExistException;
}
