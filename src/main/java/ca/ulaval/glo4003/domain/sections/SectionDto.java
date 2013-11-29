package ca.ulaval.glo4003.domain.sections;

import java.util.HashSet;
import java.util.Set;

public class SectionDto {

	private static final String GENERAL_SECTION = "Générale";
	private String sectionName;
	private int numberOfTickets;
	private double price;
	private Set<String> seats;

	public SectionDto(int numberOfTickets, double price) {
		this.sectionName = GENERAL_SECTION;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.seats = new HashSet<>();
	}

	public SectionDto(String sectionName, int numberOfTickets, double price, Set<String> seats) {
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.seats = seats;
	}

	public boolean isGeneralAdmission() {
		return sectionName.equals(GENERAL_SECTION);
	}

	public String getSectionName() {
		return sectionName;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public double getPrice() {
		return price;
	}

	public Set<String> getSeats() {
		return seats;
	}
}
