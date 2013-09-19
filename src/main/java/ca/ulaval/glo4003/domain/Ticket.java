package ca.ulaval.glo4003.domain;

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	public int TicketID;
	public int MatchID;
	public double price;
	public String opponents;
	public Date matchDate;
	public String admissionType;
	public String section;

}
