package ca.ulaval.glo4003.presentation.viewmodels;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class ChosenWithSeatTicketsViewModel extends ChosenTicketsViewModel {
	
	@NotNull
	public Set<String> selectedSeats;
	
	public ChosenWithSeatTicketsViewModel() {
		this.selectedSeats = new HashSet<String>();
	}
	
	public Set<String> getSelectedSeats() {
		return selectedSeats;
	}

	public void setSelectedSeats(Set<String> selectedSeats) {
		if (selectedSeats != null) {
			this.selectedSeats = selectedSeats;
		}
	}
}
