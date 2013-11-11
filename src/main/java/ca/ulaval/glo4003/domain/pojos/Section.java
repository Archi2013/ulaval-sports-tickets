package ca.ulaval.glo4003.domain.pojos;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.SectionDto;

public class Section {
	
	private String admissionType;
	private String sectionName;
	private int numberOfTickets;
	private double price;
	private List<String> seats;

	public Section(SectionDto sectionDto) {
		admissionType = sectionDto.getAdmissionType();
		sectionName = sectionDto.getSectionName();
		numberOfTickets = sectionDto.getNumberOfTickets();
		price = sectionDto.getPrice();
		seats = sectionDto.getSeats();
	}

	public boolean isGeneralAdmission() {
		return sectionName == null;
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

}
