package ca.ulaval.glo4003.dtos;

import java.util.Date;

public class TicketDTO {
	public int ticketID;

	public double price;
	public String opponents;
	public Date gameDate;
	public String admissionType;
	public String section;

	public int getID() {
		return ticketID;
	}

	public void setID(int ticketID) {
		this.ticketID = ticketID;
	}

	public double getPrice() {
		return price;
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

	public Date getGameDate() {
		return gameDate;
	}

	public void setDate(Date gameDate) {
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
