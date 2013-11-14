package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketDto;

@Repository
public interface TicketDao {

	public TicketDto get(int ticketId) throws TicketDoesntExistException;

	public TicketDto get(String sportName, DateTime gameDate, int ticketNumber) throws TicketDoesntExistException;

	public TicketDto get(String sport, DateTime date, String seat);

	public List<TicketDto> getAvailableTicketsForGame(Long gameID) throws GameDoesntExistException;

	public List<TicketDto> getTicketsForSection(int gameID, String sectionName) throws SectionDoesntExistException;

	public List<TicketDto> getTicketsForGame(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public void add(TicketDto ticket) throws TicketAlreadyExistException, GameDoesntExistException;

	public void update(TicketDto firstTicketData) throws TicketDoesntExistException;

	public void commit();

	List<TicketDto> getAllTicketsForGame(Long gameID) throws GameDoesntExistException;

}
