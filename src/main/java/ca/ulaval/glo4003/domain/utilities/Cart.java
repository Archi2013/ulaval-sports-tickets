package ca.ulaval.glo4003.domain.utilities;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;


@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cart {
	public Integer numberOfTicketsToBuy;
	public List<String> selectedSeats;
	
	private GameDto gameDto;
	private SectionDto sectionDto;
	private Double cumulativePrice;

	public Boolean containTickets() {
		if (gameDto != null && sectionDto != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Integer getNumberOfTicketsToBuy() {
		return numberOfTicketsToBuy;
	}

	public void setNumberOfTicketsToBuy(Integer numberOfTicketsToBuy) {
		this.numberOfTicketsToBuy = numberOfTicketsToBuy;
	}

	public List<String> getSelectedSeats() {
		return selectedSeats;
	}

	public void setSelectedSeats(List<String> selectedSeats) {
		this.selectedSeats = selectedSeats;
	}
	
	public GameDto getGameDto() {
		return gameDto;
	}
	
	public void setGameDto(GameDto gameDto) {
		this.gameDto = gameDto;
	}
	
	public SectionDto getSectionDto() {
		return sectionDto;
	}
	
	public void setSectionDto(SectionDto sectionDto) {
		this.sectionDto = sectionDto;
	}

	public Double getCumulativePrice() {
		return cumulativePrice;
	}

	public void setCumulativePrice(Double cumulativePrice) {
		this.cumulativePrice = cumulativePrice;
	}

	public void empty() {
		this.cumulativePrice = 0.0;
		this.gameDto = null;
		this.sectionDto = null;
		this.numberOfTicketsToBuy = 0;
		this.selectedSeats = null;
	}
}


