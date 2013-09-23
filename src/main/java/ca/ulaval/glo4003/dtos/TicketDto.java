package ca.ulaval.glo4003.dtos;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class TicketDto {
	public int ticketId;

	public double price;
	public String opponents;
	public GregorianCalendar gameDate;
	public String admissionType;
	public String section;

	public TicketDto(int ticketId, double price, String opponents, GregorianCalendar gameDate, String admissionType,
			String section) {
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

	public GregorianCalendar getGameDate() {
		return gameDate;
	}
	
	public String getGameDateFormatted() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd MMMM yyyy HH:MM");
		fmt.setCalendar(gameDate);
		return fmt.format(gameDate.getTime());
	}

	public void setDate(GregorianCalendar gameDate) {
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
