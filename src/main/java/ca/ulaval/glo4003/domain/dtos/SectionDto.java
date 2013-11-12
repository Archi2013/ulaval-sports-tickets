package ca.ulaval.glo4003.domain.dtos;

import java.util.ArrayList;
import java.util.List;

public class SectionDto {

	private String sectionName;
	private int numberOfTickets;
	private double price;
	private List<String> seats;
	
	public SectionDto(int numberOfTickets, double price) {
		this.sectionName = "Générale";
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.seats = new ArrayList<>();
	}

	public SectionDto(String sectionName, int numberOfTickets, double price, List<String> seats) {
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.seats = seats;
	}

	public boolean isGeneralAdmission() {
		return sectionName == null;
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

	public List<String> getSeats() {
		return seats;
	}
}
