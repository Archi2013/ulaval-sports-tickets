package ca.ulaval.glo4003.fakes;


//@Repository
/*public class FakeDataTicketDao implements TicketDao {

 @Inject
 private FakeDatabase database;

 // @Override

 public List<TicketDto> getTicketsForGame(int gameId) throws
 GameDoesntExistException { List<SportDto> sports = database.getSports();
 for (SportDto sport : sports) { for (GameDto game : sport.getGames()) {
 if (gameId == game.getId()) { return game.getTickets(); } } } throw new
 GameDoesntExistException(); }

 @Override
 public TicketDto getTicket(int ticketId) throws TicketDoesntExistException {
 List<SportDto> sports = database.getSports();
 for (SportDto sport : sports) {
 for (GameDto game : sport.getGames()) {
 for (TicketDto ticket : game.getTickets()) {
 if (ticketId == ticket.getTicketId()) {
 return ticket;
 }
 }
 }
 }
 throw new TicketDoesntExistException();
 }

 @Override
 public void add(TicketDto ticket) throws TicketAlreadyExistException {
 throw new RuntimeException("Cannot add element to fake data");
 }

 @Override
 public List<TicketDto> getTicketsForSection(int gameID, String sectionName) throws SectionDoesntExistException {
 throw new RuntimeException("Not implemented yet.");
 }
 }*/
