package ca.ulaval.glo4003.domain.search;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDto;

public class SectionForSearchDto {
	private static final String GENERAL_SECTION = "Générale";
	public String sport;
	public String opponents;
	public String location;
	public DateTime date;
	public String section;
	public Integer numberOfTicket;
	public Double price;
	public String url;
	
	public SectionForSearchDto(String sport, String opponents, String location, DateTime date,
			String section, Integer numberOfTicket,
			Double price, String url) {
		super();
		this.sport = sport;
		this.opponents = opponents;
		this.location = location;
		this.date = date;
		this.section = section;
		this.numberOfTicket = numberOfTicket;
		this.price = price;
		this.url = url;
	}
	
	public SectionForSearchDto(SectionDto sectionDto, GameDto gameDto, String sportName, String url) {
		this(sportName, gameDto.getOpponents(), gameDto.getLocation(), gameDto.getGameDate(), sectionDto.getSectionName(),
				sectionDto.getNumberOfTickets(), sectionDto.getPrice(), url);
	}

	public boolean isGeneralAdmission() {
		return section.equals(GENERAL_SECTION);
	}
	
	public Boolean isLocalGame() {
		return this.location.equals("Stade TELUS-UL") || this.location.equals("PEPS Terrain 6");
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
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