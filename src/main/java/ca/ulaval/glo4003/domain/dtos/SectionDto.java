package ca.ulaval.glo4003.domain.dtos;

import java.util.List;

public class SectionDto {

	private String admissionType;
	private String sectionName;
	private int numberOfTickets;
	private double price;
	private List<String> seats;

	public SectionDto(String admissionType, String sectionName, int numberOfTickets, double price, List<String> seats) {
		this.admissionType = admissionType;
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
		this.seats = seats;
	}

	public boolean isGeneralAdmission() {
		if (seats.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getAdmissionType() {
		return admissionType;
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
