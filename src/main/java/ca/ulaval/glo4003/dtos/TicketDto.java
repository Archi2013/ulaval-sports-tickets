package ca.ulaval.glo4003.dtos;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class TicketDto {

	private int ticketId;
	private double price;
	private String opponents;
	private DateTime gameDate;
	private String admissionType;
	private String section;

	public TicketDto(int ticketId, double price, String opponents, DateTime gameDate, String admissionType, String section) {
		this.ticketId = ticketId;
		this.price = price;
		this.opponents = opponents;
		this.gameDate = gameDate;
		this.admissionType = admissionType;
		this.section = section;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setID(int ticketID) {
		this.ticketId = ticketID;
	}

	public double getPrice() {
		return price;
	}

	public String getPriceFormatted() {
		String formatted = new Double(price).toString();
		formatted = formatted.replace('.', ',');
		return formatted;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOpponents() {
		return opponents;
	}

	public void setOpponents(String opponents) {
		this.opponents = opponents;
	}

	public DateTime getGameDate() {
		return gameDate;
	}

	public String getGameDateFormatted() {
		return gameDate.toString(DateTimeFormat.longDateTime());
	}

	public void setDate(DateTime gameDate) {
		this.gameDate = gameDate;
	}

	public String getAdmissionType() {
		return admissionType;
	}

	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
}
