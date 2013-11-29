package ca.ulaval.glo4003.domain.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDto;


@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cart {
	public Integer numberOfTicketsToBuy;
	public List<String> selectedSeats;
	
	private GameDto gameDto;
	private SectionDto sectionDto;
	
	private List<SectionForCart> sections;

	public Cart() {
		this.sections = new ArrayList<SectionForCart>();
	}
	
	public Boolean containTickets() {
		return (sections.size() != 0) ? true : false;
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
		Double cumulativePrice = 0.0;
		for (SectionForCart section : this.sections) {
			cumulativePrice += section.getSubtotal();
		}
		return cumulativePrice;
	}

	public void setCumulativePrice(Double cumulativePrice) {
		
	}

	public void empty() {
		this.sections = new ArrayList<SectionForCart>();
	}

	public void addSection(SectionForCart sectionForCart) {
		if (!this.sections.contains(sectionForCart)) {
			this.sections.add(sectionForCart);
		} else {
			for (SectionForCart sectionInList : this.sections) {
				if (sectionInList.equals(sectionForCart)) {
					sectionInList.addElements(sectionForCart);
				}
			}
		}
	}
}


