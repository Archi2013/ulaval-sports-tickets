package ca.ulaval.glo4003.dtos;

import org.joda.time.DateTime;

public class TicketDto {

	private GameDto game;
	private int ticketId;
	private double price;

	private String admissionType;
	private String section;

	public TicketDto(GameDto game, int ticketId, double price, String admissionType, String section) {
		this.ticketId = ticketId;
		this.price = price;
		this.admissionType = admissionType;
		this.section = section;
		this.game = game;
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
		return game.getOpponents();
	}

	public DateTime getGameDate() {
		return game.getGameDate();
	}

	public String getGameDateFormatted() {
		return game.getGameDateFormatted();
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

	public GameDto getGame() {
		return game;
	}

	public void setGame(GameDto game) {
		this.game = game;
	}
}
