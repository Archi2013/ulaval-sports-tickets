package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketDto;

@Repository
public interface TicketDao {

	public List<TicketDto> getTicketsForGame(int gameID) throws GameDoesntExistException;
	public TicketDto getTicket(int ticketId) throws TicketDoesntExistException;
	public void add(TicketDto ticket) throws TicketAlreadyExistException;
	public List<TicketDto> getTicketForSection(int gameID, String sectionName) throws SectionDoesntExistException;
}
