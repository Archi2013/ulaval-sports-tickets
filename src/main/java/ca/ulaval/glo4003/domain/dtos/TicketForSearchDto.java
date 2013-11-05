package ca.ulaval.glo4003.domain.dtos;

import org.joda.time.DateTime;

public class TicketForSearchDto {
	public String sport;
	public String opponents;
	public DateTime date;
	public String admissionType;
	public String section;
	public Integer numberOfTicket;
	public Double price;
	public String url;
	
	public TicketForSearchDto(String sport, String opponents, DateTime date,
			String admissionType, String section, Integer numberOfTicket,
			Double price, String url) {
		super();
		this.sport = sport;
		this.opponents = opponents;
		this.date = date;
		this.admissionType = admissionType;
		this.section = section;
		this.numberOfTicket = numberOfTicket;
		this.price = price;
		this.url = url;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getOpponents() {
		return opponents;
	}

	public void setOpponents(String opponents) {
		this.opponents = opponents;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
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

	public Integer getNumberOfTicket() {
		return numberOfTicket;
	}

	public void setNumberOfTicket(Integer numberOfTicket) {
		this.numberOfTicket = numberOfTicket;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}