package ca.ulaval.glo4003.domain.pojos;


public interface Game {

	boolean acceptsToBeScheduled();

	void beScheduledToThisSport(String newSportName);

	void addTicket(Ticket tickets);

}
