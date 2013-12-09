package ca.ulaval.glo4003.sections.dto;

import java.util.HashSet;
import java.util.Set;

public class SectionDto {

	private String sectionName;
	private int numberOfTickets;
	private double price;
	private Set<String> seats;
	private boolean generalAdmission;

	public SectionDto(String sectionName, int numberOfTickets, double price) {
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.seats = new HashSet<>();
		this.generalAdmission = true;
	}

	public SectionDto(String sectionName, int numberOfTickets,
			double price, Set<String> seats) {
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.seats = seats;
		this.generalAdmission = false;
	}

	public boolean isGeneralAdmission() {
		return generalAdmission;
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
