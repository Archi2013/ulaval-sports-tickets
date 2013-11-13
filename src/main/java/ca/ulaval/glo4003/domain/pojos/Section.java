package ca.ulaval.glo4003.domain.pojos;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.SectionDto;

public class Section {

	private static final String GENERAL_SECTION = "Générale";
	private String sectionName;
	private int numberOfTickets;
	private double price;
	private List<String> seats;

	public Section(SectionDto sectionDto) {
		sectionName = sectionDto.getSectionName();
		numberOfTickets = sectionDto.getNumberOfTickets();
		price = sectionDto.getPrice();
		seats = sectionDto.getSeats();
	}

	public boolean isGeneralAdmission() {

		boolean generalAdmission = sectionName.equals(GENERAL_SECTION);
		return generalAdmission;
	}

	public Boolean isValidElements(Integer numberOfTicketsToBuy, List<String> selectedSeats) {
		Boolean result;
		if (isGeneralAdmission()) {
			result = isValidGeneralAdmissionInformations(numberOfTicketsToBuy);
		} else {
			result = isValidWithSeatAdmissionInformations(selectedSeats);
		}
		return result;
	}

	private Boolean isValidGeneralAdmissionInformations(Integer numberOfTicketsToBuy) {
		if (numberOfTicketsToBuy < 1 || numberOfTicketsToBuy > this.numberOfTickets) {
			return false;
		}
		return true;
	}

	private Boolean isValidWithSeatAdmissionInformations(List<String> selectedSeats) {
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

	public List<String> getSeats() {
		return seats;
	}

}
