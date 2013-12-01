package ca.ulaval.glo4003.domain.tickets;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;

@Repository
public interface TicketDao {

	public List<TicketDto> getAll(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public List<TicketDto> getAllAvailable(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public List<TicketDto> getAllInSection(String sportName, DateTime gameDate, String sectionName)
			throws SectionDoesntExistException;

	public TicketDto get(int ticketId) throws TicketDoesntExistException;

	public TicketDto get(String sportName, DateTime gameDate, String section, String seat) throws TicketDoesntExistException;

	public void add(TicketDto ticket) throws TicketAlreadyExistsException, GameDoesntExistException;

	public void update(TicketDto firstTicketData) throws TicketDoesntExistException;

	public void commit();
}
