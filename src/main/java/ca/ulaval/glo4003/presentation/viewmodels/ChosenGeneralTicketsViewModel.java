package ca.ulaval.glo4003.presentation.viewmodels;

import javax.validation.constraints.NotNull;

public class ChosenGeneralTicketsViewModel extends ChosenTicketsViewModel {
	
	@NotNull
	public Integer numberOfTicketsToBuy;
	
	public Integer getNumberOfTicketsToBuy() {
		return numberOfTicketsToBuy;
	}

	public void setNumberOfTicketsToBuy(Integer numberOfTicketsToBuy) {
		this.numberOfTicketsToBuy = numberOfTicketsToBuy;
	}
}
