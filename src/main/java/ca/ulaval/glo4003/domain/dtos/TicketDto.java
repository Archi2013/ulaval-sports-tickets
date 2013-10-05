package ca.ulaval.glo4003.domain.dtos;


public class TicketDto {

	public long gameId;
	public int ticketId;
	public double price;
	public String admissionType;
	public String section;

	public TicketDto(long gameId, int ticketId, double price, String admissionType, String section) {
		this.ticketId = ticketId;
		this.price = price;
		this.admissionType = admissionType;
		this.section = section;
		this.gameId = gameId;
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

	public String getAdmissionType() {
		return admissionType;
	}

	public String getSection() {
		return section;
	}

	public long getGameId() {
		return gameId;
	}
}
