package ca.ulaval.glo4003.domain.sections;

import java.util.Set;

public class Section {

	private String sectionName;
	private int numberOfTickets;
	private double price;
	private Set<String> seats;
	private boolean generalAdmission;

	public Section(SectionDto sectionDto) {
		this.sectionName = sectionDto.getSectionName();
		this.numberOfTickets = sectionDto.getNumberOfTickets();
		this.price = sectionDto.getPrice();
		this.seats = sectionDto.getSeats();
		this.generalAdmission = sectionDto.isGeneralAdmission();
	}

	public boolean isGeneralAdmission() {
		return this.generalAdmission;
	}

	public Boolean isValidNumberOfTicketsForGeneralTickets(Integer numberOfTicketsToBuy) {
		if (numberOfTicketsToBuy < 1 || numberOfTicketsToBuy > this.numberOfTickets) {
			return false;
		}
		return true;
	}

	public Boolean isValidSelectedSeatsForWithSeatTickets(Set<String> selectedSeats) {
		Integer numberOfSelectedSeatTicketsToBuy = selectedSeats.size();
		if (numberOfSelectedSeatTicketsToBuy < 1 || numberOfSelectedSeatTicketsToBuy > this.numberOfTickets) {
			return false;
		}
		for (String seat : selectedSeats) {
			if (!seats.contains(seat)) {
				return false;
			}
		}
		return true;
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
